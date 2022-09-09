package com.dimevision.redmadrobots.model;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Component
public class CookieUtil {

    private static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public HttpCookie createAccessTokenCookie(String token) {
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, token)
                .maxAge(30 * 60 * 1000L)
                .httpOnly(true)
                .path("/")
                .sameSite(SameSiteCookies.LAX.getValue())
                .build();
    }

    public HttpCookie createRefreshTokenCookie(String token) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, token)
                .maxAge(30 * 2 * 60 * 60 * 1000L)
                .httpOnly(true)
                .path("/")
                .sameSite(SameSiteCookies.LAX.getValue())
                .build();
    }

    public List<HttpCookie> deleteAccessTokenCookie() {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_NAME, "").maxAge(0).httpOnly(true).path("/").build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, "").maxAge(0).httpOnly(true).path("/").build();

        return List.of(accessTokenCookie, refreshTokenCookie);
    }
}