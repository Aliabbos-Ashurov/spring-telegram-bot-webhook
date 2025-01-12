package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.enums.transaction.CurrencyType;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  15:02
 **/
public record TransactionFilterDTO(
        Long userId,
        TransactionType transactionType,
        CurrencyType currencyType,
        Boolean sortASC
) {
}
