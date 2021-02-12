package pro.patrykkrawczyk.showcase.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link pro.patrykkrawczyk.showcase.domain.Person} entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PersonDTO implements Serializable {
    
    private Long id;
    private String firstName;
    private String lastName;
    private Set<ProfessionDTO> professions = new HashSet<>();
}
