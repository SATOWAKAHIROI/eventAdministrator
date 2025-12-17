package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.LoginRequest;
import com.example.backend.dto.requests.UserRequest;
import com.example.backend.dto.response.AuthResponse;
import com.example.backend.dto.response.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.entity.User.RoleType;
import com.example.backend.exception.BusinessException;
import com.example.backend.exception.UnauthorizedException;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtTokenProvider;

@Service
public class AdminUserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AdminUserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("メールアドレスまたはパスワードが正しくありません"));

        boolean passCheck = user.getPassword().equals(loginRequest.getPassword());

        if (!passCheck) {
            throw new UnauthorizedException("メールアドレスまたはパスワードが正しくありません");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getUserRole());
        UserResponse userResponse = UserResponse.from(user);
        AuthResponse authResponse = new AuthResponse(token, userResponse);

        return authResponse;
    }

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = users.stream().map(UserResponse::from).collect(Collectors.toList());

        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("該当のユーザーが見つかりません"));

        return UserResponse.from(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        // エラー処理は後で実装
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new BusinessException("そのメールアドレスはすでに他のユーザーが使用しています");
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
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("該当するユーザーが見つかりません"));

        boolean isExistsEmail = userRepository.existsByEmail(userRequest.getEmail());
        boolean isEqualEmail = user.getEmail().equals(userRequest.getEmail());

        // 指定したメールアドレスがすでに存在し、かつ自身でない場合
        if (isExistsEmail && !isEqualEmail) {
            throw new BusinessException("そのメールアドレスはすでに他のユーザーが使用しています");
        }

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUserRole(userRequest.getUserRole());

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException("該当のユーザーはすでに存在していません");
        }
        deleteUserById(id);
    }
}
