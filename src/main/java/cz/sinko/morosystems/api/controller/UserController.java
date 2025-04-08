package cz.sinko.morosystems.api.controller;

import static cz.sinko.morosystems.api.ApiUris.ROOT_URI_USERS;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.UserFacade;
import cz.sinko.morosystems.facade.dto.UserDto;
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
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable final long userId) throws ResourceNotFoundException {
        log.info("Getting user with id: '{}'", userId);
        return ResponseEntity.ok(userFacade.getUser(userId));
    }
}
