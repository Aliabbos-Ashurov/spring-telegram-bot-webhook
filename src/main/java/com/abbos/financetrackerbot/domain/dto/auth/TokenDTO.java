package com.abbos.financetrackerbot.domain.dto.auth;

import com.abbos.financetrackerbot.domain.marker.Response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:51
 **/
@JsonPropertyOrder({"token", "issuedAt", "expiredAt", "expiresIn"})
public record TokenDTO(
        @NotBlank @NotNull String token,
        @JsonProperty("issued_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @NotNull LocalDateTime issuedAt,

        @JsonProperty("expired_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        @NotNull LocalDateTime expiredAt,

        @JsonProperty("expires_in")
        @NotNull Long expiresIn
) implements Response {
}
