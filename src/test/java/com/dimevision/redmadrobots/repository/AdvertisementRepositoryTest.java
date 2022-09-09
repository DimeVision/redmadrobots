package com.dimevision.redmadrobots.repository;

import com.dimevision.redmadrobots.model.domain.Advertisement;
import com.dimevision.redmadrobots.model.domain.Role;
import com.dimevision.redmadrobots.model.domain.Status;
import com.dimevision.redmadrobots.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dimevision
 * @version 0.1
 */

@DataJpaTest
class AdvertisementRepositoryTest {

    @Autowired
    AdvertisementRepository underTest;

    @Autowired
    UserRepository userRepository;

    @Test
    void itShouldSelectAllActiveAdvertsOnly() {
        // Given
        Long id = null;
        int page = 0;
        int size = 20;
        Status status = Status.ACTIVE;
        LocalDate startDate = null;
        String title = null;
        String contact = null;
        Pageable pageable = PageRequest.of(page, size);

        User user = User.builder()
                .email("testUser@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        Advertisement advertisement1 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.INACTIVE)
                .contact("123123")
                .build();
        Advertisement advertisement2 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123213")
                .build();

        userRepository.save(user);
        underTest.save(advertisement1);
        underTest.save(advertisement2);

        // When
        List<Advertisement> allActiveAdverts = underTest.findAllActiveAdverts(pageable, status, id, startDate, title, contact);

        // Then
        assertThat(allActiveAdverts.size()).isEqualTo(1);
    }

    @Test
    void itShouldSelectAllInactiveAdvertsOnly() {
        // Given
        Long id = null;
        int page = 0;
        int size = 20;
        Status status = Status.INACTIVE;
        LocalDate startDate = null;
        String title = null;
        String contact = null;
        Pageable pageable = PageRequest.of(page, size);

        User user = User.builder()
                .email("testUser@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        Advertisement advertisement1 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.INACTIVE)
                .contact("123123")
                .build();
        Advertisement advertisement2 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123213")
                .build();

        userRepository.save(user);
        underTest.save(advertisement1);
        underTest.save(advertisement2);

        // When
        List<Advertisement> allActiveAdverts = underTest.findAllActiveAdverts(pageable, status, id, startDate, title, contact);

        // Then
        assertThat(allActiveAdverts.size()).isEqualTo(1);
    }

    @Test
    void itShouldSelectAllActiveAdvertsOnlyByTitle() {
        // Given
        Long id = null;
        int page = 0;
        int size = 20;
        Status status = Status.ACTIVE;
        LocalDate startDate = null;
        String title = "Test";
        String contact = null;
        Pageable pageable = PageRequest.of(page, size);

        User user = User.builder()
                .email("testUser@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        Advertisement advertisement1 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123")
                .build();
        Advertisement advertisement2 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123213")
                .build();

        userRepository.save(user);
        underTest.save(advertisement1);
        underTest.save(advertisement2);

        // When
        List<Advertisement> allActiveAdverts = underTest.findAllActiveAdverts(pageable, status, id, startDate, title, contact);

        // Then
        assertThat(allActiveAdverts.size()).isEqualTo(2);
    }

    @Test
    void itShouldSelectAllActiveAdvertsOnlyByContact() {
        // Given
        Long id = null;
        int page = 0;
        int size = 20;
        Status status = Status.ACTIVE;
        LocalDate startDate = null;
        String title = null;
        String contact = "123123";
        Pageable pageable = PageRequest.of(page, size);

        User user = User.builder()
                .email("testUser@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        Advertisement advertisement1 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123")
                .build();
        Advertisement advertisement2 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123124")
                .build();

        userRepository.save(user);
        underTest.save(advertisement1);
        underTest.save(advertisement2);

        // When
        List<Advertisement> allActiveAdverts = underTest.findAllActiveAdverts(pageable, status, id, startDate, title, contact);

        // Then
        assertThat(allActiveAdverts.size()).isEqualTo(1);
    }

    @Test
    void itShouldSelectAllActiveAdvertsOnlyByStartDate() {
        // Given
        Long id = null;
        int page = 0;
        int size = 20;
        Status status = Status.ACTIVE;
        LocalDate startDate = LocalDate.now().minusDays(1);
        String title = null;
        String contact = null;
        Pageable pageable = PageRequest.of(page, size);

        User user = User.builder()
                .email("testUser@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();

        Advertisement advertisement1 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123123")
                .build();
        Advertisement advertisement2 = Advertisement.builder()
                .title("Test")
                .description("test")
                .user(user)
                .status(Status.ACTIVE)
                .contact("123124")
                .build();

        userRepository.save(user);
        underTest.save(advertisement1);
        underTest.save(advertisement2);

        // When
        List<Advertisement> allActiveAdverts = underTest.findAllActiveAdverts(pageable, status, id, startDate, title, contact);

        // Then
        assertThat(allActiveAdverts.size()).isEqualTo(2);
    }
}