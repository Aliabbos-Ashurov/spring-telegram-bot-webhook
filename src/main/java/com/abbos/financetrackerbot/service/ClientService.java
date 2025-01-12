package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.dto.ClientCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ClientResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ClientUpdateDTO;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.entity.Client;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:21
 **/
public interface ClientService extends GenericCrudService<Long, Client, ClientResponseDTO, ClientCreateDTO, ClientUpdateDTO> {

    Response<ClientResponseDTO> findByPhoneNumber(@NotNull String phoneNumber);

    Response<List<ClientResponseDTO>> findByServiceType(String serviceType);
}
