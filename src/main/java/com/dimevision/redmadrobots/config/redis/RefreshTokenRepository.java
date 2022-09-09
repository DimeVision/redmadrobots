package com.dimevision.redmadrobots.config.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.Optional;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface RefreshTokenRepository extends KeyValueRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Long userId);

    void deleteByTokenAndUserId(String token, Long userId);
}
