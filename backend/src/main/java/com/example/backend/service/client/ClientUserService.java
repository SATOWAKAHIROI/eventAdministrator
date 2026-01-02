package com.example.backend.service.client;

import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ClientUserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final com.example.backend.repository.EventRepository eventRepository;
    private final com.example.backend.repository.NoticeRepository noticeRepository;

    public ClientUserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
            com.example.backend.repository.EventRepository eventRepository,
            com.example.backend.repository.NoticeRepository noticeRepository) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.eventRepository = eventRepository;
        this.noticeRepository = noticeRepository;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("メールアドレスまたはパスワードが正しくありません"));

        boolean passCheck = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!passCheck) {
            throw new UnauthorizedException("メールアドレスまたはパスワードが正しくありません");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail(), user.getUserRole());
        UserResponse userResponse = UserResponse.from(user);
        AuthResponse authResponse = new AuthResponse(token, userResponse);

        return authResponse;
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
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setUserRole(RoleType.VIEWER);

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException("該当のユーザーが見つかりません"));

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
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }

        // ユーザーロールが指定されている場合のみ更新
        if (userUpdateRequest.getUserRole() != null) {
            user.setUserRole(userUpdateRequest.getUserRole());
        }

        userRepository.save(user);

        return UserResponse.from(user);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("該当のユーザーはすでに存在していません"));

        // イベントまたはお知らせを作成しているかチェック
        long eventCount = eventRepository.countByCreatedBy(user);
        long noticeCount = noticeRepository.countByCreatedBy(user);

        if (eventCount > 0 || noticeCount > 0) {
            throw new BusinessException(
                    "このユーザーが作成したイベントまたはお知らせが存在するため削除できません。" +
                            "（イベント: " + eventCount + "件、お知らせ: " + noticeCount + "件）");
        }

        userRepository.deleteById(id);
    }
}
