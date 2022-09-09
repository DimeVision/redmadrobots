package com.dimevision.redmadrobots.model.mapper;

import com.dimevision.redmadrobots.model.domain.Image;
import com.dimevision.redmadrobots.model.dto.image.ImageDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author Dimevision
 * @version 0.1
 */

@Mapper
public interface ImageMapper {
    Image toImage(ImageDTO imageDTO);

    List<Image> toImages(List<ImageDTO> imageDTOs);

    ImageDTO toImageDTO(Image image);

    List<ImageDTO> toImageDTOs(Set<Image> images);
}
