package com.dimevision.redmadrobots.model.dto.advertisement;

import com.dimevision.redmadrobots.model.domain.Status;

/**
 * @author Dimevision
 * @version 0.1
 */

public record AdvertisementRegistrationResponse(
        Long id,
        String title,
        String description,
        String contact,
        Status status
) {}
