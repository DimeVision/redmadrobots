package com.dimevision.redmadrobots.repository;

import com.dimevision.redmadrobots.model.domain.Role;
import com.dimevision.redmadrobots.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dimevision
 * @version 0.1
 */

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @Test
    void itShouldFindUserByEmail() {
        // Given
        String email = "testEmail@gmail.com";
        User mockUser = User.builder()
                .email(email)
                .password("1")
                .role(Role.USER)
                .build();

        underTest.save(mockUser);

        // When
        User user = underTest.findUserByEmail(email).orElseThrow();

        // Then
        assertThat(user.getEmail()).isEqualTo(email);
    }
}