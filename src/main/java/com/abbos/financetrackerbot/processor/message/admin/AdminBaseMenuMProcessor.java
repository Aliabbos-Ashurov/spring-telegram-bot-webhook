package com.abbos.financetrackerbot.processor.message.admin;

import com.abbos.financetrackerbot.domain.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.processor.Processor;
import com.abbos.financetrackerbot.processor.TelegramBotHandler;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.util.MessageSourceUtils;
import com.abbos.financetrackerbot.util.factory.SendMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.abbos.financetrackerbot.state.AdminBaseMenuState.*;
import static com.abbos.financetrackerbot.state.UserBaseMenuState.USER_ON_BASE_MENU;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:45
 **/
@Component
@RequiredArgsConstructor
public class AdminBaseMenuMProcessor implements TelegramBotHandler, Processor {

    private final UserService userService;
    private final MessageSourceUtils messageSourceUtils;
    private final SendMessageFactory sendMessageFactory;

    @Override
    public BotApiMethod<?> process(Update update, String state) {
        final var message = update.getMessage();
        final var chatId = message.getFrom().getId();
        final var text = message.getText();
        final var user = userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
        final var language = user.getLanguage();
        return switch (user.getState()) {
            case ADMIN_ON_BASE_MENU,
                 ADMIN_BASE_MENU_STATE_CURRENCY_TYPES,
                 ADMIN_BASE_MENU_STATE_TRANSACTION_TYPES,
                 ADMIN_BASE_MENU_STATE_EXPENSE_CATEGORIES,
                 ADMIN_BASE_MENU_STATE_INCOME_CATEGORIES,
                 ADMIN_BASE_MENU_STATE_TRANSACTION_STATUES,
                 ADMIN_BASE_MENU_STATE_NOTE,
                 ADMIN_BASE_MENU_STATE_FILE,
                 ADMIN_BASE_MENU_STATE_DATE,
                 ADMIN_BASE_MENU_STATE_CLIENTS -> handleStateReports(text, user, chatId, language);
            default -> unsupported(chatId, language);
        };
    }

    private BotApiMethod<?> handleStateReports(String text, User user, Long chatId, Language language) {
        if (isActionBack(text, language)) {
            return redirectToBaseMenu(chatId, user);
        } else if (checkLocalizedMessage("menu.admin.base.currency.types", text, language)) {
            return handleCurrencyTypes(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.transaction.types", text, language)) {
            return handleTransactionTypes(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.expense.categories", text, language)) {
            return handleExpenseCategories(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.income.categories", text, language)) {
            return handleIncomeCategories(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.transaction.statuses", text, language)) {
            return handleTransactionStatues(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.note", text, language)) {
            return handleNote(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.file", text, language)) {
            return handleFile(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.date", text, language)) {
            return handleDate(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.clients", text, language)) {
            return handleClients(user, chatId, language);
        } else if (checkLocalizedMessage("menu.admin.base.service.types", text, language)) {
            return handleServiceTypes(user, chatId, language);
        }
        return unsupported(chatId, language);
    }


    private BotApiMethod<?> handleCurrencyTypes(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_CURRENCY_TYPES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleTransactionTypes(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_TRANSACTION_TYPES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleExpenseCategories(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_EXPENSE_CATEGORIES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleIncomeCategories(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_INCOME_CATEGORIES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleTransactionStatues(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_TRANSACTION_STATUES);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleNote(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_NOTE);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleFile(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_FILE);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleDate(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_DATE);
        return sendMessageFactory.sendMessageBackButton(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleClients(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_CLIENTS);
        return sendMessageFactory.sendMessageAdminCustomersInfo(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleServiceTypes(User user, Long chatId, Language language) {
        updateUserState(user, ADMIN_BASE_MENU_STATE_SERVICE_TYPES);
        return sendMessageFactory.sendMessageAdminServiceType(String.valueOf(chatId), language);
    }

    @Override
    public boolean isActionBack(String text, Language language) {
        return checkLocalizedMessage("button.back", text, language);
    }


    @Override
    public boolean checkLocalizedMessage(String key, String message, Language language) {
        final var localizedMessage = messageSourceUtils.getLocalizedMessage(key, language);
        return localizedMessage.equals(message);
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
    public void updateUserState(User user, String state) {
        user.setState(state);
        userService.update(user);
    }

    @Override
    public SendMessage unsupported(Long chatID, Language language) {
        User user = getUserByChatId(chatID);
        Role role = user.getRole();
        final var state = role.equals(Role.USER) ? USER_ON_BASE_MENU : ADMIN_ON_BASE_MENU;
        updateUserState(user, state);
        return role.equals(Role.USER)
                ? sendMessageFactory.sendMessageUserMenu(String.valueOf(chatID), language)
                : sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatID), language);
    }

    @Override
    public BotApiMethod<?> redirectToBaseMenu(Long chatId, User user) {
        updateUserState(user, ADMIN_ON_BASE_MENU);
        return sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatId), user.getLanguage());
    }

    @Override
    public BotApiMethod<?> redirectToBack(Long chatId, User user) {
        updateUserState(user, ADMIN_ON_BASE_MENU);
        return sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatId), user.getLanguage());
    }
}
