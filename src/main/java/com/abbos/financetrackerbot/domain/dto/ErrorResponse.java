package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.domain.marker.Response;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:41
 **/
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record ErrorResponse(
        String code,
        String message,
        String path,
        String details,
        LocalDateTime timestamp)
        implements Response {

    public static ErrorResponse of(String code, String message, String path) {
        return new ErrorResponse(
                code,
                message,
                path,
                "Contact support for more details.",
                LocalDateTime.now(ZoneId.of("Asia/Tashkent"))
        );
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }
}
