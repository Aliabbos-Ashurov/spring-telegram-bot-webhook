package com.abbos.financetrackerbot.repository;

import com.abbos.financetrackerbot.domain.entity.Transaction;
import com.abbos.financetrackerbot.enums.transaction.CurrencyType;
import com.abbos.financetrackerbot.enums.transaction.TransactionStatus;
import com.abbos.financetrackerbot.enums.transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:47
 **/
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
                SELECT t
                FROM Transaction t
                WHERE 
                    (:userId IS NULL OR t.user.id = :userId)
                    AND (:transactionType IS NULL OR t.transactionType = :transactionType)
                    AND (:currencyType IS NULL OR t.currencyType = :currencyType)
                ORDER BY 
                    CASE WHEN :sortASC = true THEN t.confirmedAt END ASC,
                    CASE WHEN :sortASC = false THEN t.confirmedAt END DESC
            """)
    List<Transaction> findByFilters(
            @Param("userId") Long userId,
            @Param("transactionType") TransactionType transactionType,
            @Param("currencyType") CurrencyType currencyType,
            @Param("sortASC") Boolean sortASC
    );

    @Query("""
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.transactionStatus = :status
            AND t.deleted = false
            """)
    List<Transaction> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") TransactionStatus status);

    @Query("""
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.transactionType = :transactionType
            AND t.deleted = false
            """)
    List<Transaction> findByUserIdAndTransactionType(@Param("userId") Long userId, @Param("transactionType") TransactionType transactionType);

    @Query("""
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.transactionStatus = :status
            AND t.confirmedAt BETWEEN :startDate AND :endDate
            AND t.deleted = false
            """)
    List<Transaction> findByUserIdStatusAndDateRange(@Param("userId") Long userId,
                                                     @Param("status") TransactionStatus status,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);
}
