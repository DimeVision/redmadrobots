package com.dimevision.redmadrobots.repository;

import com.dimevision.redmadrobots.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
}
