package cz.sinko.morosystems.facade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for User.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 255, message = "Name must be at most 255 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "Username must not be blank")
    @Size(max = 255, message = "Username must be at most 255 characters")
    private String username;

    @NotBlank(message = "Password must not be blank")
    @Size(max = 255, message = "Password must be at most 255 characters")
    private String password;

    private boolean admin;
}
