package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.ServiceType;
import jakarta.validation.constraints.NotNull;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:24
 **/
public interface ServiceTypeService extends GenericCrudService<Long, ServiceType, ServiceTypeResponseDTO, ServiceTypeCreateDTO, ServiceTypeUpdateDTO> {

    Response<ServiceTypeResponseDTO> findByName(@NotNull String name);
}
