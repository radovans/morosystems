package cz.sinko.morosystems.service.impl;

import static cz.sinko.morosystems.configuration.Constants.ADMIN_ROLE;
import static cz.sinko.morosystems.configuration.Constants.USER_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.repository.UserRepository;
import cz.sinko.morosystems.repository.model.Role;
import cz.sinko.morosystems.repository.model.User;

/**
 * Test class for {@link UserServiceImpl}
 *
 * @author Radovan Šinko
 */
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserById_ReturnsUser_WhenUserExists() throws ResourceNotFoundException {
        final Role role = Role.builder().id(1L).authority(USER_ROLE).build();
        final User user = User.builder().id(1L).name("John Doe").username("johndoe").password("password").authorities(Set.of(role)).build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        final User result = userService.find(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("johndoe", result.getUsername());
        assertEquals("password", result.getPassword());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findUserById_ThrowsException_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.find(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findAllUsers_ReturnsSortedUsers() {
        final Role role = Role.builder().id(1L).authority(USER_ROLE).build();
        final Sort sort = Sort.by("id");
        final List<User> mockUsers = List.of(
                User.builder().id(1L).name("Alice").username("alice").password("password").authorities(Set.of(role)).build(),
                User.builder().id(2L).name("Bob").username("bob").password("password").authorities(Set.of(role)).build()
        );
        when(userRepository.findAll(sort)).thenReturn(mockUsers);

        final List<User> result = userService.find(sort);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        verify(userRepository, times(1)).findAll(sort);
    }

    @Test
    void createUser_SavesAndReturnsUser() {
        final Role role = Role.builder().id(1L).authority(USER_ROLE).build();
        final User newUser = User.builder().name("John Doe").username("johndoe").password("password").authorities(Set.of(role)).build();
        final User savedUser = User.builder().id(1L).name("John Doe").username("johndoe").password("password").authorities(Set.of(role)).build();
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(newUser)).thenReturn(savedUser);

        final User result = userService.createUser(newUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("johndoe", result.getUsername());
        assertEquals(Set.of(role), result.getAuthorities());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void deleteUser_DeletesUser_WhenUserExists() throws ResourceNotFoundException {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_ThrowsException_WhenUserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void updateUser_UpdatesAndReturnsUser_WhenUserExists() throws ResourceNotFoundException {
        final Role userRole = Role.builder().id(1L).authority(USER_ROLE).build();
        final Role adminRole = Role.builder().id(2L).authority(ADMIN_ROLE).build();
        final User existingUser = User.builder().id(1L).name("John Doe").username("johndoe").password("password").authorities(Set.of(userRole)).build();
        final User updatedUser = User.builder().name("Jane Doe").username("janedoe").password("newpassword").authorities(Set.of(adminRole)).build();
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        final User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("johndoe", result.getUsername());
        assertEquals(Set.of(adminRole), result.getAuthorities());

        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_ThrowsException_WhenUserDoesNotExist() {
        final Role adminRole = Role.builder().id(2L).authority(ADMIN_ROLE).build();
        final User updatedUser = User.builder().name("Jane Doe").username("janedoe").password("newpassword").authorities(Set.of(adminRole)).build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }
}
