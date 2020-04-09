package fr.sortyquizz.web.rest;

import fr.sortyquizz.service.ThemeScoreService;
import fr.sortyquizz.web.rest.errors.BadRequestAlertException;
import fr.sortyquizz.service.dto.ThemeScoreDTO;

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
 * REST controller for managing {@link fr.sortyquizz.domain.ThemeScore}.
 */
@RestController
@RequestMapping("/api")
public class ThemeScoreResource {

    private final Logger log = LoggerFactory.getLogger(ThemeScoreResource.class);

    private static final String ENTITY_NAME = "themeScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeScoreService themeScoreService;

    public ThemeScoreResource(ThemeScoreService themeScoreService) {
        this.themeScoreService = themeScoreService;
    }

    /**
     * {@code POST  /theme-scores} : Create a new themeScore.
     *
     * @param themeScoreDTO the themeScoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeScoreDTO, or with status {@code 400 (Bad Request)} if the themeScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-scores")
    public ResponseEntity<ThemeScoreDTO> createThemeScore(@Valid @RequestBody ThemeScoreDTO themeScoreDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeScore : {}", themeScoreDTO);
        if (themeScoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeScoreDTO result = themeScoreService.save(themeScoreDTO);
        return ResponseEntity.created(new URI("/api/theme-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-scores} : Updates an existing themeScore.
     *
     * @param themeScoreDTO the themeScoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeScoreDTO,
     * or with status {@code 400 (Bad Request)} if the themeScoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeScoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-scores")
    public ResponseEntity<ThemeScoreDTO> updateThemeScore(@Valid @RequestBody ThemeScoreDTO themeScoreDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeScore : {}", themeScoreDTO);
        if (themeScoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemeScoreDTO result = themeScoreService.save(themeScoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, themeScoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /theme-scores} : get all the themeScores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeScores in body.
     */
    @GetMapping("/theme-scores")
    public ResponseEntity<List<ThemeScoreDTO>> getAllThemeScores(Pageable pageable) {
        log.debug("REST request to get a page of ThemeScores");
        Page<ThemeScoreDTO> page = themeScoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-scores/:id} : get the "id" themeScore.
     *
     * @param id the id of the themeScoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeScoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-scores/{id}")
    public ResponseEntity<ThemeScoreDTO> getThemeScore(@PathVariable Long id) {
        log.debug("REST request to get ThemeScore : {}", id);
        Optional<ThemeScoreDTO> themeScoreDTO = themeScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeScoreDTO);
    }

    /**
     * {@code DELETE  /theme-scores/:id} : delete the "id" themeScore.
     *
     * @param id the id of the themeScoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-scores/{id}")
    public ResponseEntity<Void> deleteThemeScore(@PathVariable Long id) {
        log.debug("REST request to delete ThemeScore : {}", id);
        themeScoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
