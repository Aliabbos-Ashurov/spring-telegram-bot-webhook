package com.abbos.financetrackerbot.core.exception;

import com.abbos.financetrackerbot.util.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:40
 **/
public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message, Object... args) {
        super(HttpStatus.CONFLICT, ErrorCode.USER_ALREADY_EXIST, message, args);
    }
}
