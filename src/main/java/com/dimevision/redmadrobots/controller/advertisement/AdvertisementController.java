package com.dimevision.redmadrobots.controller.advertisement;

import com.dimevision.redmadrobots.model.domain.Status;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationResponse;
import com.dimevision.redmadrobots.model.dto.image.ImageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author Dimevision
 * @version 0.1
 */

@Tag(name = "Объявления")
@RequestMapping(value = "/api/v1/advertisements", produces = APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('USER', 'ADMINISTRATOR')")
public interface AdvertisementController {

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the advertisements", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdvertisementDTO.class)))
            }
    )
    @Operation(summary = "Выводит отфильтрованный по параметрам список объявлений")
    ResponseEntity<List<AdvertisementDTO>> loadAllAds(
            @RequestParam(name = "advertisementId", required = false) Long advertisementId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size,
            @RequestParam(name = "status", defaultValue = "ACTIVE") Status status,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "sellerContact", required = false) String contact
    );

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the advertisement", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdvertisementDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Advertisement not found", content = @Content)
            }
    )
    @Operation(summary = "Выводит объявление по его ID")
    ResponseEntity<AdvertisementDTO> loadAdById(@PathVariable("id") Long id);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Advertisement was created", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdvertisementRegistrationResponse.class))),
            }
    )
    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Создает новое объявление")
    ResponseEntity<AdvertisementRegistrationResponse> createAd(
            @Valid @RequestBody AdvertisementRegistrationRequest adRequest
    );

    @PutMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Advertisement was updated", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = AdvertisementDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @Operation(summary = "Обновляет информацию об объявлении")
    ResponseEntity<AdvertisementDTO> updateAd(
            @PathVariable("id") Long id,
            @Valid @RequestBody AdvertisementRegistrationRequest adRequest
    );

    @DeleteMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Advertisement was deleted", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Advertisement not found", content = @Content)
            }
    )
    @Operation(summary = "Удаляет объявление")
    ResponseEntity<Void> deleteAd(@PathVariable("id") Long id);

    @PostMapping(value = "/{id}/images", consumes = {MULTIPART_FORM_DATA_VALUE})
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Images were added to advertisement", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ImageDTO.class)))
            }
    )
    @Operation(summary = "Добавляет фотографии в объявление")
    ResponseEntity<List<ImageDTO>> uploadImage(
            @PathVariable("id") Long id,
            @RequestPart(name = "files", required = false) MultipartFile[] files
    );

    @PutMapping("/{id}/book")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Advertisement was booked", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema)),
                    @ApiResponse(responseCode = "404", description = "Advertisement not found", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden access", content = @Content)
            }
    )
    @Operation(summary = "Бронирует объявление")
    ResponseEntity<Boolean> bookAdvertisementById(@PathVariable("id") Long id);
}
