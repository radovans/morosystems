package cz.sinko.morosystems.facade.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.UserFacade;
import cz.sinko.morosystems.facade.dto.UserDto;
import cz.sinko.morosystems.facade.mapper.UserMapper;
import cz.sinko.morosystems.repository.model.User;
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
    public UserDto getUser(final long id) throws ResourceNotFoundException {
        log.info("Getting user with id: '{}'", id);
        return UserMapper.t().toUserDto(userService.find(id));
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("Getting all users");
        List<User> users = userService.find(Sort.by("id").ascending());
        return UserMapper.t().toUsers(users);
    }

    @Override
    public UserDto createUser(final UserDto userDto) {
        log.info("Creating user: '{}'", userDto);
        final User user = User.builder()
                .name(userDto.getName())
                .build();
        return UserMapper.t().toUserDto(userService.createUser(user));
    }

    @Override
    public void deleteUser(final long id) throws ResourceNotFoundException {
        log.info("Deleting user with id: '{}'", id);
        userService.deleteUser(id);
    }

    @Override
    public UserDto updateUser(final long id, final UserDto userDto) throws ResourceNotFoundException {
        log.info("Updating user with id: '{}', '{}'", id, userDto);
        final User user = User.builder()
                .name(userDto.getName())
                .build();
        return UserMapper.t().toUserDto(userService.updateUser(id, user));
    }
}