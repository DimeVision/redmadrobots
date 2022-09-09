package com.dimevision.redmadrobots.model.dto.advertisement;

import com.dimevision.redmadrobots.model.domain.DealStatus;
import com.dimevision.redmadrobots.model.domain.Status;
import lombok.Builder;

/**
 * @author Dimevision
 * @version 0.1
 */

@Builder
public record AdvertisementRegistrationRequest(
        String title,
        String description,
        String contact,
        Status status,
        DealStatus dealStatus
) {}
