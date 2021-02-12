package pro.patrykkrawczyk.showcase.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.patrykkrawczyk.showcase.domain.Profession;
import pro.patrykkrawczyk.showcase.repository.ProfessionRepository;
import pro.patrykkrawczyk.showcase.service.dto.ProfessionDTO;
import pro.patrykkrawczyk.showcase.service.mapper.ProfessionMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Profession}.
 */
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ProfessionService {

    private final ProfessionRepository professionRepository;
    private final ProfessionMapper professionMapper;

    /**
     * Save a profession.
     *
     * @param professionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfessionDTO save(ProfessionDTO professionDTO) {
        log.debug("Request to save Profession : {}", professionDTO);

        Profession profession = professionMapper.toEntity(professionDTO);
        Profession result = professionRepository.save(profession);

        return professionMapper.toDto(result);
    }

    /**
     * Get all the professions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProfessionDTO> findAll() {
        log.debug("Request to get all Professions");

        return professionRepository.findAll().stream()
            .map(professionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one profession by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfessionDTO> findOne(Long id) {
        log.debug("Request to get Profession : {}", id);

        return professionRepository.findById(id).map(professionMapper::toDto);
    }

    /**
     * Delete the profession by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profession : {}", id);

        professionRepository.deleteById(id);
    }
}
