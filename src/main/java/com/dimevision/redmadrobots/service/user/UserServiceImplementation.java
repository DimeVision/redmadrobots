package com.dimevision.redmadrobots.service.user;

import com.dimevision.redmadrobots.model.domain.User;
import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationResponse;
import com.dimevision.redmadrobots.model.mapper.UserMapper;
import com.dimevision.redmadrobots.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private static final String USER_NOT_FOUND_BY_ID = "User with id %s not exists in database";

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDTO> findAllUsers() {
        return userMapper.toUserDTOs(userRepository.findAll());
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_BY_ID, id)));

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserRegistrationResponse createUser(UserRegistrationRequest request) {
        User userToCreate = userMapper.toUser(request);

        String encryptedPassword = passwordEncoder.encode(request.password());
        userToCreate.setPassword(encryptedPassword);
        User user = userRepository.save(userToCreate);

        return userMapper.toUserRegistrationResponse(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserRegistrationRequest request) {
        User user = userRepository.findById(id)
                .map(u -> {
                    u.setEmail(request.email());
                    u.setRole(request.role());
                    return u;
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format(USER_NOT_FOUND_BY_ID, id)));
        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
