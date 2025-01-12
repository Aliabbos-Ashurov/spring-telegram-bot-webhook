package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.core.config.security.SessionUser;
import com.abbos.financetrackerbot.core.exception.UserNotFoundException;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.user.UserCreateDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserResponseDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserUpdateDTO;
import com.abbos.financetrackerbot.domain.entity.User;
import com.abbos.financetrackerbot.mapper.UserMapper;
import com.abbos.financetrackerbot.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:05
 **/
@Service
public class UserServiceImpl extends AbstractService<UserRepository, UserMapper> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final SessionUser sessionUser;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder, SessionUser sessionUser) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
        this.sessionUser = sessionUser;
    }


    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found with username: {0}", username));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(User user) {
        repository.save(user);
    }

    @Override
    public Response<UserResponseDTO> findMe() {
        return find(sessionUser.id());
    }

    @Override
    public Response<UserResponseDTO> create(UserCreateDTO dto) {
        User newUser = mapper.fromCreate(dto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return Response.ok(mapper.toDTO(save(newUser)));
    }

    @Override
    public Response<Boolean> update(UserUpdateDTO dto) {
        return null;
    }

    @Override
    public Response<Boolean> delete(Long id) {
        return null;
    }

    @Override
    public Response<UserResponseDTO> find(Long id) {
        return Response.ok(
                mapper.toDTO(repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: {0}", id))));
    }

    @Override
    public Response<List<UserResponseDTO>> findAll() {
        return Response.ok(repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @Override
    public Optional<User> findByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }
}
