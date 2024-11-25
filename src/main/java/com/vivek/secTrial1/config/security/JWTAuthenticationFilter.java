package com.vivek.secTrial1.config.security;

import java.io.IOException;

import com.vivek.secTrial1.service.JWTService;
import com.vivek.secTrial1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        System.out.println(request.toString());

        final String authHeader = request.getHeader("Authorization");

        System.out.println(authHeader);

        final String jwt;
        final String userName;

        // Check authenticatin header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extract the token from the header
        jwt = authHeader.substring(7);

        System.out.println(jwt);
        // Extract the username from the token (username is the email)
        userName = jwtService.extractUserName(jwt);

        System.out.println(userName);
        boolean nonAuthenticated = SecurityContextHolder.getContext().getAuthentication() == null;
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
        // Authenticate if not already authenticated
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = userService.loadUserByUsername(userName);
            System.out.println(user.toString());
            // Check if token is valid
            if (jwtService.isTokenValid(jwt, user)) {
                System.out.println("token is valid");
                // Update the security context holder
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());
                // Set additional details such as user's IP address, browser, or other
                // attributes
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Call the next filter
        filterChain.doFilter(request, response);
    }
}