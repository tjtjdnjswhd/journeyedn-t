package com.example.journeyednt.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationFailureHandler failureHandler, AccessDeniedHandler accessDeniedHandler) throws Exception {
        http.formLogin(cnf -> cnf.loginPage("/users/login").usernameParameter("accountId").passwordParameter("password").permitAll().failureHandler(failureHandler).defaultSuccessUrl("/"));
        http.logout(conf -> conf.logoutUrl("/users/logout").permitAll().logoutSuccessUrl("/").permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/users/logout", "GET")));

        http.authorizeHttpRequests(auth ->
        {
            auth.dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll();
            auth.requestMatchers(HttpMethod.GET, "/css/**", "/img/**", "/js/**", "/lib/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/", "admin/notice/**", "/error", "/api/post-images/**", "/api/post-images/posts/**", "/api/country").permitAll();
            auth.requestMatchers("/users/**").permitAll();

            auth.requestMatchers("/admin", "/admin/**").hasRole("Admin");
            auth.anyRequest().hasAnyRole("User");
        });

        http.httpBasic(AbstractHttpConfigurer::disable);

        http.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("Admin").implies("User")
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/error");
        return accessDeniedHandler;
    }
}
