package com.abbos.financetrackerbot.controller.web;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.auth.RefreshTokenRequestDTO;
import com.abbos.financetrackerbot.domain.dto.auth.TokenRequestDTO;
import com.abbos.financetrackerbot.domain.dto.auth.TokenResponseDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserCreateDTO;
import com.abbos.financetrackerbot.domain.dto.user.UserResponseDTO;
import com.abbos.financetrackerbot.service.TokenService;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:17
 **/
@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    @Operation(
            summary = "Generate Access Token",
            description = "Authenticates a user and generates an access token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token generated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credentials for authentication",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TokenRequestDTO.class))
    )
    @PostMapping(value = "/token")
    public ResponseEntity<Response<TokenResponseDTO>> login(@Valid @RequestBody TokenRequestDTO dto) {
        return ResponseEntity.ok(tokenService.generateToken(dto.username(), dto.password()));
    }

    @Operation(
            summary = "Register New User",
            description = "Registers a new user in the system."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Details of the user to be registered",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserCreateDTO.class))
    )
    @PostMapping(value = "/register")
    private ResponseEntity<Response<UserResponseDTO>> register(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Operation(
            summary = "Refresh Access Token",
            description = "Refreshes the access token using a valid refresh token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Response.class)))
            }
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Request containing the refresh token",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RefreshTokenRequestDTO.class))
    )
    @PostMapping(value = "/refresh-token")
    public ResponseEntity<Response<TokenResponseDTO>> refreshToken(@Valid @RequestBody RefreshTokenRequestDTO dto) {
        return ResponseEntity.ok(tokenService.refreshToken(dto.refreshToken()));
    }
}
