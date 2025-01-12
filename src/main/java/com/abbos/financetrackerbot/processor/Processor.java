package com.abbos.financetrackerbot.processor;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:37
 **/
@FunctionalInterface
public interface Processor {
    BotApiMethod<?> process(Update update, String state);
}
