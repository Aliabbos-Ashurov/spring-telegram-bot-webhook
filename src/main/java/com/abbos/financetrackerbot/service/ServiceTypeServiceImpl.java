package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.core.exception.ResourceNotFoundException;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeCreateDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeResponseDTO;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.ServiceType;
import com.abbos.financetrackerbot.mapper.ServiceTypeMapper;
import com.abbos.financetrackerbot.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:25
 **/
@Service
public class ServiceTypeServiceImpl extends AbstractService<ServiceTypeRepository, ServiceTypeMapper> implements ServiceTypeService {

    public ServiceTypeServiceImpl(ServiceTypeRepository repository, ServiceTypeMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Response<ServiceTypeResponseDTO> findByName(String name) {
        return Response.ok(mapper.toDTO(repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Service type not found with name: {0}", name))));
    }

    @Override
    public Response<ServiceTypeResponseDTO> create(ServiceTypeCreateDTO dto) {
        ServiceType newServiceType = mapper.fromCreate(dto);
        return Response.ok(mapper.toDTO(repository.save(newServiceType)));
    }

    @Override
    public Response<ServiceTypeResponseDTO> find(Long id) {
        return Response.ok(
                mapper.toDTO(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: {0}", id))));
    }

    @Override
    public Response<List<ServiceTypeResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public Response<Boolean> update(ServiceTypeUpdateDTO dto) {
        return null;
    }

    @Override
    public Response<Boolean> delete(Long id) {
        return null;
    }
}
