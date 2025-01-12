package com.abbos.financetrackerbot.handler;

import com.abbos.financetrackerbot.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.processor.message.AuthenticationMProcessor;
import com.abbos.financetrackerbot.processor.message.admin.AdminBaseMenuMProcessor;
import com.abbos.financetrackerbot.processor.message.admin.AdminMenuServicesMProcessor;
import com.abbos.financetrackerbot.processor.message.user.UserBaseMenuMProcessor;
import com.abbos.financetrackerbot.processor.message.user.UserMenuReportsMProcessor;
import com.abbos.financetrackerbot.processor.message.user.UserMenuSettingsMProcessor;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.state.AdminBaseMenuState;
import com.abbos.financetrackerbot.util.factory.SendMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.abbos.financetrackerbot.state.AdminBaseMenuState.*;
import static com.abbos.financetrackerbot.state.AuthenticationState.REQUEST_PASSWORD;
import static com.abbos.financetrackerbot.state.UserBaseMenuState.*;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:21
 **/
@Component
@RequiredArgsConstructor
public class MessageHandler implements Handler {

    private final UserService userService;
    private final UserBaseMenuMProcessor userBaseMenuMProcessor;
    private final AdminBaseMenuMProcessor adminBaseMenuMProcessor;
    private final AdminMenuServicesMProcessor adminMenuServicesMProcessor;
    private final AuthenticationMProcessor authenticationMProcessor;
    private final UserMenuReportsMProcessor userMenuReportsMProcessor;
    private final UserMenuSettingsMProcessor userMenuSettingsMProcessor;
    private final SendMessageFactory sendMessageFactory;

    @Override
    public BotApiMethod<?> handle(Update update) {
        final var message = update.getMessage();
        final var text = message.getText();
        final var user = message.getFrom();
        final var chatId = user.getId();
        Optional<User> telegramUser = userService.findByChatId(chatId);
        if (telegramUser.isPresent()) {
            User tUser = telegramUser.get();
            String state = tUser.getState();
            Language language = tUser.getLanguage();
            return switch (state) {
                case REQUEST_PASSWORD -> authenticationMProcessor.process(update, state);
                case USER_ON_BASE_MENU -> userBaseMenuMProcessor.process(update, state);
                case USER_BASE_MENU_STATE_REPORTS,
                     USER_MENU_REPORTS_STATE_ADDITIONAL_REPORTS,
                     USER_MENU_REPORTS_STATE_MONTHLY_EXPENSES,
                     USER_MENU_REPORTS_STATE_MONTHLY_INCOME -> userMenuReportsMProcessor.process(update, state);
                case USER_BASE_MENU_STATE_SETTINGS,
                     USER_MENU_SETTINGS_CHANGE_PASSWORD,
                     USER_MENU_SETTINGS_CHANGE_PROFILE_INFORMATION,
                     USER_MENU_SETTINGS_CHANGE_LANGUAGE,
                     USER_MENU_SETTINGS_CHANGE_VIEW_CURRENT_ACCESS_RIGHTS ->
                        userMenuSettingsMProcessor.process(update, state);
                case ADMIN_ON_BASE_MENU,
                     ADMIN_BASE_MENU_STATE_CURRENCY_TYPES,
                     ADMIN_BASE_MENU_STATE_TRANSACTION_TYPES,
                     ADMIN_BASE_MENU_STATE_EXPENSE_CATEGORIES,
                     ADMIN_BASE_MENU_STATE_INCOME_CATEGORIES,
                     ADMIN_BASE_MENU_STATE_NOTE,
                     ADMIN_BASE_MENU_STATE_FILE,
                     ADMIN_BASE_MENU_STATE_DATE,
                     ADMIN_BASE_MENU_STATE_CLIENTS,
                     ADMIN_BASE_MENU_STATE_TRANSACTION_STATUES -> adminBaseMenuMProcessor.process(update, state);
                case ADMIN_BASE_MENU_STATE_SERVICE_TYPES,
                     ADMIN_MENU_SERVICE_WEBSITE,
                     ADMIN_MENU_SERVICE_SMM,
                     ADMIN_MENU_SERVICE_CONTEXT_ADS,
                     ADMIN_MENU_SERVICE_TARGET_ADS,
                     ADMIN_MENU_SERVICE_BRANDING,
                     ADMIN_MENU_SERVICE_SEO,
                     ADMIN_MENU_SERVICE_BOT,
                     ADMIN_MENU_SERVICE_OTHER -> adminMenuServicesMProcessor.process(update, state);
                default -> unsupported(chatId, language);
            };
        }
        return userOnBeginning(user, chatId);
    }

    private BotApiMethod<?> userOnBeginning(org.telegram.telegrambots.meta.api.objects.User user, Long chatId) {
        userService.save(new User().toBuilder()
                .fullname(user.getFirstName() + " " + user.getLastName())
                .chatId(chatId)
                .build());
        User tgUser = userService.findByChatId(chatId).orElse(null);
        assert tgUser != null;
        return sendMessageFactory.sendMessageRequestPass(String.valueOf(chatId), tgUser.getLanguage());
    }

    private SendMessage unsupported(Long chatID, Language language) {
        com.abbos.financetrackerbot.entity.User user = getUserByChatId(chatID);
        Role role = user.getRole();
        final var state = role.equals(Role.USER) ? USER_ON_BASE_MENU : AdminBaseMenuState.ADMIN_ON_BASE_MENU;
        updateUserState(user, state);
        return role.equals(Role.USER)
                ? sendMessageFactory.sendMessageUserMenu(String.valueOf(chatID), language)
                : sendMessageFactory.sendMessageAdminMenu(String.valueOf(chatID), language);
    }

    private void updateUserState(com.abbos.financetrackerbot.entity.User user, String state) {
        user.setState(state);
        userService.update(user);
    }

    private com.abbos.financetrackerbot.entity.User getUserByChatId(Long chatId) {
        return userService.findByChatId(chatId)
                .orElseThrow(() -> new IllegalStateException("User not found for chat ID: " + chatId));
    }
}
