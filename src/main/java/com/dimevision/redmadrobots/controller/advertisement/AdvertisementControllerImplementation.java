package com.dimevision.redmadrobots.controller.advertisement;

import com.dimevision.redmadrobots.model.domain.Status;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationResponse;
import com.dimevision.redmadrobots.model.dto.image.ImageDTO;
import com.dimevision.redmadrobots.service.advertisement.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@RestController
@RequiredArgsConstructor
public class AdvertisementControllerImplementation implements AdvertisementController {

    private final AdvertisementService advertisementService;

    @Override
    public ResponseEntity<List<AdvertisementDTO>> loadAllAds(Long advertisementId, Integer page, Integer size, Status status, String sort, LocalDate startDate, String title, String contact) {
        List<AdvertisementDTO> advertisements =
                advertisementService.findAllAdvertisements(advertisementId, page, size, status, startDate, title, contact);

        return ResponseEntity.ok(advertisements);
    }

    @Override
    public ResponseEntity<AdvertisementDTO> loadAdById(Long id) {
        AdvertisementDTO advertisement = advertisementService.findAdvertisementById(id);

        return ResponseEntity.ok(advertisement);
    }

    @Override
    public ResponseEntity<AdvertisementRegistrationResponse> createAd(AdvertisementRegistrationRequest adRequest) {
        AdvertisementRegistrationResponse advertisement = advertisementService.createAdvertisement(adRequest);

        return ResponseEntity.status(201).body(advertisement);
    }

    @Override
    public ResponseEntity<AdvertisementDTO> updateAd(Long id, AdvertisementRegistrationRequest adRequest) {
        AdvertisementDTO advertisement = advertisementService.updateAdvertisement(id, adRequest);

        return ResponseEntity.ok(advertisement);
    }

    @Override
    public ResponseEntity<Void> deleteAd(Long id) {
        advertisementService.deleteAdById(id);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ImageDTO>> uploadImage(Long id, MultipartFile[] files) {
        List<ImageDTO> images = advertisementService.addImagesToAdvertisement(id, files);

        return ResponseEntity.ok(images);
    }

    @Override
    public ResponseEntity<Boolean> bookAdvertisementById(Long id) {
        advertisementService.bookAdvertisement(id);

        return ResponseEntity.ok(true);
    }
}
