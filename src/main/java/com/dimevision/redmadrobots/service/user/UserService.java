package com.dimevision.redmadrobots.service.user;

import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationResponse;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface UserService {

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Long id);

    UserRegistrationResponse createUser(UserRegistrationRequest user);

    UserDTO updateUser(Long id, UserRegistrationRequest user);

    void deleteUser(Long id);
}
