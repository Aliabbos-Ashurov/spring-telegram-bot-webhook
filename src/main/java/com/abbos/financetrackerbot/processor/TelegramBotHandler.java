package com.abbos.financetrackerbot.processor;

import com.abbos.financetrackerbot.entity.User;
import com.abbos.financetrackerbot.enums.Language;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  19:33
 **/
public interface TelegramBotHandler {
    BotApiMethod<?> redirectToBaseMenu(Long chatId, User user);

    BotApiMethod<?> redirectToBack(Long chatId, User user);

    boolean isActionBack(String text, Language language);

    User getUserByChatId(Long chatId);

    Language getUserLanguage(Long chatId);

    void updateUserState(User user, String state);

    boolean checkLocalizedMessage(String key, String message, Language language);

    SendMessage unsupported(Long chatId, Language language);
}
