package com.dimevision.redmadrobots.service.advertisement;

import com.dimevision.redmadrobots.model.domain.Advertisement;
import com.dimevision.redmadrobots.model.domain.Image;
import com.dimevision.redmadrobots.model.domain.Status;
import com.dimevision.redmadrobots.model.domain.User;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementDTO;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.advertisement.AdvertisementRegistrationResponse;
import com.dimevision.redmadrobots.model.dto.image.ImageDTO;
import com.dimevision.redmadrobots.model.mapper.AdvertisementMapper;
import com.dimevision.redmadrobots.model.mapper.ImageMapper;
import com.dimevision.redmadrobots.repository.AdvertisementRepository;
import com.dimevision.redmadrobots.repository.ImageRepository;
import com.dimevision.redmadrobots.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dimevision.redmadrobots.model.domain.DealStatus.BOOKED;
import static com.dimevision.redmadrobots.model.domain.Status.INACTIVE;

/**
 * @author Dimevision
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImplementation implements AdvertisementService {

    private static final String AD_NOT_FOUND_BY_ID = "Ad with id: %s not exists in database";
    private static final String FORBIDDEN_ACCESS = "User doesn't have permission";

    private final AdvertisementRepository adRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    private final AdvertisementMapper adMapper;
    private final ImageMapper imageMapper;

    @Override
    public List<AdvertisementDTO> findAllAdvertisements(Long advertisementId, Integer page, Integer size, Status status, LocalDate startDate, String title, String contact) {

        return adMapper.toAdvertisementDTOs(adRepository.findAllActiveAdverts(PageRequest.of(page, size), status, advertisementId, startDate, title, contact));
    }

    @Override
    public AdvertisementDTO findAdvertisementById(Long id) {
        Advertisement advertisement = adRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(AD_NOT_FOUND_BY_ID, id)));

        return adMapper.toAdvertisementDTO(advertisement);
    }

    @Override
    @Transactional
    public AdvertisementRegistrationResponse createAdvertisement(AdvertisementRegistrationRequest adRequest) {
        Advertisement advertisement = adMapper.toAdvertisement(adRequest);

        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findUserByEmail(principal)
                .orElseThrow(() -> new IllegalArgumentException("Fa"));

        advertisement.setUser(user);
        advertisement = adRepository.save(advertisement);

        return adMapper.toAdvertisementResponse(advertisement);
    }

    @Override
    @Transactional
    public List<ImageDTO> addImagesToAdvertisement(Long id, MultipartFile[] images) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User authorizedUser = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User email:%s doesn't exists in database", username)));

        Advertisement advertisement = adRepository.findById(id)
                .orElseThrow();
        if (!advertisement.getUser().equals(authorizedUser)) {
            throw new IllegalArgumentException(FORBIDDEN_ACCESS);
        }

        Set<Image> images1 = new HashSet<>();
        if (images != null) {
            images1 = Arrays.stream(images).map(i -> {
                Image im;
                try {
                    im = Image.builder()
                            .name(i.getName())
                            .type(i.getContentType())
                            .imageCode(i.getBytes())
                            .advertisements(advertisement)
                            .build();
                    imageRepository.save(im);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                return im;
            }).collect(Collectors.toSet());

            advertisement.setImages(images1);
        }

        return imageMapper.toImageDTOs(images1);
    }

    @Override
    public AdvertisementDTO updateAdvertisement(Long id, AdvertisementRegistrationRequest adRequest) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow();

        Advertisement advertisement = adRepository.findById(id)
                .map(a -> {
                    if (a.getUser().equals(user)) {
                        a.setTitle(adRequest.title());
                        a.setDescription(adRequest.description());
                        a.setContact(adRequest.contact());
                        return a;
                    } else throw new IllegalArgumentException(FORBIDDEN_ACCESS);
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format(AD_NOT_FOUND_BY_ID, id)));

        return adMapper.toAdvertisementDTO(advertisement);
    }

    @Override
    public void bookAdvertisement(Long id) {
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User authenticatedUser = userRepository.findUserByEmail(principal)
                .orElseThrow();
        Advertisement advertisement = adRepository.findById(id)
                .filter(a -> (!a.getStatus().equals(INACTIVE)) && (!a.getDealStatus().equals(BOOKED)))
                .orElseThrow(() -> new IllegalArgumentException("Объявление уже забронировано или неактивно"));

        if (advertisement.getUser().equals(authenticatedUser)) {
            throw new IllegalArgumentException("Вы не можете забронировать свое объявление");
        }

        advertisement.setCustomerId(authenticatedUser.getId());
        advertisement.setDealStatus(BOOKED);

        adRepository.save(advertisement);
    }

    @Override
    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }
}
