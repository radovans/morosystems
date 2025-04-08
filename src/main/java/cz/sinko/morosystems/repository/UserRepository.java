package cz.sinko.morosystems.repository;

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

}
