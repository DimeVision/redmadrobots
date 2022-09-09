package com.dimevision.redmadrobots.model.dto.token;

import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import lombok.Builder;
import lombok.Data;

/**
 * @author Dimevision
 * @version 0.1
 */

@Builder
@Data
public class RefreshTokenResponse {

    String accessToken;

    UserDTO user;
}
