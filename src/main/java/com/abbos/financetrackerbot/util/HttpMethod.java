package com.abbos.financetrackerbot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:51
 **/
@Getter
@RequiredArgsConstructor
public class HttpMethod {
    public static final String _GET = "GET";
    public static final String _POST = "POST";
    public static final String _PUT = "PUT";
    public static final String _DELETE = "DELETE";
}