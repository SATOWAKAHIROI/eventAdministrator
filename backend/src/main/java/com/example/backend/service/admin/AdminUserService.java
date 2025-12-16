package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.UserRequest;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.entity.User.RoleType;
import com.example.backend.repository.UserRepository;

@Service
public class AdminUserService {
    private final UserRepository userRepository;

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = users.stream().map(UserResponse::from).collect(Collectors.toList());

        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        // エラー処理は後で実装
        if (user == null) {
            return null;
        }

        return UserResponse.from(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        // エラー処理は後で実装
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return null;
        }

        User user = new User();

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserRole(RoleType.ADMIN);

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse editUserById(UserRequest userRequest, Long id) {
        User user = userRepository.findById(id).orElse(null);

        // エラー処理は後で実装
        if (user == null) {
            return null;
        }

        // エラー処理は後で実装
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return null;
        }

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserRole(userRequest.getUserRole());

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public void deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            deleteUserById(id);
        }
    }
}
