package com.abbos.financetrackerbot.processor.message.user;

import com.abbos.financetrackerbot.domain.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.processor.Processor;
import com.abbos.financetrackerbot.processor.TelegramBotHandler;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.state.AdminBaseMenuState;
import com.abbos.financetrackerbot.util.MessageSourceUtils;
import com.abbos.financetrackerbot.util.factory.SendMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.abbos.financetrackerbot.state.UserBaseMenuState.*;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  14:18
 **/
@Component
@RequiredArgsConstructor
public class UserMenuReportsMProcessor implements TelegramBotHandler, Processor {

    private final UserService userService;
    private final MessageSourceUtils messageSourceUtils;
    private final SendMessageFactory sendMessageFactory;

    @Override
    public BotApiMethod<?> process(Update update, String state) {
        final var message = update.getMessage();
        final var chatId = message.getFrom().getId();
        final var text = message.getText();

        User user = getUserByChatId(chatId);
        Language language = user.getLanguage();

        return switch (user.getState()) {
            case USER_BASE_MENU_STATE_REPORTS -> handleStateReports(text, user, chatId, language);
            case USER_MENU_REPORTS_STATE_ADDITIONAL_REPORTS,
                 USER_MENU_REPORTS_STATE_MONTHLY_EXPENSES,
                 USER_MENU_REPORTS_STATE_MONTHLY_INCOME -> redirectToBack(chatId, user);
            default -> unsupported(chatId, language);
        };
    }


    private BotApiMethod<?> handleStateReports(String text, User user, Long chatId, Language language) {
        if (isActionBack(text, language)) {
            return redirectToBaseMenu(chatId, user);
        } else if (checkLocalizedMessage("menu.additional.reports", text, language)) {
            return handleAdditionalReports(user, chatId, language);
        } else if (checkLocalizedMessage("menu.monthly.expenses", text, language)) {
            return handleMonthlyExpenses(user, chatId, language);
        } else if (checkLocalizedMessage("menu.monthly.income", text, language)) {
            return handleMonthlyIncome(user, chatId, language);
        }
        return unsupported(chatId, language);
    }

    private BotApiMethod<?> handleAdditionalReports(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_REPORTS_STATE_ADDITIONAL_REPORTS);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleMonthlyExpenses(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_REPORTS_STATE_MONTHLY_EXPENSES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleMonthlyIncome(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_REPORTS_STATE_MONTHLY_INCOME);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    @Override
    public void updateUserState(User user, String state) {
        user.setState(state);
        userService.updateUser(user);
    }

    @Override
    public boolean isActionBack(String text, Language language) {
        return checkLocalizedMessage("button.back", text, language);
    }

    @Override
    public BotApiMethod<?> redirectToBaseMenu(Long chatId, User user) {
        updateUserState(user, USER_ON_BASE_MENU);
        return sendMessageFactory.sendMessageUserMenu(String.valueOf(chatId), user.getLanguage());
    }

    @Override
    public BotApiMethod<?> redirectToBack(Long chatId, User user) {
        updateUserState(user, USER_BASE_MENU_STATE_REPORTS);
        return sendMessageFactory.sendMessageUserMenuReports(String.valueOf(chatId), user.getLanguage());
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
    }

    @Override
    public Language getUserLanguage(Long chatId) {
        return getUserByChatId(chatId).getLanguage();
    }

    @Override
    public boolean checkLocalizedMessage(String key, String message, Language language) {
        final var localizedMessage = messageSourceUtils.getLocalizedMessage(key, language);
        return localizedMessage.equals(message);
    }

    @Override
    public SendMessage unsupported(Long chatID, Language language) {
        User user = getUserByChatId(chatID);
        Role role = user.getRole();
        final var state = role.equals(Role.USER) ? USER_ON_BASE_MENU : AdminBaseMenuState.ADMIN_ON_BASE_MENU;
        updateUserState(user, state);
        return role.equals(Role.USER)
                ? sendMessageFactory.sendMessageUserMenu(String.valueOf(chatID), language)
                : sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatID), language);
    }
}
