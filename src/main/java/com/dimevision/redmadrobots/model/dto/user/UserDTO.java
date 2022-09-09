package com.dimevision.redmadrobots.model.dto.user;

import com.dimevision.redmadrobots.model.domain.Role;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

public record UserDTO(
        Long id,
        String email,
        Role role,
        List<AdvertisementDTO> advertisements
) {}
