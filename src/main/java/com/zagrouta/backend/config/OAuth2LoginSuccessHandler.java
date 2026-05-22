package com.zagrouta.backend.config;

import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public OAuth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        if(email == null) {
            response.sendRedirect("http://localhost:5000/auth?error=NoEmailFromProvider");
            return;
        }

        User existingUser = userService.getUserByEmail(email).orElse(null);
        if (existingUser == null) {
            // Create new user for social login
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullName(name != null ? name : "Social User");
            newUser.setPassword(UUID.randomUUID().toString()); // Random secure password since they use social login
            newUser.setRole("CUSTOMER"); // Default role
            newUser.setGender("MALE"); // Default
            newUser.setPhone(""); // Empty for now
            userService.saveUser(newUser);
        }

        // Redirect back to React frontend callback (AuthCallback.jsx handles this route)
        response.sendRedirect("http://localhost:5000/auth/callback?email=" + email);
    }
}
