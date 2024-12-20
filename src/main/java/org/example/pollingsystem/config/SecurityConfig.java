package org.example.pollingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity; enable in production
                .authorizeRequests()
                .antMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Public endpoints
                .antMatchers("/api/polls/**").hasAnyRole("USER", "ADMIN") // Restrict polls
                .anyRequest().authenticated(); // Require authentication for all other endpoints
                .and()
                .httpBasic(); // Basic authentication; replace with JWT or OAuth for production

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .jdbcAuthentication()
                .withUser("admin")
                .password(passwordEncoder.encode("adminpass"))
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder.encode("userpass"))
                .roles("USER")
                .and()
                .and()
                .build();
    }
}
