package com.abbos.financetrackerbot.controller;

import com.abbos.financetrackerbot.config.FinTrackBot;
import com.abbos.financetrackerbot.config.TelegramBotConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:11
 **/
@RestController
@RequestMapping("/api")
public class WebHookController extends DefaultAbsSender {

    private final FinTrackBot finTrackBot;

    public WebHookController(DefaultBotOptions options, FinTrackBot finTrackBot, TelegramBotConfig config) {
        super(options, config.getToken());
        this.finTrackBot = finTrackBot;
    }

    @PostMapping("/update")
    public void onUpdateReceived(@RequestBody Update update) throws TelegramApiException {
        execute(finTrackBot.onWebhookUpdateReceived(update));
    }
}
