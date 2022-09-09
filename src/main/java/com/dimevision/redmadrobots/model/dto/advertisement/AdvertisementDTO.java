package com.dimevision.redmadrobots.model.dto.advertisement;

import com.dimevision.redmadrobots.model.dto.image.ImageDTO;

import java.util.Set;

/**
 * @author Dimevision
 * @version 0.1
 */

public record AdvertisementDTO(
   Long id,
   String title,
   String description,
   String contact,
   Set<ImageDTO> images
) {}
