package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.core.exception.ResourceNotFoundException;
import com.abbos.financetrackerbot.domain.dto.ClientCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ClientResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ClientUpdateDTO;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.entity.Client;
import com.abbos.financetrackerbot.mapper.ClientMapper;
import com.abbos.financetrackerbot.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:22
 **/
@Service
public class ClientServiceImpl extends AbstractService<ClientRepository, ClientMapper> implements ClientService {

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Response<ClientResponseDTO> findByPhoneNumber(String phoneNumber) {
        return Response.ok(mapper.toDTO(repository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with phone number: {0}", phoneNumber))));
    }

    @Override
    public Response<List<ClientResponseDTO>> findByServiceType(String serviceType) {
        List<Client> clients = repository.findByServiceType(serviceType);
        return Response.ok(clients.stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public Response<ClientResponseDTO> create(ClientCreateDTO dto) {
        Client newClient = mapper.fromCreate(dto);
        return Response.ok(mapper.toDTO(repository.save(newClient)));
    }

    @Override
    public Response<ClientResponseDTO> find(Long id) {
        return Response.ok(
                mapper.toDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found with id: {0}", id))));
    }

    @Override
    public Response<List<ClientResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public Response<Boolean> update(ClientUpdateDTO dto) {
        return null;
    }

    @Override
    public Response<Boolean> delete(Long id) {
        return null;
    }
}
