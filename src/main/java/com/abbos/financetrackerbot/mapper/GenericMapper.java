package com.abbos.financetrackerbot.mapper;

import com.abbos.financetrackerbot.domain.entity.BaseEntity;
import com.abbos.financetrackerbot.domain.marker.Request;
import com.abbos.financetrackerbot.domain.marker.Response;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:45
 **/
public interface GenericMapper<
        E extends BaseEntity,
        R extends Response,
        CD extends Request,
        UD extends Request> extends Mapper {

    E toEntity(R dto);

    R toDTO(E entity);

    E fromCreate(CD dto);

    E fromUpdate(UD dto);
}

