package cz.sinko.morosystems.facade.impl;

import org.springframework.stereotype.Component;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.UserFacade;
import cz.sinko.morosystems.facade.dto.UserDto;
import cz.sinko.morosystems.facade.mapper.UserMapper;
import cz.sinko.morosystems.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserFacade}
 *
 * @author Radovan Šinko
 */
@Component
@AllArgsConstructor
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public UserDto getUser(final long userId) throws ResourceNotFoundException {
        log.info("Getting user with id: '{}'", userId);
        return UserMapper.t().toUserDto(userService.find(userId));
    }
}