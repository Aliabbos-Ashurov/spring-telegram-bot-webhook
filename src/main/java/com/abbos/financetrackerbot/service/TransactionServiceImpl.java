package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.core.config.security.SessionUser;
import com.abbos.financetrackerbot.domain.dto.*;
import com.abbos.financetrackerbot.domain.entity.Transaction;
import com.abbos.financetrackerbot.enums.transaction.TransactionStatus;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;
import com.abbos.financetrackerbot.mapper.TransactionMapper;
import com.abbos.financetrackerbot.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:57
 **/
@Service
public class TransactionServiceImpl extends AbstractService<TransactionRepository, TransactionMapper>
        implements TransactionService {

    private final UserService userService;
    private final SessionUser sessionUser;

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper,
                                  UserService userService, SessionUser sessionUser) {
        super(repository, mapper);
        this.userService = userService;
        this.sessionUser = sessionUser;
    }

    @Override
    public Response<List<TransactionResponseDTO>> findByUserIdAndStatus(Long userId, TransactionStatus status) {
        List<Transaction> transactions = repository.findByUserIdAndStatus(userId, status);
        return Response.ok(transactions.stream().map(mapper::toDTO).toList());
    }

    @Override
    public Response<List<TransactionResponseDTO>> findByUserIdAndTransactionType(Long userId, TransactionType transactionType) {
        List<Transaction> transactions = repository.findByUserIdAndTransactionType(userId, transactionType);
        return Response.ok(transactions.stream().map(mapper::toDTO).toList());
    }

    public Response<List<TransactionResponseDTO>> getTransactionsByFilters(TransactionFilterDTO filterDTO) {
        List<Transaction> transactions = repository.findByFilters(
                filterDTO.userId(),
                filterDTO.transactionType(),
                filterDTO.currencyType(),
                filterDTO.sortASC()
        );
        List<TransactionResponseDTO> transactionDTOs = transactions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(transactionDTOs);
    }


    @Transactional
    @Override
    public Response<TransactionResponseDTO> create(TransactionCreateDTO dto) {
        Transaction transaction = mapper.fromCreate(dto);
        transaction.setUser(userService.findById(sessionUser.id()));
        return Response.ok(mapper.toDTO(repository.save(transaction)));
    }

    @Override
    public Response<Boolean> update(TransactionUpdateDTO dto) {
        return null;
    }

    @Override
    public Response<Boolean> delete(Long id) {
        return null;
    }

    @Override
    public Response<TransactionResponseDTO> find(Long id) {
        return Response.ok(mapper.toDTO(repository.findById(id).orElse(null)));
    }

    @Override
    public Response<List<TransactionResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream().map(mapper::toDTO).toList());
    }
}
