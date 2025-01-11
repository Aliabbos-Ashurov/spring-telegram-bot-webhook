package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.entity.TelegramUser;
import com.abbos.financetrackerbot.repository.TelegramUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:16
 **/
@Service
@RequiredArgsConstructor
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;


    public void save(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public TelegramUser update(TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    public Optional<TelegramUser> findByChatId(Long chatId) {
        return telegramUserRepository.findByChatId(chatId);
    }
}
