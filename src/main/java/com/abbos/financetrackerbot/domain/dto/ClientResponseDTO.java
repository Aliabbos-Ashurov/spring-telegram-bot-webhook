package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.domain.marker.Response;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:21
 **/
public record ClientResponseDTO(
        Long id,
        String fullName,
        String phoneNumber,
        String serviceType
) implements Response {
}
