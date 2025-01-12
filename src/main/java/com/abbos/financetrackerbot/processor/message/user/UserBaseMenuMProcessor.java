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
 * @since 09/January/2025  15:40
 **/
@Component
@RequiredArgsConstructor
public class UserBaseMenuMProcessor implements TelegramBotHandler, Processor {

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
            case USER_ON_BASE_MENU -> handleOnBaseMenuProcess(text, user, chatId, language);
            default -> unsupported(chatId, language);
        };
    }

    private BotApiMethod<?> handleOnBaseMenuProcess(String text, User user, Long chatId, Language language) {
        if (checkLocalizedMessage("menu.reports", text, language)) {
            updateUserState(user, USER_BASE_MENU_STATE_REPORTS);
            return sendMessageFactory.sendMessageUserMenuReports(String.valueOf(chatId), language);
        } else if (checkLocalizedMessage("menu.settings", text, language)) {
            updateUserState(user, USER_BASE_MENU_STATE_SETTINGS);
            return sendMessageFactory.sendMessageUserMenuSettings(String.valueOf(chatId), language);
        }
        return unsupported(chatId, language);
    }

    @Override
    public void updateUserState(User user, String state) {
        user.setState(state);
        userService.update(user);
    }

    @Override
    public User getUserByChatId(Long chatId) {
        return userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
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

    @Override
    public BotApiMethod<?> redirectToBaseMenu(Long chatId, User user) {
        return null;
    }

    @Override
    public BotApiMethod<?> redirectToBack(Long chatId, User user) {
        return null;
    }

    @Override
    public boolean isActionBack(String text, Language language) {
        return false;
    }

    @Override
    public Language getUserLanguage(Long chatId) {
        return null;
    }
}
