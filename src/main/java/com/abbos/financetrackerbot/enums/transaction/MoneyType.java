package com.abbos.financetrackerbot.enums.transaction;

import lombok.Getter;

/**
 * @author Aliabbos Ashurov
 * @since 11/January/2025  13:06
 **/
@Getter
public enum MoneyType {
    CASH_AMOUNT,
    CASH_CURRENCY,
    BANK,
    CARD_AMOUNT;
}
