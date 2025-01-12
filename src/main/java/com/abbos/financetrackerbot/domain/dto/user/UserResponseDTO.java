package com.abbos.financetrackerbot.domain.dto.user;

import com.abbos.financetrackerbot.domain.marker.Response;
import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:55
 **/
@JsonPropertyOrder({"id", "fullname", "username", "email", "phone", "isPremium", "twoFactorEnabled", "notificationsEnabled"})
public record UserResponseDTO(
        @NotBlank @NotNull Long id,
        @NotBlank @NotNull String fullname,
        @NotBlank @NotNull String username,
        @NotBlank @NotNull Role role,
        @NotBlank @NotNull Language language,

        @JsonProperty("is_premium")
        @NotNull boolean premium,

        @JsonProperty("two_factor_enabled")
        @NotNull boolean twoFactorEnabled,
        @JsonProperty("notifications_enabled")
        @NotNull boolean notificationsEnabled
) implements Response {
}
