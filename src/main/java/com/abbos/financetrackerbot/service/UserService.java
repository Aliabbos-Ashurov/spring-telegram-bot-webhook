package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.user.UserCreateDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserResponseDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.User;
import jakarta.validation.constraints.NotNull;

public interface UserService
        extends GenericTelegramService, GenericCrudService<Long, User, UserResponseDTO, UserCreateDTO, UserUpdateDTO> {

    User findByUsername(@NotNull String username);

    User save(@NotNull User user);

    User findById(@NotNull Long id);

    void updateUser(User user);

    Response<UserResponseDTO> findMe();
}