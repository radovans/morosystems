package cz.sinko.morosystems.api.controller;

import static cz.sinko.morosystems.api.ApiUris.ROOT_URI_USERS;
import static net.logstash.logback.marker.Markers.append;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.morosystems.api.mapper.UserApiMapper;
import cz.sinko.morosystems.api.dto.request.user.UserCreateRequest;
import cz.sinko.morosystems.api.dto.request.user.UserUpdateRequest;
import cz.sinko.morosystems.api.dto.response.user.UserResponse;
import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.UserFacade;
import cz.sinko.morosystems.repository.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for User management.
 *
 * @author Radovan Šinko
 */
@RestController
@RequestMapping(ROOT_URI_USERS)
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserFacade userFacade;

    private final UserApiMapper userApiMapper;

    /**
     * Get user by id.
     *
     * @param id the id of the user
     * @return the user
     * @throws ResourceNotFoundException if user not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable final long id) throws ResourceNotFoundException {
        log.info("Call getUser with id '{}'", id);
        return ResponseEntity.ok(userApiMapper.toResponse(userFacade.getUser(id)));
    }

    /**
     * Get all users.
     *
     * @return the list of users
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        log.info("Call getUsers");
        return ResponseEntity.ok().body(userApiMapper.toResponse(userFacade.getUsers()));
    }

    /**
     * Create new user.
     *
     * @param userCreateRequest the user create request
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid final UserCreateRequest userCreateRequest) {
        log.info(append("userCreateRequest", userCreateRequest), "Call createUser with request '{}'", userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userApiMapper.toResponse(userFacade.createUser(userApiMapper.fromRequest(userCreateRequest))));
    }

    /**
     * Delete user by id.
     *
     * @param loggedUser the logged user
     * @param id the id of the user to delete
     * @return void
     * @throws ResourceNotFoundException if user not found
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal final User loggedUser, @PathVariable final long id) throws ResourceNotFoundException {
        log.info("Call deleteUser with id '{}'", id);
        userFacade.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update user by id.
     *
     * @param loggedUser the logged user
     * @param id the id of the user to update
     * @param userUpdateRequest the user update request
     * @return the updated user
     * @throws ResourceNotFoundException if user not found
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal final User loggedUser, @PathVariable final long id,
            @RequestBody @Valid final UserUpdateRequest userUpdateRequest)
            throws ResourceNotFoundException {
        log.info(append("userUpdateRequest", userUpdateRequest), "Call updateUser with id '{}' and request '{}'", id, userUpdateRequest);
        return ResponseEntity.ok().body(userApiMapper.toResponse(userFacade.updateUser(loggedUser, id,
                userApiMapper.fromRequest(userUpdateRequest))));
    }
}
