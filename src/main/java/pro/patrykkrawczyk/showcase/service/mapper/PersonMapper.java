package pro.patrykkrawczyk.showcase.service.mapper;


import org.mapstruct.Mapper;
import pro.patrykkrawczyk.showcase.domain.Person;
import pro.patrykkrawczyk.showcase.service.dto.PersonDTO;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionMapper.class})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    Person toEntity(PersonDTO personDTO);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }

        Person person = new Person();
        person.setId(id);
        return person;
    }
}
