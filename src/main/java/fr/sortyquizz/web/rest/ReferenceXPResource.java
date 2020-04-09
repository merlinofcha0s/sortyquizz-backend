package fr.sortyquizz.web.rest;

import fr.sortyquizz.service.ReferenceXPService;
import fr.sortyquizz.web.rest.errors.BadRequestAlertException;
import fr.sortyquizz.service.dto.ReferenceXPDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.sortyquizz.domain.ReferenceXP}.
 */
@RestController
@RequestMapping("/api")
public class ReferenceXPResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceXPResource.class);

    private static final String ENTITY_NAME = "referenceXP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenceXPService referenceXPService;

    public ReferenceXPResource(ReferenceXPService referenceXPService) {
        this.referenceXPService = referenceXPService;
    }

    /**
     * {@code POST  /reference-xps} : Create a new referenceXP.
     *
     * @param referenceXPDTO the referenceXPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referenceXPDTO, or with status {@code 400 (Bad Request)} if the referenceXP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reference-xps")
    public ResponseEntity<ReferenceXPDTO> createReferenceXP(@Valid @RequestBody ReferenceXPDTO referenceXPDTO) throws URISyntaxException {
        log.debug("REST request to save ReferenceXP : {}", referenceXPDTO);
        if (referenceXPDTO.getId() != null) {
            throw new BadRequestAlertException("A new referenceXP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenceXPDTO result = referenceXPService.save(referenceXPDTO);
        return ResponseEntity.created(new URI("/api/reference-xps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reference-xps} : Updates an existing referenceXP.
     *
     * @param referenceXPDTO the referenceXPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceXPDTO,
     * or with status {@code 400 (Bad Request)} if the referenceXPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referenceXPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reference-xps")
    public ResponseEntity<ReferenceXPDTO> updateReferenceXP(@Valid @RequestBody ReferenceXPDTO referenceXPDTO) throws URISyntaxException {
        log.debug("REST request to update ReferenceXP : {}", referenceXPDTO);
        if (referenceXPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferenceXPDTO result = referenceXPService.save(referenceXPDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referenceXPDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reference-xps} : get all the referenceXPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referenceXPS in body.
     */
    @GetMapping("/reference-xps")
    public ResponseEntity<List<ReferenceXPDTO>> getAllReferenceXPS(Pageable pageable) {
        log.debug("REST request to get a page of ReferenceXPS");
        Page<ReferenceXPDTO> page = referenceXPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reference-xps/:id} : get the "id" referenceXP.
     *
     * @param id the id of the referenceXPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referenceXPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reference-xps/{id}")
    public ResponseEntity<ReferenceXPDTO> getReferenceXP(@PathVariable Long id) {
        log.debug("REST request to get ReferenceXP : {}", id);
        Optional<ReferenceXPDTO> referenceXPDTO = referenceXPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referenceXPDTO);
    }

    /**
     * {@code DELETE  /reference-xps/:id} : delete the "id" referenceXP.
     *
     * @param id the id of the referenceXPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reference-xps/{id}")
    public ResponseEntity<Void> deleteReferenceXP(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceXP : {}", id);
        referenceXPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
