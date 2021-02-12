package pro.patrykkrawczyk.showcase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.showcase.domain.Person;
import pro.patrykkrawczyk.showcase.domain.Profession;
import pro.patrykkrawczyk.showcase.repository.PersonRepository;
import pro.patrykkrawczyk.showcase.repository.ProfessionRepository;
import pro.patrykkrawczyk.showcase.service.dto.PersonDTO;
import pro.patrykkrawczyk.showcase.service.mapper.PersonMapper;
import pro.patrykkrawczyk.showcase.web.rest.errors.BadRequestAlertException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Person}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final ProfessionRepository professionRepository;
    private final PersonMapper personMapper;

    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonDTO save(PersonDTO personDTO) {
        log.debug("Request to save Person : {}", personDTO);
        Person person = personMapper.toEntity(personDTO);

        List<Long> professionIds = person.getProfessions().stream().map(Profession::getId).collect(Collectors.toList());
        long countOfMatchingProf = professionRepository.countAllByIdIn(professionIds);

        if (countOfMatchingProf != professionIds.size()) {
            throw new BadRequestAlertException("Could not find mapping for provided professions", "person", "professions");
        }

        Person result = personRepository.save(person);
        return personMapper.toDto(result);
    }

    /**
     * Get all the people.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all People");

        return personRepository.findAll(pageable).map(personMapper::toDto);
    }

    /**
     * Get one person by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonDTO> findOne(Long id) {
        log.debug("Request to get Person : {}", id);

        return personRepository.findOneWithEagerRelationships(id).map(personMapper::toDto);
    }

    /**
     * Delete the person by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);

        personRepository.deleteById(id);
    }
}
