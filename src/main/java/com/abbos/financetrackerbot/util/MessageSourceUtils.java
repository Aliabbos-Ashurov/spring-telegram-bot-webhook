package com.abbos.financetrackerbot.util;

import com.abbos.financetrackerbot.enums.Language;
import lombok.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:53
 **/
@Component
public class MessageSourceUtils implements MessageSourceAware, Util {

    private MessageSource messageSource;

    @Override
    public void setMessageSource(@NonNull MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(@NonNull String key, Language language) {
        return messageSource.getMessage(key, null, Locale.forLanguageTag(language.getCode()));
    }
}
