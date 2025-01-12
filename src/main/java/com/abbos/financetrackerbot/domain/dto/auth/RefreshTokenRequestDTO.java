package com.abbos.financetrackerbot.domain.dto.auth;

import com.abbos.financetrackerbot.domain.marker.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:52
 **/
public record RefreshTokenRequestDTO(
        @JsonProperty("refresh_token")
        @NotBlank @NotNull String refreshToken
) implements Request {
}