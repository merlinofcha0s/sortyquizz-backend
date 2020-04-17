package fr.sortyquizz.web.rest;

import fr.sortyquizz.security.SecurityUtils;
import fr.sortyquizz.service.UserPackService;
import fr.sortyquizz.service.dto.UserPackDTO;
import fr.sortyquizz.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.sortyquizz.domain.UserPack}.
 */
@RestController
@RequestMapping("/api")
public class UserPackResource {

    private final Logger log = LoggerFactory.getLogger(UserPackResource.class);

    private static final String ENTITY_NAME = "userPack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPackService userPackService;

    public UserPackResource(UserPackService userPackService) {
        this.userPackService = userPackService;
    }

    /**
     * {@code POST  /user-packs} : Create a new userPack.
     *
     * @param userPackDTO the userPackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPackDTO, or with status {@code 400 (Bad Request)} if the userPack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-packs")
    public ResponseEntity<UserPackDTO> createUserPack(@Valid @RequestBody UserPackDTO userPackDTO) throws URISyntaxException {
        log.debug("REST request to save UserPack : {}", userPackDTO);
        if (userPackDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPackDTO result = userPackService.save(userPackDTO);
        return ResponseEntity.created(new URI("/api/user-packs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-packs} : Updates an existing userPack.
     *
     * @param userPackDTO the userPackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPackDTO,
     * or with status {@code 400 (Bad Request)} if the userPackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-packs")
    public ResponseEntity<UserPackDTO> updateUserPack(@Valid @RequestBody UserPackDTO userPackDTO) throws URISyntaxException {
        log.debug("REST request to update UserPack : {}", userPackDTO);
        if (userPackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserPackDTO result = userPackService.save(userPackDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-packs} : get all the userPacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPacks in body.
     */
    @GetMapping("/user-packs")
    public ResponseEntity<List<UserPackDTO>> getAllUserPacks(Pageable pageable) {
        log.debug("REST request to get a page of UserPacks");
        Page<UserPackDTO> page = userPackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-packs/:id} : get the "id" userPack.
     *
     * @param id the id of the userPackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-packs/{id}")
    public ResponseEntity<UserPackDTO> getUserPack(@PathVariable Long id) {
        log.debug("REST request to get UserPack : {}", id);
        Optional<UserPackDTO> userPackDTO = userPackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPackDTO);
    }

    /**
     * {@code DELETE  /user-packs/:id} : delete the "id" userPack.
     *
     * @param id the id of the userPackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-packs/{id}")
    public ResponseEntity<Void> deleteUserPack(@PathVariable Long id) {
        log.debug("REST request to delete UserPack : {}", id);
        userPackService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /user-packs/get-count-number-by-user} : get all the user pack for the connected user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the number of the user pack.
     */
    @GetMapping("/user-packs/get-count-number-by-user")
    public ResponseEntity<Integer> getCountUserPackByConnectedUser() {
        log.debug("REST request to UserPack for the connected user");
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResource.AccountResourceException("Current user login not found"));
        int countUserPack = userPackService.countByUserLogin(userLogin);
        return ResponseEntity.ok().body(countUserPack);
    }

    /**
     * {@code GET  /user-packs/get-count-number-by-user} : get all the user pack for the connected user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the number of the user pack.
     */
    @GetMapping("/user-packs/get-by-user")
    public ResponseEntity<List<UserPackDTO>> getUserPackByConnectedUser() {
        log.debug("REST request to UserPack for the connected user");
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResource.AccountResourceException("Current user login not found"));
        List<UserPackDTO> allUserPackForConnectedUser = userPackService.getByUserLogin(userLogin);
        return ResponseEntity.ok().body(allUserPackForConnectedUser);
    }

    /**
     * {@code POST  /user-packs/complete} : Create a new userPack.
     *
     * @param userPackDTO the userPackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPackDTO, or with status {@code 400 (Bad Request)} if the userPack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-packs/complete-step-1")
    public ResponseEntity<Boolean> completeUserPackForStep1(@RequestBody UserPackDTO userPackDTO) {
        log.debug("REST request to save UserPack : {}", userPackDTO);
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResource.AccountResourceException("Current user login not found"));
        boolean result = userPackService.completePackForStep1(userPackDTO, userLogin);
        return ResponseEntity.ok().body(result);
    }
}
