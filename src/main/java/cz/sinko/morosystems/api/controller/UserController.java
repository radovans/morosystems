package cz.sinko.morosystems.api.controller;

import static cz.sinko.morosystems.api.ApiUris.ROOT_URI_USERS;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.UserFacade;
import cz.sinko.morosystems.facade.dto.UserDto;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final long id) throws ResourceNotFoundException {
        log.info("Getting user with id: '{}'", id);
        return ResponseEntity.ok(userFacade.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        log.info("Getting all users");
        return ResponseEntity.ok().body(userFacade.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid final UserDto userDto) {
        log.info("Creating new user: '{}'", userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.createUser(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final long id) throws ResourceNotFoundException {
        log.info("Deleting user with id: '{}'", id);
        userFacade.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable final long id, @RequestBody @Valid final UserDto userDto)
            throws ResourceNotFoundException {
        log.info("Updating user with id: '{}', '{}'", id, userDto);
        return ResponseEntity.ok().body(userFacade.updateUser(id, userDto));
    }
}
