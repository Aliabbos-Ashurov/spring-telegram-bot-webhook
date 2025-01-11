package com.abbos.financetrackerbot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  16:15
 **/
@Getter
@AllArgsConstructor
public enum Permission {

    // USER
    VIEW_REPORTS("VIEW_REPORTS"),
    EDIT_PROFILE("EDIT_PROFILE"),
    VIEW_ACCESS_RIGHTS("VIEW_ACCESS_RIGHTS"),
    CHANGE_PASSWORD("CHANGE_PASSWORD"),
    CHANGE_LANGUAGE("CHANGE_LANGUAGE"),

    // ADMIN
    MANAGE_USERS("MANAGE_USERS"),
    MANAGE_REPORTS("MANAGE_REPORTS"),
    CHANGE_SYSTEM_SETTINGS("CHANGE_SYSTEM_SETTINGS");

    private final String access;
}
