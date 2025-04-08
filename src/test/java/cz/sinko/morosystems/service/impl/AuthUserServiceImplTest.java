package cz.sinko.morosystems.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cz.sinko.morosystems.repository.UserRepository;
import cz.sinko.morosystems.repository.model.User;

/**
 * Test class for {@link AuthUserServiceImpl}
 *
 * @author Radovan Šinko
 */
class AuthUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthUserServiceImpl authUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_ReturnsUserDetails_WhenUserExists() {
        final User user = User.builder().id(1L).username("johndoe").password("password").build();
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));

        final User result = (User) authUserService.loadUserByUsername("johndoe");

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(userRepository, times(1)).findByUsername("johndoe");
    }

    @Test
    void loadUserByUsername_ThrowsException_WhenUserDoesNotExist() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authUserService.loadUserByUsername("nonexistent"));
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }
}
