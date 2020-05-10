package fr.sortyquizz.web.rest;

import fr.sortyquizz.service.OpenQuizzDBService;
import fr.sortyquizz.service.dto.imports.openquizzdb.ThemeQuizzDBDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link fr.sortyquizz.service.dto.imports.openquizzdb.ThemeQuizzDBDTO}.
 */
@RestController
@RequestMapping("/api")
public class OpenQuizzDBResource {

    private final Logger log = LoggerFactory.getLogger(OpenQuizzDBResource.class);
    private final OpenQuizzDBService openQuizzDBService;

    public OpenQuizzDBResource(OpenQuizzDBService openQuizzDBService) {
        this.openQuizzDBService = openQuizzDBService;
    }

    /**
     * {@code POST  /openquizz/import-quizz} : Validate the step 2 of the game
     *
     * @param themeQuizzDBDTO the stats + the list of the cards.
     * @return the {@link ResponseEntity} with status {@code 200 (Created)} and with body the refreshed UserPackDTO.
     */
    @PostMapping("/openquizz/import-quizz")
//    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Boolean> importQuizz(@RequestBody ThemeQuizzDBDTO themeQuizzDBDTO) {
        log.debug("REST request to import theme quizz DB : {}", themeQuizzDBDTO);
        Boolean success = openQuizzDBService.importQuizz(themeQuizzDBDTO);
        return ResponseEntity.ok().body(success);
    }

}
