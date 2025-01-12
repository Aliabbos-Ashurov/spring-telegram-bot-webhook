package com.abbos.financetrackerbot.core.config;

import com.abbos.financetrackerbot.handler.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:15
 **/
@Component
public class FinTrackBot extends TelegramWebhookBot {

    private final UpdateHandler updateHandler;

    private final String botUsername;
    private final String botPath;
    private final String botToken;

    public FinTrackBot(TelegramBotConfig botConfig, UpdateHandler updateHandler) {
        botUsername = botConfig.getUsername();
        botPath = botConfig.getUrl();
        botToken = botConfig.getToken();
        this.updateHandler = updateHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateHandler.handle(update);
    }


    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }
}
