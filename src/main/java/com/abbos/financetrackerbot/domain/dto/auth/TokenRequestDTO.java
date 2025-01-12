package com.abbos.financetrackerbot.domain.dto.auth;

import com.abbos.financetrackerbot.domain.marker.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:53
 **/
public record TokenRequestDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password
) implements Request {
}
