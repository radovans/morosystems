package cz.sinko.morosystems.api.controller.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import cz.sinko.morosystems.api.controller.request.UserCreateRequest;
import cz.sinko.morosystems.api.controller.request.UserUpdateRequest;
import cz.sinko.morosystems.api.controller.response.UserResponse;
import cz.sinko.morosystems.facade.dto.UserDto;


/**
 * Mapper between User and Request/Response UserDto.
 *
 * @author Radovan Šinko
 */
@Mapper(componentModel = "spring")
public interface UserApiMapper {

    /**
     * Get instance of UserMapper.
     *
     * @return instance of UserMapper
     */
    static UserApiMapper t() {
        return Mappers.getMapper(UserApiMapper.class);
    }

    /**
     * Map UserDto to UserResponse.
     *
     * @param source userDto
     * @return userResponse
     */
    UserResponse toResponse(UserDto source);

    /**
     * Map list of UserDtos to list of UserResponses.
     *
     * @param source userDto
     * @return userResponse
     */
    List<UserResponse> toResponse(List<UserDto> source);

    /**
     * Map CreateUserRequest to UserDto.
     *
     * @param source userResponse
     * @return userDto
     */
    @Mapping(target = "id", ignore = true)
    UserDto fromRequest(UserCreateRequest source);

    /**
     * Map UpdateUserRequest to UserDto.
     *
     * @param source userResponse
     * @return userDto
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "username", ignore = true)
    })
    UserDto fromRequest(UserUpdateRequest source);
}
