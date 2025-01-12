package com.abbos.financetrackerbot.service;

import com.abbos.financetrackerbot.core.exception.TokenExpiredException;
import com.abbos.financetrackerbot.core.exception.UserNotFoundException;
import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.auth.TokenResponseDTO;
import com.abbos.financetrackerbot.repository.UserRepository;
import com.abbos.financetrackerbot.util.JwtTokenUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:20
 **/
@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public Response<TokenResponseDTO> generateToken(@NotNull String username, @NotNull String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found with username: {0}", username));
        var accessToken = jwtTokenUtils.generateAccessToken(user.getId(), user.getUsername(), user.getRole());
        var refreshToken = jwtTokenUtils.generateRefreshToken(user.getUsername());
        return Response.ok(TokenResponseDTO.of(user.getId(), accessToken, refreshToken));
    }

    public Response<TokenResponseDTO> refreshToken(@NotNull String refreshToken) {
        if (!jwtTokenUtils.validateToken(refreshToken))
            throw new TokenExpiredException("Token has expired or invalid: {0}", refreshToken);
        var username = jwtTokenUtils.extractUsername(refreshToken);
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: {0}", username));
        if (user == null)
            throw new UserNotFoundException("User not found: {0}", username);
        var newAccessToken = jwtTokenUtils.generateAccessToken(user.getId(), user.getUsername(), user.getRole());
        var newRefreshToken = jwtTokenUtils.generateRefreshToken(user.getUsername());
        return Response.ok(TokenResponseDTO.of(user.getId(), newAccessToken, newRefreshToken));
    }
}
