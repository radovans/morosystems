package cz.sinko.morosystems.service.impl;

import static cz.sinko.morosystems.configuration.Constants.ADMIN_ROLE;
import static cz.sinko.morosystems.configuration.Constants.USER_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cz.sinko.morosystems.repository.RoleRepository;
import cz.sinko.morosystems.repository.model.Role;

/**
 * Test class for {@link RoleServiceImpl}
 *
 * @author Radovan Šinko
 */
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByAuthority_ReturnsRole_WhenAuthorityExists() {
        final Role role = Role.builder().id(1L).authority(USER_ROLE).build();
        when(roleRepository.findByAuthority(USER_ROLE)).thenReturn(Optional.of(role));

        final Role result = roleService.findByAuthority(USER_ROLE);

        assertNotNull(result);
        assertEquals(USER_ROLE, result.getAuthority());
        verify(roleRepository, times(1)).findByAuthority(USER_ROLE);
    }

    @Test
    void findByAuthority_ThrowsException_WhenAuthorityDoesNotExist() {
        when(roleRepository.findByAuthority(ADMIN_ROLE)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> roleService.findByAuthority(ADMIN_ROLE));
        verify(roleRepository, times(1)).findByAuthority(ADMIN_ROLE);
    }
}
