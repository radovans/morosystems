package cz.sinko.morosystems.facade.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import cz.sinko.morosystems.facade.dto.UserDto;
import cz.sinko.morosystems.repository.model.User;


/**
 * Mapper for User entity.
 *
 * @author Radovan Šinko
 */
@Mapper()
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
    UserDto toUserDto(final User source);

    /**
     * Map UserDto to User
     *
     * @param source userDto
     * @return user
     */
    User toUser(final UserDto source);
}
