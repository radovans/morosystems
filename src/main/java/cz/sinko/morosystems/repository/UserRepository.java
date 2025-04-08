package cz.sinko.morosystems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.sinko.morosystems.repository.model.User;

/**
 * Repository for User entity.
 *
 * @author Radovan Šinko
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by its username.
     *
     * @param email the username of the user
     * @return an Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String email);
}
