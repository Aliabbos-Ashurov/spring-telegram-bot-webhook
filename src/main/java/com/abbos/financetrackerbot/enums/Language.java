package com.abbos.financetrackerbot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:50
 **/
@Getter
@RequiredArgsConstructor
public enum Language {
    ENG("en"),
    UZ("uz"),
    RU("ru");

    private final String code;

}
