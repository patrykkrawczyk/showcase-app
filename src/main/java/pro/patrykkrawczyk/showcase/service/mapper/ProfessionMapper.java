package pro.patrykkrawczyk.showcase.service.mapper;


import org.mapstruct.Mapper;
import pro.patrykkrawczyk.showcase.domain.Profession;
import pro.patrykkrawczyk.showcase.service.dto.ProfessionDTO;

/**
 * Mapper for the entity {@link Profession} and its DTO {@link ProfessionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessionMapper extends EntityMapper<ProfessionDTO, Profession> {

    Profession toEntity(ProfessionDTO professionDTO);

    default Profession fromId(Long id) {
        if (id == null) {
            return null;
        }

        Profession profession = new Profession();
        profession.setId(id);
        return profession;
    }
}
