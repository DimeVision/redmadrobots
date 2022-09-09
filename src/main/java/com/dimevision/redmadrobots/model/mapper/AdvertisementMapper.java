package com.dimevision.redmadrobots.model.mapper;

import com.dimevision.redmadrobots.model.domain.Advertisement;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Mapper
public interface AdvertisementMapper {

    Advertisement toAdvertisement(AdvertisementDTO advertisementDTO);

    Advertisement toAdvertisement(AdvertisementRegistrationRequest advertisementRequest);

    List<Advertisement> toAdvertisements(List<AdvertisementDTO> advertisementDTOs);

    AdvertisementDTO toAdvertisementDTO(Advertisement advertisement);

    List<AdvertisementDTO> toAdvertisementDTOs(List<Advertisement> advertisements);

    AdvertisementRegistrationResponse toAdvertisementResponse(Advertisement advertisement);

    List<AdvertisementRegistrationResponse> toAdvertisementResponses(List<Advertisement> advertisements);

    AdvertisementRegistrationRequest toAdvertisementRequest(Advertisement advertisement);

    List<AdvertisementRegistrationRequest> toAdvertisementRequests(List<Advertisement> advertisements);
}
