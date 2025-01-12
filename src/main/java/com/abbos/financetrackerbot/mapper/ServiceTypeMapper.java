package com.abbos.financetrackerbot.mapper;

import com.abbos.financetrackerbot.domain.dto.ServiceTypeCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:29
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceTypeMapper extends GenericMapper<ServiceType, ServiceTypeResponseDTO, ServiceTypeCreateDTO, ServiceTypeUpdateDTO> {

}
