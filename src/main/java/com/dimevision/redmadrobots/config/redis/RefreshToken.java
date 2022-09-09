package com.dimevision.redmadrobots.config.redis;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Dimevision
 * @version 0.1
 */

@Data
@Builder
@RedisHash("RefreshToken")
public class RefreshToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Indexed
    @Id
    private Long userId;

    @Indexed
    private String token;

    private Date expiredAt;
}
