package cz.sinko.morosystems.api.dto.response.user;

import lombok.Builder;
import lombok.Data;

/**
 * Response object for User.
 *
 * @author Radovan Šinko
 */
@Data
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String username;

    private boolean admin;
}
