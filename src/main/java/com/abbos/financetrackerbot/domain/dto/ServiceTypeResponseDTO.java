package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.domain.marker.Response;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:24
 **/
public record ServiceTypeResponseDTO(
        Long id,
        String name
) implements Response {
}
