package cz.sinko.morosystems.facade.impl;

import static cz.sinko.morosystems.configuration.Constants.ADMIN_ROLE;
import static cz.sinko.morosystems.configuration.Constants.USER_ROLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import cz.sinko.morosystems.configuration.exception.ResourceNotFoundException;
import cz.sinko.morosystems.facade.dto.UserDto;
import cz.sinko.morosystems.repository.model.Role;
import cz.sinko.morosystems.repository.model.User;
import cz.sinko.morosystems.service.RoleService;
import cz.sinko.morosystems.service.UserService;
import cz.sinko.morosystems.service.mapper.UserMapper;

/**
 * Test class for {@link UserFacadeImpl}
 *
 * @author Radovan Šinko
 */
class UserFacadeImplTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @ParameterizedTest
    @MethodSource("provideUserData")
    void getUser_WhenUserExists(final User user, final UserDto expectedUserDto, final boolean isAdmin) throws ResourceNotFoundException {
        when(userService.find(1L)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(expectedUserDto);

        final UserDto result = userFacade.getUser(1L);

        assertNotNull(result);
        assertEquals(expectedUserDto.getId(), result.getId());
        assertEquals(expectedUserDto.getName(), result.getName());
        assertEquals(expectedUserDto.getUsername(), result.getUsername());
        if (isAdmin) {
            assertTrue(result.isAdmin());
        } else {
            assertFalse(result.isAdmin());
        }
        verify(userService, times(1)).find(1L);
    }

    @Test
    void getUser_ThrowsException_WhenUserDoesNotExist() throws ResourceNotFoundException {
        when(userService.find(1L)).thenThrow(ResourceNotFoundException.createWith("User", "with id '1L' was not found"));

        assertThrows(ResourceNotFoundException.class, () -> userFacade.getUser(1L));
        verify(userService, times(1)).find(1L);
    }

    @Test
    void getUsers_ReturnsListOfUserDtos() {
        final List<User> users = List.of(
                createUserWithUserRole(),
                createUserWithAdminRole()
        );
        when(userService.find(Sort.by("id").ascending())).thenReturn(users);
        when(userMapper.toUsers(users)).thenReturn(List.of(
                createUserDtoWithUserRole(),
                createUserDtoWithAdminRole()
        ));

        final List<UserDto> result = userFacade.getUsers();
        final UserDto userDto = result.get(0);
        final UserDto adminUserDto = result.get(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", userDto.getName());
        assertEquals("alice", userDto.getUsername());
        assertFalse(userDto.isAdmin());
        assertEquals("Bob", adminUserDto.getName());
        assertEquals("bob", adminUserDto.getUsername());
        assertTrue(adminUserDto.isAdmin());
        verify(userService, times(1)).find(Sort.by("id").ascending());
    }

    @Test
    void deleteUser_DeletesUser_WhenUserExists() throws ResourceNotFoundException {
        doNothing().when(userService).deleteUser(1L);

        userFacade.deleteUser(1L);

        verify(userService, times(1)).deleteUser(1L);
    }

    @ParameterizedTest
    @MethodSource("provideUserCreationData")
    void createUser_CreatesUserSuccessfully(final UserDto userDto, final Role role, final User user, final boolean isAdmin) {
        when(roleService.findByAuthority(isAdmin ? ADMIN_ROLE : USER_ROLE)).thenReturn(role);
        when(userService.createUser(user)).thenReturn(user);
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        final UserDto result = userFacade.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(isAdmin, result.isAdmin());
        verify(roleService, times(1)).findByAuthority(isAdmin ? ADMIN_ROLE : USER_ROLE);
        verify(userService, times(1)).createUser(user);
    }

    private static Stream<Arguments> provideUserData() {
        return Stream.of(
                Arguments.of(createUserWithUserRole(), createUserDtoWithUserRole(), false),
                Arguments.of(createUserWithAdminRole(), createUserDtoWithAdminRole(), true)
        );
    }

    private static Stream<Arguments> provideUserCreationData() {
        return Stream.of(
                Arguments.of(createUserDtoWithUserRole(), createUserRole(), createUserWithUserRoleWithoutId(), false),
                Arguments.of(createUserDtoWithAdminRole(), createAdminRole(), createUserWithAdminRoleWithoutId(), true)
        );
    }

    private static Role createUserRole() {
        return Role.builder().id(1L).authority(USER_ROLE).build();
    }

    private static Role createAdminRole() {
        return Role.builder().id(2L).authority(ADMIN_ROLE).build();
    }

    private static User createUserWithUserRole() {
        return User.builder()
                .id(1L)
                .name("Alice")
                .username("alice")
                .password("Password123")
                .authorities(Set.of(createUserRole()))
                .build();
    }

    private static User createUserWithUserRoleWithoutId() {
        return User.builder()
                .name("Alice")
                .username("alice")
                .password("Password123")
                .authorities(Set.of(createUserRole()))
                .build();
    }

    private static UserDto createUserDtoWithUserRole() {
        return UserDto.builder()
                .id(1L)
                .name("Alice")
                .username("alice")
                .password("Password123")
                .admin(false)
                .build();
    }

    private static User createUserWithAdminRole() {
        return User.builder()
                .id(2L)
                .name("Bob")
                .username("bob")
                .password("Password123")
                .authorities(Set.of(createAdminRole()))
                .build();
    }

    private static User createUserWithAdminRoleWithoutId() {
        return User.builder()
                .name("Bob")
                .username("bob")
                .password("Password123")
                .authorities(Set.of(createAdminRole()))
                .build();
    }

    private static UserDto createUserDtoWithAdminRole() {
        return UserDto.builder()
                .id(2L)
                .name("Bob")
                .username("bob")
                .password("Password123")
                .admin(true)
                .build();
    }
}
