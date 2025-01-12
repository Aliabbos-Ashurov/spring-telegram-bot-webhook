package com.abbos.financetrackerbot.service;


import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.entity.BaseEntity;
import com.abbos.financetrackerbot.domain.marker.DTO;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:03
 **/
public interface GenericQueryService<
        ID extends Serializable,
        E extends BaseEntity,
        R extends DTO
        > {

    Response<R> find(@NotNull ID id);

    Response<List<R>> findAll();

    default <T> T find(@NotNull ID id, @NotNull Class<T> clazz) {
        return null;
    }
}
