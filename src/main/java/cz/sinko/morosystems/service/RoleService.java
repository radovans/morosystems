package cz.sinko.morosystems.service;

import cz.sinko.morosystems.repository.model.Role;

/**
 * Service for User operations.
 *
 * @author Radovan Šinko
 */
public interface RoleService {

    /**
     * Find Role by authority.
     *
     * @param authority Role authority
     * @return Role
     */
    Role findByAuthority(String authority);
}
