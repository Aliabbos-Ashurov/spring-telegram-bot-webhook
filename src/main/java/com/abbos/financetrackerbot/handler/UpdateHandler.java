package com.abbos.financetrackerbot.handler;

import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.util.factory.SendMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:23
 **/
@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final CallBackHandler callBackHandler;
    private final MessageHandler messageHandler;
    private final SendMessageFactory sendMessageFactory;

    public BotApiMethod<?> handle(Update update) {
        if (Objects.nonNull(update.getMessage())) {
            return messageHandler.handle(update);
        } else if (Objects.nonNull(update.getCallbackQuery())) {
            return callBackHandler.handle(update);
        }
        return sendMessageFactory.sendMessageSmtWrong(update.getMessage().getChatId().toString(), Language.ENG);
    }
}
