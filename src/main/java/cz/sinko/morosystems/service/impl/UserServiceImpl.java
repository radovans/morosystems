package cz.sinko.morosystems.service.impl;

import org.springframework.stereotype.Service;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.repository.UserRepository;
import cz.sinko.morosystems.repository.model.User;
import cz.sinko.morosystems.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link UserService}
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User find(final long userId) throws ResourceNotFoundException {
        log.info("Finding user with id: '{}'", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.createWith("User", "with id '" + userId + "' was not found"));
    }
}

