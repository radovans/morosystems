package cz.sinko.morosystems.service;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.repository.model.User;

/**
 * Service for User operations.
 *
 * @author Radovan Šinko
 */
public interface UserService {

    /**
     * Find User by ID.
     *
     * @param userId User ID
     * @return User
     * @throws ResourceNotFoundException if User was not found
     */
    User find(long userId) throws ResourceNotFoundException;
}

