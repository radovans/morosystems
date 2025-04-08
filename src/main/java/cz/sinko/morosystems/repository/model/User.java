package cz.sinko.morosystems.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity.
 *
 * @author Radovan Šinko
 */
@Data
@Entity(name = "users")
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    private String name;
}