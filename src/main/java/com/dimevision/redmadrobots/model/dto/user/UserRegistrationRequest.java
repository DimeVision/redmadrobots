package com.dimevision.redmadrobots.model.dto.user;

import com.dimevision.redmadrobots.model.domain.Role;

/**
 * @author Dimevision
 * @version 0.1
 */

public record UserRegistrationRequest(
        String email,
        String password,
        Role role
) {}
