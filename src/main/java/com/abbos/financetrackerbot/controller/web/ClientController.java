package com.abbos.financetrackerbot.controller.web;

import com.abbos.financetrackerbot.domain.dto.ClientResponseDTO;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.service.ClientService;
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
 * @since 12/January/2025  16:26
 **/
@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Get all clients")
    @GetMapping("/get")
    public ResponseEntity<Response<List<ClientResponseDTO>>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @Operation(summary = "Get client by phone number")
    @GetMapping("/get/{phoneNumber}")
    public ResponseEntity<Response<ClientResponseDTO>> getClientByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(clientService.findByPhoneNumber(phoneNumber));
    }
}
