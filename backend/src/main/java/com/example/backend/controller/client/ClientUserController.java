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
import com.example.backend.dto.common.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/user")
public class ClientUserController {
    @GetMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> userAuth(@Valid @RequestBody LoginRequest loginRequest) {
        // 認証ロジックは後で実装
        AuthResponse authResponse = new AuthResponse(null, null);

        return ResponseEntity.ok(ApiResponse.success(authResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> userCreate(@Valid @RequestBody UserRequest userRequest) {
        // ユーザー登録ロジックは後で作成する
        UserResponse userResponse = new UserResponse();

        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@Valid @RequestBody UserRequest userRequest,
            @PathVariable Long id) {
        // ユーザー取得ロジックは後で作成する
        UserResponse userResponse = new UserResponse();
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> editUserbyId(@Valid @RequestBody UserRequest userRequest,
            @PathVariable Long id) {
        // ユーザー編集ロジックは後で作成する
        UserResponse userResponse = new UserResponse();
        return ResponseEntity.ok(ApiResponse.success(userResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUserbyId(@PathVariable Long id) {
        // ユーザー削除ロジックは後で作成する
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
