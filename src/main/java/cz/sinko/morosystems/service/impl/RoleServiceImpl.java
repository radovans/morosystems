package cz.sinko.morosystems.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import cz.sinko.morosystems.repository.RoleRepository;
import cz.sinko.morosystems.repository.model.Role;
import cz.sinko.morosystems.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link RoleService}
 *
 * @author Radovan Šinko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByAuthority(final String authority) {
        return roleRepository.findByAuthority(authority).orElseThrow(() -> new NoSuchElementException("Authority not present"));
    }
}
