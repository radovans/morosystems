package cz.sinko.morosystems.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
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
    public User find(final long id) throws ResourceNotFoundException {
        log.info("Finding user with id: '{}'", id);
        return userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.createWith("User", "with id '" + id + "' was not found"));
    }

    @Override
    public List<User> find(final Sort sort) {
        log.info("Finding all users with sort: '{}'", sort);
        return userRepository.findAll(sort);
    }

    @Override
    public User createUser(final User user) {
        log.info("Creating user: '{}'", user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(final long id) throws ResourceNotFoundException {
        log.info("Deleting user with id: '{}'", id);
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException.createWith("User", "with id '" + id + "' was not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(final long id, final User user) throws ResourceNotFoundException {
        log.info("Updating user with id: '{}', '{}'", id, user);
        final User existingUser = find(id);
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);
    }
}
