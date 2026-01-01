package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.LoginRequest;
import com.example.backend.dto.requests.UserCreateRequest;
import com.example.backend.dto.requests.UserUpdateRequest;
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

        boolean roleCheck = user.getUserRole() == RoleType.ADMIN;

        if (!roleCheck) {
            throw new UnauthorizedException("このサイトは管理者権限がないユーザーではアクセスできません");
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

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new BusinessException("そのメールアドレスはすでに他のユーザーが使用しています");
        }

        // パスワードの長さチェック
        if (userCreateRequest.getPassword().length() < 8 || userCreateRequest.getPassword().length() > 100) {
            throw new BusinessException("パスワードは8文字以上100文字以下で入力してください");
        }

        User user = new User();

        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());
        user.setUserRole(RoleType.ADMIN);

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse editUserById(UserUpdateRequest userUpdateRequest, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("該当するユーザーが見つかりません"));

        boolean isExistsEmail = userRepository.existsByEmail(userUpdateRequest.getEmail());
        boolean isEqualEmail = user.getEmail().equals(userUpdateRequest.getEmail());

        // 指定したメールアドレスがすでに存在し、かつ自身でない場合
        if (isExistsEmail && !isEqualEmail) {
            throw new BusinessException("そのメールアドレスはすでに他のユーザーが使用しています");
        }

        user.setName(userUpdateRequest.getName());
        user.setEmail(userUpdateRequest.getEmail());

        // パスワードが入力されている場合のみ更新
        if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().trim().isEmpty()) {
            // パスワードの長さチェック
            if (userUpdateRequest.getPassword().length() < 8 || userUpdateRequest.getPassword().length() > 100) {
                throw new BusinessException("パスワードは8文字以上100文字以下で入力してください");
            }
            user.setPassword(userUpdateRequest.getPassword());
        }

        // ユーザーロールが指定されている場合のみ更新
        if (userUpdateRequest.getUserRole() != null) {
            user.setUserRole(userUpdateRequest.getUserRole());
        }

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
