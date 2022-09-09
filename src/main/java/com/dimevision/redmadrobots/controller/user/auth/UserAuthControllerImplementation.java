package com.dimevision.redmadrobots.controller.user.auth;

import com.dimevision.redmadrobots.model.dto.user.LoginRequest;
import com.dimevision.redmadrobots.model.dto.user.LoginResponse;
import com.dimevision.redmadrobots.model.dto.token.RefreshTokenResponse;
import com.dimevision.redmadrobots.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Dimevision
 * @version 0.1
 */

@RestController
@RequiredArgsConstructor
public class UserAuthControllerImplementation implements UserAuthController {

    private final UserAuthService userAuthService;

    @Override
    public ResponseEntity<Map<String, Object>> logout(String refreshToken, String bearerToken) {
        return bearerToken != null
                ? userAuthService.logout(bearerToken.substring(7))
                : userAuthService.logout(refreshToken);
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(String accessToken, String refreshToken, String bearerToken) {
        if (bearerToken != null) {
            return userAuthService.refreshToken(bearerToken.substring(7));
        }
        return userAuthService.refreshToken(refreshToken);
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        return userAuthService.login(loginRequest);
    }
}
