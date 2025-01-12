package com.abbos.financetrackerbot.core.exception;

import com.abbos.financetrackerbot.util.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:41
 **/
public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message, Object... args) {
        super(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, message, args);
    }
}
