package com.dimevision.redmadrobots.service.user.auth;

import com.dimevision.redmadrobots.model.dto.user.LoginRequest;
import com.dimevision.redmadrobots.model.dto.user.LoginResponse;
import com.dimevision.redmadrobots.model.dto.token.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface UserAuthService {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);

    ResponseEntity<RefreshTokenResponse> refreshToken(String tokenRequest);

    ResponseEntity<Map<String, Object>> logout(String refreshToken);
}
