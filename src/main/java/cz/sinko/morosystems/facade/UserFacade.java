package cz.sinko.morosystems.facade;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.dto.UserDto;

/**
 * Facade for User operations.
 *
 * @author Radovan Šinko
 */
public interface UserFacade {

    /**
     * Get User by ID.
     *
     * @param userId User ID
     * @return User DTO
     */
    UserDto getUser(long userId) throws ResourceNotFoundException;
}