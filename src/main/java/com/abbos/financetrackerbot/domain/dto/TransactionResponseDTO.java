package com.abbos.financetrackerbot.domain.dto;

import com.abbos.financetrackerbot.domain.marker.Response;
import com.abbos.financetrackerbot.enums.ExpenseCategories;
import com.abbos.financetrackerbot.enums.IncomeSource;
import com.abbos.financetrackerbot.enums.transaction.CurrencyType;
import com.abbos.financetrackerbot.enums.transaction.MoneyType;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:45
 **/
public record TransactionResponseDTO(
        @JsonProperty("from_wallet")
        @NotBlank @NotNull String fromWallet,
        @JsonProperty("to_wallet")
        @NotBlank @NotNull String toWallet,
        @NotNull BigDecimal amount,
        @JsonProperty("money_type")
        @NotNull MoneyType moneyType,
        @JsonProperty("currency_type")
        @NotNull CurrencyType currencyType,
        @JsonProperty("expense_category")
        @NotNull ExpenseCategories expenseCategory,
        @JsonProperty("income_source")
        @NotNull IncomeSource incomeSource,
        @JsonProperty("transaction_type")
        @NotNull TransactionType transactionType,
        @JsonProperty("created_at")
        @NotNull LocalDateTime createdAt
) implements Response {
}
