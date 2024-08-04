package com.example.journeyednt.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "잘못된 아이디 혹은 비밀번호입니다";
        } else {
            errorMessage = "잠시 후 다시 시도해 주십시오";
        }

        String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        setDefaultFailureUrl("/login?error=" + encodedMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}
