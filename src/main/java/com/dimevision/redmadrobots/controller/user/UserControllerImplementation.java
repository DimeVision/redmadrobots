package com.dimevision.redmadrobots.controller.user;

import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationResponse;
import com.dimevision.redmadrobots.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@RestController
@RequiredArgsConstructor
public class UserControllerImplementation implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDTO>> loadUsers() {
        List<UserDTO> users = userService.findAllUsers();

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDTO> loadUserById(Long id) {
        UserDTO user = userService.findUserById(id);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserRegistrationResponse> createUser(UserRegistrationRequest request) {
        UserRegistrationResponse userResponse = userService.createUser(request);

        return ResponseEntity.status(201).body(userResponse);
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(Long id, UserRegistrationRequest request) {
        UserDTO user = userService.updateUser(id, request);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserDTO> deleteUser(Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }
}
