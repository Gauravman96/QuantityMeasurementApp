package com.app.quantitymeasurement.security;

import com.app.quantitymeasurement.service.CustomUserDetailsService;
import com.app.quantitymeasurement.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        // ── Step 1: Extract token ──────────────────────────
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
                System.out.println("✅ JWT username extracted: " + username);
            } catch (Exception e) {
                System.out.println("❌ Invalid JWT Token: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ No Bearer token found in request: "
                + request.getRequestURI());
        }

        // ── Step 2: Validate and set authentication ────────
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

                // ✅ Pass username string — matches your JwtUtil signature
                boolean isValid = jwtUtil.validateToken(token, username);
                System.out.println("✅ Token valid: " + isValid);

                if (isValid) {
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ Authentication set for: " + username);
                } else {
                    System.out.println("❌ Token validation failed for: " + username);
                }

            } catch (Exception e) {
                System.out.println("❌ Auth error for user: " + username
                    + " → " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}