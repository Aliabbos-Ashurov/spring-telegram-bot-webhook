package com.abbos.financetrackerbot.domain.dto.user;

import com.abbos.financetrackerbot.domain.marker.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:56
 **/
@JsonPropertyOrder({"oldPassword", "newPassword"})
public record UserUpdateDTO(
        @JsonProperty("old_password")
        @NotBlank @NotNull String oldPassword,
        @JsonProperty("new_password")
        @NotBlank @NotNull String newPassword
) implements Request {
}
