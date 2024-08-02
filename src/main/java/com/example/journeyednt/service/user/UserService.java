package com.example.journeyednt.service.user;

import com.example.journeyednt.config.PasswordEncoderConfig;
import com.example.journeyednt.domain.user.UserCreateDto;
import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.domain.user.UserLoginDto;
import com.example.journeyednt.entity.User;
import com.example.journeyednt.exception.UserServiceException;
import com.example.journeyednt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    // 회원 생성
    public void  createUser(UserCreateDto userCreateDto) {
        existsByAccountId(userCreateDto.getAccountId());
        existsByNickname(userCreateDto.getNickName());
        User user = userCreateDto.toEntity();
        userRepository.save(user);
    }

    // 로그인
    public User loginUser(UserLoginDto userLoginDto) {
        User user = findByAccountId(userLoginDto.getAccountId());
        if (!passwordEncoderConfig.passwordEncoder().matches(userLoginDto.getPasswordHash(), user.getPasswordHash())) {
            throw new UserServiceException();
        }
        return user;
    }

    // 노출 여부
    @Transactional
    public Object updateVisibleUser(UserDto userDto) {
        int updatedRows = userRepository.updateUserVisibleByAccountId(userDto.getAccountId(), userDto.getIsVisible());
        return (updatedRows != 0) ? findByAccountId(userDto.getAccountId())
                : new UserServiceException();
    }

    // 권한 변경
    @Transactional
    public Object updateUserRole(UserDto userDto) {
        int updatedRows = userRepository.updateUserRoleByAccountId(userDto.getAccountId(), userDto.getRole());
        return (updatedRows != 0) ? findByAccountId(userDto.getAccountId())
                : new UserServiceException();
    }

    // 검증 로직 및 유틸 로직
    public Boolean existsByAccountId(String accountId) {
        return userRepository.existsByAccountId(accountId);
    }

    public Boolean existsByNickname(String nickName) {
        return userRepository.existsByNickname(nickName);
    }

    public User findByAccountId(String accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserServiceException();
    }

    public User findByNickname(String nickName) {
        return userRepository.findByNickname(nickName)
                .orElseThrow(() -> new UserServiceException();
    }
}
