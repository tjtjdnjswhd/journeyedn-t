package com.example.journeyednt.service;

import com.example.journeyednt.config.PasswordEncoderConfig;
import com.example.journeyednt.domain.user.UserCreateDto;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserLoginDto;
import com.example.journeyednt.entity.Role;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.exception.UserException;
import com.example.journeyednt.repository.RoleRepository;
import com.example.journeyednt.repository.UserRepository;
import com.example.journeyednt.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final RoleRepository roleRepository;

    // 회원 생성
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        boolean accountExists = existsByAccountId(userCreateDto.getAccountId());
        boolean nicknameExists = existsByNickname(userCreateDto.getNickName());

        if (nicknameExists) {
            throw new UserException(ErrorCode.USER_NICKNAME_EXISTS);
        }
        if (accountExists) {
            throw new UserException(ErrorCode.USER_ACCOUNT_ID_EXISTS);
        }

        User user = userCreateDto.toEntity();
        userRepository.save(user);
        return user;
    }

    // 로그인
    @Transactional(readOnly = true)
    public User loginUser(UserLoginDto userLoginDto) {
        User user = findByAccountId(userLoginDto.getAccountId());
        if (!passwordEncoderConfig.passwordEncoder().matches(userLoginDto.getPasswordHash(), user.getPasswordHash())) {
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

    // 권한 조회
    @Transactional(readOnly = true)
    public Optional<Role> findUserRoleByAccountId(String accountId) {
        return userRepository.findRoleByAccountId(accountId);
    }

    // 권한 변경
    @Transactional
    public void updateUserRole(Integer userId, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

        user.setRole(role);
        userRepository.save(user);
    }

    // 회원 조회 - accountId
    @Transactional(readOnly = true)
    public UserDto getUserById(String accountId) {
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        return UserDto.toDto(user);
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
    public User findByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findByNickname(String nickName) {
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
    }
}
