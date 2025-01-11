package com.abbos.financetrackerbot.enums.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  14:12
 **/
@Getter
@AllArgsConstructor
public enum TransactionStatus {

    PENDING,
    COMPLETED,
    FAILED
}
