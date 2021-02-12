package pro.patrykkrawczyk.showcase.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * A DTO for the {@link pro.patrykkrawczyk.showcase.domain.BankAccount} entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BankAccountDTO implements Serializable {
    
    private Long id;
    private String name;
    private Long balance;
    private Long personId;
}
