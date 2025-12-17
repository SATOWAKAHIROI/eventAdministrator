package com.example.backend.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.requests.LoginRequest;
import com.example.backend.dto.requests.UserRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.client.ClientUserService;
import com.example.backend.dto.common.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/user")
public class ClientUserController {
    private final ClientUserService clientUserService;

    public ClientUserController(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> userAuth(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = clientUserService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(authResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> userCreate(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = clientUserService.createUser(userRequest);

        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse userResponse = clientUserService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> editUserbyId(@Valid @RequestBody UserRequest userRequest,
            @PathVariable Long id) {
        UserResponse userResponse = clientUserService.editUserById(userRequest, id);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserbyId(@PathVariable Long id) {
        clientUserService.deleteUserById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
