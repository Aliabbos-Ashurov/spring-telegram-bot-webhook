package com.abbos.financetrackerbot.domain.dto.user;

import com.abbos.financetrackerbot.domain.marker.Response;
import com.abbos.financetrackerbot.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:54
 **/
public record UserDTO(
        @NotBlank @NotNull Long id,
        @NotBlank @NotNull String fullname,
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password,
        @NotBlank @NotNull Role role,
        boolean premium
) implements Response {
    public static UserDTO of(Long id, String fullname, String username, String password, Role role) {
        return new UserDTO(id, fullname, username, password, role, Boolean.FALSE);
    }
}
