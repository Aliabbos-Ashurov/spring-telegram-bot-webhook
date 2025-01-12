package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.enums.Language;
import com.abbos.financetrackerbot.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  18:52
 **/
public record TgUserResponseDTO(
        @NonNull @NotBlank String fullname,
        @NonNull @NotBlank Role role,
        @NonNull @NotBlank Language language
) {
}
