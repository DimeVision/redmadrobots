package com.dimevision.redmadrobots.service.advertisement;

import com.dimevision.redmadrobots.model.domain.Status;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationResponse;
import com.dimevision.redmadrobots.model.dto.image.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface AdvertisementService {

    List<AdvertisementDTO> findAllAdvertisements(Long advertisementId, Integer page, Integer size, Status status, LocalDate startDate, String title, String contact);

    AdvertisementDTO findAdvertisementById(Long id);

    AdvertisementDTO updateAdvertisement(Long id, AdvertisementRegistrationRequest adRequest);

    void bookAdvertisement(Long id);

    void deleteAdById(Long id);

    AdvertisementRegistrationResponse createAdvertisement(AdvertisementRegistrationRequest adRequest);

    List<ImageDTO> addImagesToAdvertisement(Long id, MultipartFile[] images);
}
