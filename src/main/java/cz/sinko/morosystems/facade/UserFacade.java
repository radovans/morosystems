package cz.sinko.morosystems.facade;

import java.util.List;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.service.dto.UserDto;
import cz.sinko.morosystems.repository.model.User;

/**
 * Facade for User operations.
 *
 * @author Radovan Šinko
 */
public interface UserFacade {

    /**
     * Get User by id.
     *
     * @param id User id
     * @return User DTO
     */
    UserDto getUser(long id) throws ResourceNotFoundException;

    /**
     * Get all Users.
     *
     * @return list of User DTOs
     */
    List<UserDto> getUsers();

    /**
     * Create User.
     *
     * @param userDto User DTO
     * @return created User DTO
     */
    UserDto createUser(UserDto userDto);

    /**
     * Delete User by id.
     *
     * @param id User id
     * @throws ResourceNotFoundException if User was not found
     */
    void deleteUser(long id) throws ResourceNotFoundException;

    /**
     * Update User by id.
     *
     * @param loggedUser User who is logged in
     * @param id         User id
     * @param userDto    User DTO
     * @return updated User DTO
     * @throws ResourceNotFoundException if User was not found
     */
    UserDto updateUser(User loggedUser, long id, UserDto userDto) throws ResourceNotFoundException;
}
