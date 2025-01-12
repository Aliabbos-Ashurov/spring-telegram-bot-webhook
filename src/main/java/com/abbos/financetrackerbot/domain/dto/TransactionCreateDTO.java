package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.domain.marker.Request;
import com.abbos.financetrackerbot.enums.ExpenseCategories;
import com.abbos.financetrackerbot.enums.IncomeSource;
import com.abbos.financetrackerbot.enums.transaction.CurrencyType;
import com.abbos.financetrackerbot.enums.transaction.MoneyType;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:39
 **/
public record TransactionCreateDTO(
        @NotBlank @NotNull String fromWallet,
        @NotBlank @NotNull String toWallet,
        @NotNull BigDecimal amount,
        @NotNull MoneyType moneyType,
        @NotNull CurrencyType currencyType,
        @NotNull ExpenseCategories expenseCategory,
        @NotNull IncomeSource incomeSource,
        @NotNull TransactionType transactionType,
        @NotNull LocalDateTime createdAt
) implements Request {
}
