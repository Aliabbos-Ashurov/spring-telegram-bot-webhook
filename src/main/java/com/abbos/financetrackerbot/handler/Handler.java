package com.abbos.financetrackerbot.handler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Aliabbos Ashurov
 * @since 08/January/2025  21:20
 **/
@FunctionalInterface
public interface Handler {
    BotApiMethod<?> handle(Update update);
}
