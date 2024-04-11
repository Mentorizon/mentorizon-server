package com.mapthree.mentorizonserver.security.config;

import com.mapthree.mentorizonserver.exception.UserNotFoundException;
import com.mapthree.mentorizonserver.model.User;
import com.mapthree.mentorizonserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for authentication and user details.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * Creates a UserDetailsService bean to retrieve user details by email.
     *
     * @return The UserDetailsService bean.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        };
    }

    /**
     * Creates an AuthenticationProvider bean for authentication.
     *
     * @return The AuthenticationProvider bean.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean for authentication.
     *
     * @param config The AuthenticationConfiguration object.
     * @return The AuthenticationManager bean.
     * @throws Exception if an error occurs while creating the AuthenticationManager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean for encoding passwords.
     *
     * @return The PasswordEncoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
