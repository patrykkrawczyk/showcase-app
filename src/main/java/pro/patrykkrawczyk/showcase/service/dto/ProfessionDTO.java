package pro.patrykkrawczyk.showcase.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.patrykkrawczyk.showcase.domain.Profession} entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProfessionDTO implements Serializable {
    
    private Long id;
    private String name;
}
