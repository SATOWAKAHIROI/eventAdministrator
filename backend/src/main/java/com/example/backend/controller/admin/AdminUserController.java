package com.example.backend.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.LoginRequest;
import com.example.backend.dto.requests.UserRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.service.admin.AdminUserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> userAuth(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = adminUserService.login(loginRequest);

        return ResponseEntity.ok(ApiResponse.success(authResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> userCreate(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = adminUserService.createUser(userRequest);

        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser() {
        List<UserResponse> userResponses = adminUserService.getAllUser();
        return ResponseEntity.ok(ApiResponse.success(userResponses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse userResponse = adminUserService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> editUserbyId(@Valid @RequestBody UserRequest userRequest,
            @PathVariable Long id) {
        UserResponse userResponse = adminUserService.editUserById(userRequest, id);
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserbyId(@PathVariable Long id) {
        adminUserService.deleteUserById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
