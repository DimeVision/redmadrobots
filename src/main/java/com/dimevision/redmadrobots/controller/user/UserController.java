package com.dimevision.redmadrobots.controller.user;

import com.dimevision.redmadrobots.model.dto.user.UserDTO;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationRequest;
import com.dimevision.redmadrobots.model.dto.user.UserRegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.*;

/**
 * @author Dimevision
 * @version 0.1
 */

@Tag(name = "Пользователи")
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
public interface UserController {

    @GetMapping("/")
    @Operation(summary = "Выводит список пользователей")
    ResponseEntity<List<UserDTO>> loadUsers();

    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Found the user", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @Operation(summary = "Выводит пользователя по его ID")
    ResponseEntity<UserDTO> loadUserById(@PathVariable("id") Long id);

    @PostMapping("/")
    @PreAuthorize("permitAll()")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRegistrationResponse.class)))
            }
    )
    @Operation(summary = "Создает нового пользователя")
    ResponseEntity<UserRegistrationResponse> createUser(@Valid @RequestBody UserRegistrationRequest registrationRequest);

    @PutMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User was updated", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @Operation(summary = "Обновляет информацию пользователя")
    ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRegistrationRequest updateRequest);

    @DeleteMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User was deleted", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
            }
    )
    @Operation(summary = "Удаляет пользователя")
    ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id);
}
