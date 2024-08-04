package com.example.journeyednt.security;

import com.example.journeyednt.domain.user.UserDto;
import com.example.journeyednt.entity.Role;
import com.example.journeyednt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByAccountId(username);
        Role role = userService.findUserRoleByAccountId(username);

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder();
        userBuilder.username(user.getAccountId());
        userBuilder.password(user.getPasswordHash());
        userBuilder.roles(role.getName());
        return userBuilder.build();
    }
}
