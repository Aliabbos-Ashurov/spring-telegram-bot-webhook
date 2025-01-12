package com.abbos.financetrackerbot.mapper;

import com.abbos.financetrackerbot.domain.dto.ClientCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ClientResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ClientUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:29
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper extends GenericMapper<Client, ClientResponseDTO, ClientCreateDTO, ClientUpdateDTO> {
}
