package com.abbos.financetrackerbot.controller.web;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.ServiceTypeResponseDTO;
import com.abbos.financetrackerbot.service.ServiceTypeService;
import com.abbos.financetrackerbot.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  16:27
 **/
@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/service-type")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @Operation(summary = "Get all service types")
    @GetMapping("/get")
    public ResponseEntity<Response<List<ServiceTypeResponseDTO>>> getAllServiceTypes() {
        return ResponseEntity.ok(serviceTypeService.findAll());
    }

    @Operation(summary = "Get service type by name")
    @GetMapping("/get/{name}")
    public ResponseEntity<Response<ServiceTypeResponseDTO>> getServiceTypeByName(@PathVariable String name) {
        return ResponseEntity.ok(serviceTypeService.findByName(name));
    }
}
