package com.abbos.financetrackerbot.core.exception;

import com.abbos.financetrackerbot.util.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:39
 **/
@Getter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, Object... args) {
        super(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND, message, args);
    }
}
