package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.TokenEntity;
import com.gloomhaven.helper.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String jwt = extractJwtFromRequest(request);
        if (jwt != null) {
            invalidateToken(jwt);
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void invalidateToken(String jwt) {
        TokenEntity storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            markTokenAsInvalid(storedToken);
            SecurityContextHolder.clearContext();
        }
    }

    private void markTokenAsInvalid(TokenEntity storedToken) {
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);
    }
}
