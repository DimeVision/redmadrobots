package com.dimevision.redmadrobots.config.redis;

import java.util.Map;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface JwtTokenService {

    Map<String, Object> generateJwtTokens(String username);
}
