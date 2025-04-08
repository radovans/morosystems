package cz.sinko.morosystems.service.mapper;

import static cz.sinko.morosystems.configuration.Constants.ADMIN_ROLE;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import cz.sinko.morosystems.facade.dto.UserDto;
import cz.sinko.morosystems.repository.model.Role;
import cz.sinko.morosystems.repository.model.User;

/**
 * Mapper between User and UserDto.
 *
 * @author Radovan Šinko
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Get instance of UserMapper.
     *
     * @return instance of UserMapper
     */
    static UserMapper t() {
        return Mappers.getMapper(UserMapper.class);
    }

    /**
     * Map User to UserDto.
     *
     * @param source user
     * @return userDto
     */
    @Mapping(target = "admin", expression = "java(mapAuthoritiesToAdmin(source.getAuthorities()))")
    UserDto toUserDto(User source);

    /**
     * Map UserDto to User
     *
     * @param source userDto
     * @return user
     */
    @Mapping(target = "authorities", ignore = true)
    User toUser(UserDto source);

    /**
     * Map list of Users to list of UserDtos.
     *
     * @param users list of users
     * @return list of userDtos
     */
    List<UserDto> toUsers(List<User> users);

    /**
     * Map list of UserDtos to list of Users.
     *
     * @param userDtos list of userDtos
     * @return list of users
     */
    List<User> toUserDtos(List<UserDto> userDtos);

    /**
     * Custom mapping to set the admin field in UserDto based on authorities.
     *
     * @param authorities set of roles
     * @return true if ADMIN_ROLE is present, false otherwise
     */
    default boolean mapAuthoritiesToAdmin(Set<Role> authorities) {
        return authorities != null && authorities.stream()
                .anyMatch(role -> ADMIN_ROLE.equals(role.getAuthority()));
    }
}
