package com.abbos.financetrackerbot.util.factory;

import com.abbos.financetrackerbot.dto.UserResponseDTO;
import com.abbos.financetrackerbot.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.util.MessageSourceUtils;
import com.abbos.financetrackerbot.util.Util;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:49
 **/
@Component
@RequiredArgsConstructor
public class SendMessageFactory implements Util {

    private final UserService userService;
    private final MessageSourceUtils messageSourceUtils;
    private final ReplyKeyboardMarkupFactory replyKeyboardMarkupFactory;


    public SendMessage sendMessageBackButton(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("button.back", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createBackButton(language));
    }

    public SendMessage sendMessageNewPass(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("alert.new.pass", language);
        return new SendMessage(chatID, message);
    }

    public SendMessage sendMessageSuccess(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("alert.success", language);
        return new SendMessage(chatID, message);
    }

    public SendMessage sendMessageChooseLang(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createChooseLangButtons(language));
    }

    public SendMessage sendMessageSmtWrong(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("alert.smt.wrong", language);
        return new SendMessage(chatID, message);
    }

    public SendMessage sendMessageUserMenuReports(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createUserMenuReportsButtons(language));
    }

    public SendMessage sendMessageUserMenuSettings(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createUserMenuSettingsButtons(language));
    }


    public SendMessage sendMessagePassWrong(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("alert.password.wrong", language);
        return new SendMessage(chatID, message);
    }

    public SendMessage sendMessageRequestPass(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("request.password", language);
        return new SendMessage(chatID, message);
    }

    public SendMessage sendMessageAccessRightsInfo(String chatID, Language language, Role role) {
        if (role.equals(Role.USER)) {
            final var accessRights = getLocalizedMessages(language,
                    "menu.reports",
                    "menu.settings",
                    "menu.user.settings.profile.edit",
                    "menu.user.settings.access.rights.view",
                    "menu.user.settings.password.change",
                    "menu.user.settings.language.change");
            return createMessageRKeyboard(chatID, accessRights, replyKeyboardMarkupFactory.createBackButton(language));
        }
        //TODO: add ADMIN access rights!
        return createMessageRKeyboard(chatID, "[ NEED UPDATES ]", replyKeyboardMarkupFactory.createBackButton(language));
    }

    public SendMessage sendMessageAdminServiceType(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createAdminServiceTypeButtons(language));
    }

    public SendMessage sendMessageAdminCustomersInfo(String chatID, Language language) {
        final var apiMethod = new SendMessage(chatID, buildCustomersInfo(language));
        apiMethod.setReplyMarkup(replyKeyboardMarkupFactory.createBackButton(language));
        return apiMethod;
    }

    public SendMessage sendMessageRequestPassAftNew(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("request.password", language);
        final var success = messageSourceUtils.getLocalizedMessage("alert.success", language);
        return new SendMessage(chatID, success + " " + message);
    }

    public SendMessage sendMessageLangSuccess(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("alert.success", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createUserMenuSettingsButtons(language));
    }

    public SendMessage sendMessageProfileInfo(String chatID, Language language,
                                              UserResponseDTO data) {
        return createMessageRKeyboard(chatID, buildUserData(language, data), replyKeyboardMarkupFactory.createBackButton(language));
    }

    public SendMessage sendMessageUserMenu(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createUserBaseMenuButtons(language));
    }

    public SendMessage sendMessageAdminMenu(String chatID, Language language) {
        final var message = messageSourceUtils.getLocalizedMessage("menu.choose", language);
        return createMessageRKeyboard(chatID, message, replyKeyboardMarkupFactory.createAdminBaseMenuButtons(language));
    }

    public String buildUserData(Language language, UserResponseDTO data) {
        final var name = messageSourceUtils.getLocalizedMessage("user.info.name", language);
        final var role = messageSourceUtils.getLocalizedMessage("user.info.role", language);
        final var lang = messageSourceUtils.getLocalizedMessage("user.info.language", language);
        return name + ": " + data.fullname() + "\n" +
                role + ": " + data.role() + "\n" +
                lang + ": " + data.language() + "\n";
    }

    public String buildCustomersInfo(Language language) {
        List<User> users = userService.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        users.forEach(user -> stringBuilder.append(
                buildUserData(language, new UserResponseDTO(user.getFullname(), user.getRole(), user.getLanguage()))
        ).append("\n"));
        return stringBuilder.toString();
    }

    public String getLocalizedMessages(@NonNull Language language, @NonNull String... keys) {
        return Arrays.stream(keys)
                .map(k -> messageSourceUtils.getLocalizedMessage(k, language))
                .collect(Collectors.joining("\n✅ ", "✅ ", ""));
    }

    private SendMessage createMessageRKeyboard(String chatID, String messageText, ReplyKeyboard replyKeyboard) {
        final var message = new SendMessage(chatID, messageText);
        message.setReplyMarkup(replyKeyboard);
        return message;
    }

    private SendMessage createMessageIKeyboard(String chatID, String messageText, InlineKeyboardMarkup inlineKeyboardMarkup) {
        final var message = new SendMessage(chatID, messageText);
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }
}
