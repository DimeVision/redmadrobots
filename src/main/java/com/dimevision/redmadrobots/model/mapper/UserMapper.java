package com.dimevision.redmadrobots.model.mapper;

import com.dimevision.redmadrobots.model.domain.User;
import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

@Mapper
public interface UserMapper {

    UserDTO toUserDTO(User user);

    UserRegistrationResponse toUserRegistrationResponse(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User toUser(UserDTO userDTO);

    User toUser(UserRegistrationRequest userRegistrationRequest);

    List<User> toUsers(List<UserDTO> userDTOs);
}
