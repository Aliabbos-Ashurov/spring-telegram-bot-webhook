package com.abbos.financetrackerbot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncomeSource {

    SALARY,
    DIVIDENDS,
    INVESTMENTS,
    FREELANCING,
    RENTAL_INCOME,
    OTHER;
}