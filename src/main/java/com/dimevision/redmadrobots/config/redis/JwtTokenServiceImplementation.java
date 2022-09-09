package com.dimevision.redmadrobots.config.redis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dimevision.redmadrobots.model.domain.User;
import com.dimevision.redmadrobots.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Dimevision
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImplementation implements JwtTokenService {

    private static final String NOT_EXISTS = "User with email: %s not exists in database";

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Override
    public Map<String, Object> generateJwtTokens(String username) {
        final Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
        final Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000));

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_EXISTS, username)));

        SimpleGrantedAuthority userAuthorities = new SimpleGrantedAuthority(user.getRole().name());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        Date issuedAt = new Date(System.currentTimeMillis());
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(issuedAt)
                .withExpiresAt(accessTokenExpiresAt)
                .withClaim("roles", userAuthorities.toString())
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(issuedAt)
                .withExpiresAt(refreshTokenExpiresAt)
                .withClaim("roles", userAuthorities.toString())
                .sign(algorithm);

        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .expiredAt(refreshTokenExpiresAt)
                .userId(user.getId())
                .build();
        refreshTokenRepository.save(token);

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        tokens.put("user", user);

        return tokens;
    }
}
