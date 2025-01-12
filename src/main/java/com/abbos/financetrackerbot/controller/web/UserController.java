package com.abbos.financetrackerbot.controller.web;

import com.abbos.financetrackerbot.domain.dto.Response;
import com.abbos.financetrackerbot.domain.dto.user.UserResponseDTO;
import com.abbos.financetrackerbot.service.UserService;
import com.abbos.financetrackerbot.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  15:15
 **/
@RestController
@RequestMapping(Constants.BASE_PATH_V1 + "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "to get all users")
    @GetMapping("/get")
    public ResponseEntity<Response<List<UserResponseDTO>>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
