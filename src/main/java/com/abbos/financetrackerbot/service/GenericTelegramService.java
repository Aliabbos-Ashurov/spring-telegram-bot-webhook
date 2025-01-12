package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.entity.User;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:07
 **/
public interface GenericTelegramService {

    Optional<User> findByChatId(Long chatId);
}
