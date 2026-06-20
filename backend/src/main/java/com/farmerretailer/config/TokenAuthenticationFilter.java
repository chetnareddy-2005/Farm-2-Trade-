package com.farmerretailer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenRegistry tokenRegistry;

    public TokenAuthenticationFilter(TokenRegistry tokenRegistry) {
        this.tokenRegistry = tokenRegistry;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String path = request.getRequestURI();

        // ✅ SKIP AUTH FOR LOGIN & REGISTER
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = request.getHeader("X-Auth-Token");
        
        if (token != null && !token.isEmpty()) {
            Authentication authentication = tokenRegistry.getAuthentication(token);
            if (authentication != null) {
                System.out.println("[Security Debug] SUCCESS: Token found in registry. User: " + authentication.getName() + " | Path: " + path);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.err.println("[Security Debug] FAILURE: Token present but NOT found in registry! Token: " + token + " | Path: " + path);
            }
        } else {
            if (!path.startsWith("/api/auth")) {
                System.out.println("[Security Debug] WARNING: No X-Auth-Token header found for protected path: " + path);
            }
        }
 
        filterChain.doFilter(request, response);
    }
}
