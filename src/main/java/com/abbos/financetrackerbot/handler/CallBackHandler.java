package com.abbos.financetrackerbot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:21
 **/
@Component
public class CallBackHandler implements Handler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        return new SendMessage(update.getMessage().getChatId().toString(), "CALLBACK HANDLER ON UPDATE!");
    }
}
