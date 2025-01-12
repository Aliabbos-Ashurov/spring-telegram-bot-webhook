package com.abbos.financetrackerbot.repository;

import com.abbos.financetrackerbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Aliabbos Ashurov
 * @since 09/January/2025  15:08
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            FROM  User u
            WHERE u.chatId=:chatId
            AND u.deleted = FALSE
            """)
    Optional<User> findByChatId(Long chatId);


    @Query("""
            FROM User u
            WHERE u.deleted = FALSE
            """)
    List<User> findAllActive();
}
