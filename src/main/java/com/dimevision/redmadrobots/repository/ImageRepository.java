package com.dimevision.redmadrobots.repository;

import com.dimevision.redmadrobots.model.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface ImageRepository extends JpaRepository<Image, Long> {
}
