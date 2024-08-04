package com.example.journeyednt.service;

import com.example.journeyednt.security.PasswordEncoderConfig;
import com.example.journeyednt.domain.user.UserSignup;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserLogin;
import com.example.journeyednt.entity.Role;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.exception.UserException;
import com.example.journeyednt.repository.RoleRepository;
import com.example.journeyednt.repository.UserRepository;
import com.example.journeyednt.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final RoleRepository roleRepository;

    // 회원 생성
    @Transactional
    public UserDto createUser(UserSignup userSignup) {
        boolean accountExists = existsByAccountId(userSignup.getAccountId());
        boolean nicknameExists = existsByNickname(userSignup.getNickName());

        if (nicknameExists) {
            throw new UserException(ErrorCode.USER_NICKNAME_EXISTS);
        }
        if (accountExists) {
            throw new UserException(ErrorCode.USER_ACCOUNT_ID_EXISTS);
        }

        String passwordHash = passwordEncoderConfig.passwordEncoder().encode(userSignup.getPassword());

        User user = User.builder()
                .name(userSignup.getName())
                .accountId(userSignup.getAccountId())
                .passwordHash(passwordHash)
                .nickName(userSignup.getNickName())
                .build();

        return UserDto.fromEntity(userRepository.save(user));
    }

    // 로그인
    @Transactional(readOnly = true)
    public UserDto loginUser(UserLogin userLoginDto) {
        UserDto user = findByAccountId(userLoginDto.getAccountId());
        if (!passwordEncoderConfig.passwordEncoder().matches(userLoginDto.getPassword(), user.getPasswordHash())) {
            throw new UserException(ErrorCode.WRONG_PASSWORD);
        }

        return user;
    }

    // 노출 여부 조회
    @Transactional(readOnly = true)
    public Boolean findUserIsVisibleByAccountId(String accountId) {
        return userRepository.findIsVisibleByAccountId(accountId);
    }

    // 노출 변경
    @Transactional
    public void updateVisibleUser(Integer Id, Boolean visible) {
        userRepository.updateUserVisibleById(Id, visible);
    }

    @Transactional
    public void updateVisibleUser(String accountId, Boolean visible) {
        userRepository.updateUserVisibleByAccountId(accountId, visible);
    }

    // 권한 조회
    @Transactional(readOnly = true)
    public Role findUserRoleByAccountId(String accountId) {
        return userRepository.findRoleByAccountId(accountId).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    // 권한 변경
    @Transactional
    public void updateUserRole(Integer userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRole(String accountId, String roleName) {
        User user = userRepository.findByAccountId(accountId).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
    }

    // 회원 조회 - accountId
    @Transactional(readOnly = true)
    public UserDto getUserById(String accountId) {
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        return UserDto.fromEntity(user);
    }

    // 검증 로직 및 유틸 로직
    @Transactional(readOnly = true)
    public Boolean existsByAccountId(String accountId) {
        return userRepository.existsByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public Boolean existsByNickname(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Transactional(readOnly = true)
    public UserDto findByPostId(Integer postId) {
        return userRepository.findByPostId(postId).map(UserDto::fromEntity).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserDto findByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId).map(UserDto::fromEntity).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public UserDto findByNickname(String nickName) {
        return userRepository.findByNickName(nickName).map(UserDto::fromEntity).orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
}
