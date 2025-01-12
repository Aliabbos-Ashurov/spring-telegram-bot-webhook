package com.abbos.financetrackerbot.domain.dto.user;


import com.abbos.financetrackerbot.domain.marker.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:54
 **/
public record UserCreateDTO(
        @NotBlank @NotNull String fullname,
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password
) implements Request {
}
