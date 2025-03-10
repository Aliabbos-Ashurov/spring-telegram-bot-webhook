package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.entity.BaseEntity;
import com.abbos.financetrackerbot.domain.marker.DTO;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:04
 **/
public interface GenericCrudService<
        ID extends Serializable,
        E extends BaseEntity,
        R extends Response,
        CD extends DTO,
        UD extends DTO
        > extends GenericService, GenericQueryService<ID, E, R> {

    Response<R> create(@NotNull CD dto);

    Response<Boolean> update(@NotNull UD dto);

    Response<Boolean> delete(@NotNull ID id);

    //NOTE: add
}
