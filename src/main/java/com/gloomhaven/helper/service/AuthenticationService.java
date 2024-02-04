package com.gloomhaven.helper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloomhaven.helper.model.dto.rest.auth.AuthenticationRequest;
import com.gloomhaven.helper.model.dto.rest.auth.AuthenticationResponse;
import com.gloomhaven.helper.model.dto.rest.auth.RegisterRequest;
import com.gloomhaven.helper.model.entities.TokenEntity;
import com.gloomhaven.helper.model.entities.TokenType;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.TokenRepository;
import com.gloomhaven.helper.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        UserEntity user = createUser(request);
        UserEntity savedUser = saveUser(user);
        String jwtToken = generateJwtToken(user);
        String refreshToken = generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return buildAuthenticationResponse(jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request.getEmail(), request.getPassword());
        UserEntity user = getUserByEmail(request.getEmail());
        String jwtToken = generateJwtToken(user);
        String refreshToken = generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return buildAuthenticationResponse(jwtToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = extractRefreshTokenFromRequest(request);
        if (refreshToken == null) {
            return;
        }

        String userEmail = jwtService.extractUserEmail(refreshToken);
        if (userEmail == null) {
            return;
        }

        UserEntity user = getUserByEmail(userEmail);
        if (!jwtService.isTokenValid(refreshToken, user)) {
            return;
        }

        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        AuthenticationResponse authResponse = buildAuthenticationResponse(accessToken, refreshToken);
        writeResponse(response, authResponse);
    }

    private UserEntity createUser(RegisterRequest request) {
        return UserEntity.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
    }

    private UserEntity saveUser(UserEntity user) {
        return repository.save(user);
    }

    private String generateJwtToken(UserEntity user) {
        return jwtService.generateToken(user);
    }

    private String generateRefreshToken(UserEntity user) {
        return jwtService.generateRefreshToken(user);
    }

    private void revokeAllUserTokens(UserEntity user) {
        List<TokenEntity> validUserTokens = findAllValidTokensByUser(user);
        if (validUserTokens.isEmpty()) {
            return;
        }
        invalidateTokens(validUserTokens);
        saveTokens(validUserTokens);
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private List<TokenEntity> findAllValidTokensByUser(UserEntity user) {
        return tokenRepository.findAllValidTokenByUser(user.getId());
    }

    private void invalidateTokens(List<TokenEntity> tokens) {
        tokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
    }

    private void saveTokens(List<TokenEntity> tokens) {
        tokenRepository.saveAll(tokens);
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private String extractRefreshTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            int bearerLength = 7;
            return authHeader.substring(bearerLength);
        }
        return null;
    }

    private UserEntity getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    private AuthenticationResponse buildAuthenticationResponse(String jwtToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    private void writeResponse(HttpServletResponse response, AuthenticationResponse authResponse) throws IOException {
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }
}
