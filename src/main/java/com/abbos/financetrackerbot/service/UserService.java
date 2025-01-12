package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.entity.User;
import com.abbos.financetrackerbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:16
 **/
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository telegramUserRepository;


    public void save(User telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    public User update(User telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    public Optional<User> findByChatId(Long chatId) {
        return telegramUserRepository.findByChatId(chatId);
    }

    public List<User> findAll() {
        return telegramUserRepository.findAllActive();
    }
}
