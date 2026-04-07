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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                // ✅ API requests — authenticated via JWT only
                .requestMatchers("/api/v1/**").authenticated()
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // ✅ THIS IS THE KEY FIX
            // When /api/v1/** request has no/bad token → return 401 JSON
            // Do NOT redirect to Google login page
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                    (request, response, authException) -> {
                        System.out.println("❌ Unauthorized API request: "
                            + request.getRequestURI());
                        response.setStatus(401);
                        response.setContentType("application/json");
                        response.getWriter().write(
                            "{\"error\":\"Unauthorized\"," +
                            "\"message\":\"Invalid or missing JWT token\"}"
                        );
                    },
                    // ✅ Only apply this entry point to /api/** requests
                    new AntPathRequestMatcher("/api/**")
                )
            )

            // Google OAuth2 — only for browser-initiated login
            .oauth2Login(oauth -> oauth
            		.authorizationEndpoint(a -> a
                            .baseUri("/oauth2/authorization")
                        )
                .successHandler((request, response, authentication) -> {

                    Object principal = authentication.getPrincipal();
                    String username = null;

                    if (principal instanceof
                        org.springframework.security.oauth2.core.user
                            .DefaultOAuth2User oauthUser) {
                        username = oauthUser.getAttribute("email");
                    }

                    if (username == null) {
                        username = authentication.getName();
                    }

                    userService.saveUserIfNotExists(username);

                    String token = jwtUtil.generateToken(username);

                    System.out.println("✅ Google login success for: " + username);
                    System.out.println("✅ Token generated: " + token);

                    // Send token back to Angular
                    response.sendRedirect(
                        "http://localhost:4200/dashboard?token=" + token
                    );
                })
            );

        //  JWT filter must run before Spring Security auth checks
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}