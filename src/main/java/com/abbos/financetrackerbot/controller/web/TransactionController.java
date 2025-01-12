package com.abbos.financetrackerbot.controller.web;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.TransactionCreateDTO;
import com.abbos.financetrackerbot.domain.dto.TransactionFilterDTO;
import com.abbos.financetrackerbot.domain.dto.TransactionResponseDTO;
import com.abbos.financetrackerbot.service.TransactionService;
import com.abbos.financetrackerbot.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  15:08
 **/
@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Get filtered transactions", description = "Get transactions by applying filters such as user ID, transaction type, status, etc.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtered transactions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/filter")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> getByFilter(@RequestBody TransactionFilterDTO dto) {
        return ResponseEntity.ok(transactionService.getTransactionsByFilters(dto));
    }

    @Operation(summary = "Create a new transaction", description = "Create a new transaction by providing transaction details.")
    @PostMapping("/create")
    public ResponseEntity<Response<TransactionResponseDTO>> create(@RequestBody TransactionCreateDTO dto) {
        System.out.println(dto);
        return ResponseEntity.ok(transactionService.create(dto));
    }

    @Operation(summary = "Get all transactions", description = "Retrieve all transactions in the system.")
    @GetMapping("/get")
    public ResponseEntity<Response<List<TransactionResponseDTO>>> getAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }
}
