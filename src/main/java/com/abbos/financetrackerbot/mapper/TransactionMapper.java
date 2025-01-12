package com.abbos.financetrackerbot.mapper;

import com.abbos.financetrackerbot.domain.dto.TransactionCreateDTO;
import com.abbos.financetrackerbot.domain.dto.TransactionResponseDTO;
import com.abbos.financetrackerbot.domain.dto.TransactionUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  14:46
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper
        extends GenericMapper<Transaction, TransactionResponseDTO, TransactionCreateDTO, TransactionUpdateDTO> {

}
