package com.dimevision.redmadrobots.model.dto.user;

import lombok.Builder;

/**
 * @author Dimevision
 * @version 0.1
 */

@Builder
public record LoginResponse(
        String accessToken,
        UserDTO user
) {}
