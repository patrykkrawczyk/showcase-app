package pro.patrykkrawczyk.showcase.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.patrykkrawczyk.showcase.service.ProfessionService;
import pro.patrykkrawczyk.showcase.service.dto.ProfessionDTO;
import pro.patrykkrawczyk.showcase.web.rest.errors.BadRequestAlertException;
import pro.patrykkrawczyk.showcase.web.util.HeaderUtil;
import pro.patrykkrawczyk.showcase.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pro.patrykkrawczyk.showcase.domain.Profession}.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfessionResource {

    private static final String ENTITY_NAME = "profession";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProfessionService professionService;

    /**
     * {@code POST  /professions} : Create a new profession.
     *
     * @param professionDTO the professionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new professionDTO, or with status {@code 400 (Bad Request)} if the profession has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/professions")
    public ResponseEntity<ProfessionDTO> createProfession(@RequestBody ProfessionDTO professionDTO) throws URISyntaxException {
        log.debug("REST request to save Profession : {}", professionDTO);

        if (professionDTO.getId() != null) {
            throw new BadRequestAlertException("A new profession cannot already have an ID", ENTITY_NAME, "idexists");
        }

        ProfessionDTO result = professionService.save(professionDTO);

        return ResponseEntity.created(new URI("/api/professions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /professions} : Updates an existing profession.
     *
     * @param professionDTO the professionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated professionDTO,
     * or with status {@code 400 (Bad Request)} if the professionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the professionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/professions")
    public ResponseEntity<ProfessionDTO> updateProfession(@RequestBody ProfessionDTO professionDTO) throws URISyntaxException {
        log.debug("REST request to update Profession : {}", professionDTO);

        if (professionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        ProfessionDTO result = professionService.save(professionDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, professionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /professions} : get all the professions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of professions in body.
     */
    @GetMapping("/professions")
    public List<ProfessionDTO> getAllProfessions() {
        log.debug("REST request to get all Professions");

        return professionService.findAll();
    }

    /**
     * {@code GET  /professions/:id} : get the "id" profession.
     *
     * @param id the id of the professionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the professionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/professions/{id}")
    public ResponseEntity<ProfessionDTO> getProfession(@PathVariable Long id) {
        log.debug("REST request to get Profession : {}", id);

        Optional<ProfessionDTO> professionDTO = professionService.findOne(id);

        return ResponseUtil.wrapOrNotFound(professionDTO);
    }

    /**
     * {@code DELETE  /professions/:id} : delete the "id" profession.
     *
     * @param id the id of the professionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/professions/{id}")
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        log.debug("REST request to delete Profession : {}", id);

        professionService.delete(id);

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
