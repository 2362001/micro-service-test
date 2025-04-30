package com.example._startcode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll() // Cho phép truy cập h2-console
                .anyRequest().authenticated()).csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**") // Bỏ qua CSRF cho h2-console
        ).headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()) // Cho phép frame
        );

        return http.build();
    }
}