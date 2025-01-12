package com.abbos.financetrackerbot.processor.message.user;

import com.abbos.financetrackerbot.domain.dto.TgUserResponseDTO;
import com.abbos.financetrackerbot.domain.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.processor.Processor;
import com.abbos.financetrackerbot.processor.TelegramBotHandler;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.state.AdminBaseMenuState;
import com.abbos.financetrackerbot.state.AuthenticationState;
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
 * @since 11/January/2025  15:03
 **/
@Component
@RequiredArgsConstructor
public class UserMenuSettingsMProcessor implements TelegramBotHandler, Processor {
    private final UserService userService;
    private final MessageSourceUtils messageSourceUtils;
    private final SendMessageFactory sendMessageFactory;

    @Override
    public BotApiMethod<?> process(Update update, String state) {
        var message = update.getMessage();
        var chatId = message.getFrom().getId();
        var text = message.getText();

        User user = getUserByChatId(chatId);
        Language language = user.getLanguage();

        return switch (user.getState()) {
            case USER_BASE_MENU_STATE_SETTINGS -> handleStateSettings(text, chatId);
            case USER_MENU_SETTINGS_CHANGE_VIEW_CURRENT_ACCESS_RIGHTS -> viewAccessRights(text, user, chatId, language);
            case USER_MENU_SETTINGS_CHANGE_PASSWORD -> updatePassword(text, user, chatId, language);
            case USER_MENU_SETTINGS_CHANGE_LANGUAGE -> changeLanguage(text, user, chatId, language);
            case USER_MENU_SETTINGS_CHANGE_PROFILE_INFORMATION -> profileInformation(text, user, chatId, language);
            default -> unsupported(chatId, language);
        };
    }

    private BotApiMethod<?> handleStateSettings(String text, Long chatId) {
        User user = getUserByChatId(chatId);
        Language language = getUserLanguage(chatId);
        if (isActionBack(text, language)) return redirectToBaseMenu(chatId, user);

        if (checkLocalizedMessage("menu.user.settings.profile.edit", text, language))
            return handleProfileEdit(user, chatId, language);
        else if (checkLocalizedMessage("menu.user.settings.access.rights.view", text, language))
            return handleAccessRights(user, chatId, language);
        else if (checkLocalizedMessage("menu.user.settings.password.change", text, language))
            return handlePasswordChange(user, chatId, language);
        else if (checkLocalizedMessage("menu.user.settings.language.change", text, language))
            return handleChangeLanguage(user, chatId, language);

        return unsupported(chatId, language);
    }

    private BotApiMethod<?> handleProfileEdit(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_SETTINGS_CHANGE_PROFILE_INFORMATION);
        return sendMessageFactory.sendMessageProfileInfo(String.valueOf(chatId), language,
                new TgUserResponseDTO(user.getFullname(), user.getRole(), user.getLanguage()));
    }

    private BotApiMethod<?> handleAccessRights(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_SETTINGS_CHANGE_VIEW_CURRENT_ACCESS_RIGHTS);
        return sendMessageFactory.sendMessageAccessRightsInfo(String.valueOf(chatId), language, user.getRole());
    }

    private BotApiMethod<?> handlePasswordChange(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_SETTINGS_CHANGE_PASSWORD);
        return sendMessageFactory.sendMessageNewPass(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> handleChangeLanguage(User user, Long chatId, Language language) {
        updateUserState(user, USER_MENU_SETTINGS_CHANGE_LANGUAGE);
        return sendMessageFactory.sendMessageChooseLang(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> profileInformation(String text, User user, Long chatId, Language language) {
        if (isActionBack(text, language)) return redirectToBack(chatId, user);
        else return unsupported(chatId, language);
    }

    private BotApiMethod<?> viewAccessRights(String text, User user, Long chatId, Language language) {
        if (isActionBack(text, language)) return redirectToBack(chatId, user);
        else return unsupported(chatId, language);
    }

    private BotApiMethod<?> updatePassword(String newPassword, User user, Long chatId, Language language) {
        updateUserState(user, AuthenticationState.REQUEST_PASSWORD);
        user.setPassword(newPassword);
        userService.updateUser(user);
        return sendMessageFactory.sendMessageRequestPassAftNew(String.valueOf(chatId), language);
    }

    private BotApiMethod<?> changeLanguage(String text, User user, Long chatId, Language language) {
        updateUserState(user, USER_BASE_MENU_STATE_SETTINGS);
        Language newLanguage = determineLanguage(text, language);
        user.setLanguage(newLanguage);
        userService.updateUser(user);
        return sendMessageFactory.sendMessageLangSuccess(String.valueOf(chatId), newLanguage);
    }

    private Language determineLanguage(String text, Language currentLanguage) {
        if (checkLocalizedMessage("settings.lang.eng", text, currentLanguage)) return Language.ENG;
        if (checkLocalizedMessage("settings.lang.ru", text, currentLanguage)) return Language.RU;
        if (checkLocalizedMessage("settings.lang.uz", text, currentLanguage)) return Language.UZ;
        return currentLanguage;
    }

    @Override
    public BotApiMethod<?> redirectToBack(Long chatId, User user) {
        updateUserState(user, USER_BASE_MENU_STATE_SETTINGS);
        return sendMessageFactory.sendMessageUserMenuSettings(String.valueOf(chatId), user.getLanguage());
    }

    @Override
    public BotApiMethod<?> redirectToBaseMenu(Long chatId, User user) {
        updateUserState(user, USER_ON_BASE_MENU);
        return sendMessageFactory.sendMessageUserMenu(String.valueOf(chatId), user.getLanguage());
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
        String localizedMessage = messageSourceUtils.getLocalizedMessage(key, language);
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
