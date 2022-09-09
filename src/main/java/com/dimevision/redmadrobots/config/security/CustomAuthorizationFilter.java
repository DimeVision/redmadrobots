package com.dimevision.redmadrobots.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dimevision.redmadrobots.config.redis.RefreshToken;
import com.dimevision.redmadrobots.config.redis.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Dimevision
 * @version 0.1
 */

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final RefreshTokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getServletPath().contains("/login") || request.getServletPath().contains("/logout") || request.getServletPath().contains("/refresh") || request.getServletPath().contains("/swagger")) {
                filterChain.doFilter(request, response);
            } else {
                String accessToken = getJwtToken(request);
                if (StringUtils.hasText(accessToken) && !accessToken.equals("invalidated")) {
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(accessToken);
                    String username = decodedJWT.getSubject();

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request, response);
            }
        } catch (RuntimeException exception) {
            Map<String, String> errors = new HashMap<>();
            errors.put("Exception", exception.getClass().getSimpleName());
            errors.put("Reason", exception.getMessage());
            errors.put("Timestamp", LocalDateTime.now().toString());

            response.setStatus(403);
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
    }

    private String getJwtToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            RefreshToken token = tokenRepository.findByToken(bearerToken.substring(7))
                    .orElseThrow(() -> new TokenExpiredException("Token either no more available or doesn't exists", Instant.now()));
            if (token.getExpiredAt().before(new Date(System.currentTimeMillis()))) {
                tokenRepository.deleteByTokenAndUserId(token.getToken(), token.getUserId());
                throw new TokenExpiredException("Token has expired", token.getExpiredAt().toInstant());
            }

            return bearerToken.substring(7);
        }

        Cookie accessToken = WebUtils.getCookie(request, "access_token");
        return accessToken != null ? accessToken.getValue() : null;
    }
}
