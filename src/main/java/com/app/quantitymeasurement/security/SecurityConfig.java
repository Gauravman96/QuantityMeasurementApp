package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.util.JwtUtil;
import com.app.quantitymeasurement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService; // 🔥 NEW

    // Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            //  Google OAuth2 Login
            .oauth2Login(oauth -> oauth
                .successHandler((request, response, authentication) -> {

                    Object principal = authentication.getPrincipal();

                    String username = null;

                    if (principal instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
                        username = oauthUser.getAttribute("email");
                    }

                    if (username == null) {
                        username = authentication.getName();
                    }

                    //  SAVE USER IN DB
                    userService.saveUserIfNotExists(username);

                    //  Generate JWT
                    String token = jwtUtil.generateToken(username);

                    response.setContentType("application/json");
                    response.getWriter()
                            .write("{\"token\":\"" + token + "\"}");
                })
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

