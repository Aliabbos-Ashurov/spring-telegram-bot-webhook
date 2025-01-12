package com.abbos.financetrackerbot.domain.entity;

import com.abbos.financetrackerbot.enums.ExpenseCategories;
import com.abbos.financetrackerbot.enums.IncomeSource;
import com.abbos.financetrackerbot.enums.transaction.CurrencyType;
import com.abbos.financetrackerbot.enums.transaction.MoneyType;
import com.abbos.financetrackerbot.enums.transaction.TransactionStatus;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  14:11
 **/
@Entity
@Table(name = "transaction", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_transaction_type", columnList = "transaction_type"),
        @Index(name = "idx_transaction_status", columnList = "transaction_status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Transaction extends BaseEntity {

    @Pattern(regexp = "\\d{16}", message = "Wallet must be a 16-digit number")
    @Column(name = "from_wallet", nullable = false, updatable = false, length = 16)
    private String fromWallet;

    @Pattern(regexp = "\\d{16}", message = "Wallet must be a 16-digit number")
    @Column(name = "to_wallet", nullable = false, updatable = false, length = 16)
    private String toWallet;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, updatable = false)
    private TransactionType transactionType;

    @DecimalMin("0.0")
    @Column(nullable = false, precision = 38, scale = 8)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false, updatable = false)
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "money_type", nullable = false, updatable = false)
    private MoneyType moneyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category", nullable = true)
    private ExpenseCategories expenseCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_source", nullable = true)
    private IncomeSource incomeSource;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "error_message")
    private String errorMessage;

    @OneToOne
    @JoinColumn(name = "file", referencedColumnName = "id", updatable = false)
    private Upload file;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

}
