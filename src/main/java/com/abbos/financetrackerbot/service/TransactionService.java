package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.dto.*;
import com.abbos.financetrackerbot.domain.entity.Transaction;
import com.abbos.financetrackerbot.enums.transaction.TransactionStatus;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:51
 **/
public interface TransactionService
        extends GenericCrudService<Long, Transaction, TransactionResponseDTO, TransactionCreateDTO, TransactionUpdateDTO> {

    Response<List<TransactionResponseDTO>> getTransactionsByFilters(TransactionFilterDTO filterDTO);

    Response<List<TransactionResponseDTO>> findByUserIdAndStatus(Long userId, TransactionStatus status);

    Response<List<TransactionResponseDTO>> findByUserIdAndTransactionType(Long userId, TransactionType transactionType);

}
