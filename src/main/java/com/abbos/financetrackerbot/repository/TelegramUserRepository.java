package com.abbos.financetrackerbot.repository;

import com.abbos.financetrackerbot.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:08
 **/
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    @Query("""
            FROM TelegramUser tu
            WHERE tu.chatId=:chatId
            AND tu.deleted = FALSE
            """)
    Optional<TelegramUser> findByChatId(Long chatId);
}
