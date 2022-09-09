package com.dimevision.redmadrobots.service.user.auth;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dimevision.redmadrobots.config.redis.JwtTokenService;
import com.dimevision.redmadrobots.config.redis.RefreshToken;
import com.dimevision.redmadrobots.config.redis.RefreshTokenRepository;
import com.dimevision.redmadrobots.model.CookieUtil;
import com.dimevision.redmadrobots.model.domain.User;
import com.dimevision.redmadrobots.model.dto.token.RefreshTokenResponse;
import com.dimevision.redmadrobots.model.dto.user.LoginRequest;
import com.dimevision.redmadrobots.model.dto.user.LoginResponse;
import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.mapper.UserMapper;
import com.dimevision.redmadrobots.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

/**
 * @author Dimevision
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private static final String NOT_EXISTS = "User with email: %s not exists in database";

    private final AuthenticationManager authenticationManager;
    private final CookieUtil cookieUtil;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_EXISTS, username)));

        refreshTokenRepository.findByUserId(user.getId())
                .ifPresent(refreshTokenRepository::delete);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> tokens = jwtTokenService.generateJwtTokens(username);

        String access = tokens.get("access_token").toString();
        String refresh = tokens.get("refresh_token").toString();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(SET_COOKIE, cookieUtil.createAccessTokenCookie(access).toString());
        responseHeaders.add(SET_COOKIE, cookieUtil.createRefreshTokenCookie(refresh).toString());

        UserDTO userDTO = userMapper.toUserDTO(user);
        LoginResponse response = LoginResponse.builder()
                .accessToken(refresh)
                .user(userDTO)
                .build();

        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(String tokenRequest) {
        RefreshToken token = refreshTokenRepository.findByToken(tokenRequest)
                .orElseThrow(() -> new TokenExpiredException("Token either no more available or doesn't exists", Instant.now()));

        refreshTokenRepository.delete(token);

        User user = userRepository.findById(token.getUserId())
                .orElseThrow();
        UserDTO userDTO = userMapper.toUserDTO(user);

        Map<String, Object> refreshedTokens = jwtTokenService.generateJwtTokens(userDTO.email());
        String accessToken = refreshedTokens.get("access_token").toString();
        String refreshToken = refreshedTokens.get("refresh_token").toString();
        RefreshTokenResponse refreshTokenResponse = RefreshTokenResponse.builder()
                .accessToken(refreshToken)
                .user(userDTO)
                .build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(SET_COOKIE, cookieUtil.createAccessTokenCookie(accessToken).toString());
        responseHeaders.add(SET_COOKIE, cookieUtil.createRefreshTokenCookie(refreshToken).toString());

        return ResponseEntity.ok().headers(responseHeaders).body(refreshTokenResponse);
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout(String refreshToken) {
        List<HttpCookie> cookiesToInvalidate = cookieUtil.deleteAccessTokenCookie();

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(NOT_EXISTS, "Token")));

        refreshTokenRepository.delete(token);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(SET_COOKIE, cookieUtil.createRefreshTokenCookie("invalidated").toString());
        responseHeaders.add(SET_COOKIE, cookieUtil.createAccessTokenCookie("invalidated").toString());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Logout successful. Access token was invalidated");
        responseBody.put("time", LocalDateTime.now());

        return ResponseEntity.ok().headers(responseHeaders).body(responseBody);
    }
}
