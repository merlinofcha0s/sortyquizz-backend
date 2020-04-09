package fr.sortyquizz.web.rest;

import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.ReferenceXP;
import fr.sortyquizz.repository.ReferenceXPRepository;
import fr.sortyquizz.service.ReferenceXPService;
import fr.sortyquizz.service.dto.ReferenceXPDTO;
import fr.sortyquizz.service.mapper.ReferenceXPMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.sortyquizz.domain.enumeration.XPType;
/**
 * Integration tests for the {@link ReferenceXPResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReferenceXPResourceIT {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_MIN_XP = 1;
    private static final Integer UPDATED_MIN_XP = 2;

    private static final Integer DEFAULT_MAX_XP = 1;
    private static final Integer UPDATED_MAX_XP = 2;

    private static final XPType DEFAULT_XP_TYPE = XPType.PROFILE;
    private static final XPType UPDATED_XP_TYPE = XPType.THEME;

    @Autowired
    private ReferenceXPRepository referenceXPRepository;

    @Autowired
    private ReferenceXPMapper referenceXPMapper;

    @Autowired
    private ReferenceXPService referenceXPService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferenceXPMockMvc;

    private ReferenceXP referenceXP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceXP createEntity(EntityManager em) {
        ReferenceXP referenceXP = new ReferenceXP()
            .level(DEFAULT_LEVEL)
            .minXp(DEFAULT_MIN_XP)
            .maxXp(DEFAULT_MAX_XP)
            .xpType(DEFAULT_XP_TYPE);
        return referenceXP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceXP createUpdatedEntity(EntityManager em) {
        ReferenceXP referenceXP = new ReferenceXP()
            .level(UPDATED_LEVEL)
            .minXp(UPDATED_MIN_XP)
            .maxXp(UPDATED_MAX_XP)
            .xpType(UPDATED_XP_TYPE);
        return referenceXP;
    }

    @BeforeEach
    public void initTest() {
        referenceXP = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenceXP() throws Exception {
        int databaseSizeBeforeCreate = referenceXPRepository.findAll().size();

        // Create the ReferenceXP
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);
        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferenceXP in the database
        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceXP testReferenceXP = referenceXPList.get(referenceXPList.size() - 1);
        assertThat(testReferenceXP.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testReferenceXP.getMinXp()).isEqualTo(DEFAULT_MIN_XP);
        assertThat(testReferenceXP.getMaxXp()).isEqualTo(DEFAULT_MAX_XP);
        assertThat(testReferenceXP.getXpType()).isEqualTo(DEFAULT_XP_TYPE);
    }

    @Test
    @Transactional
    public void createReferenceXPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenceXPRepository.findAll().size();

        // Create the ReferenceXP with an existing ID
        referenceXP.setId(1L);
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceXP in the database
        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceXPRepository.findAll().size();
        // set the field null
        referenceXP.setLevel(null);

        // Create the ReferenceXP, which fails.
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinXpIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceXPRepository.findAll().size();
        // set the field null
        referenceXP.setMinXp(null);

        // Create the ReferenceXP, which fails.
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxXpIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceXPRepository.findAll().size();
        // set the field null
        referenceXP.setMaxXp(null);

        // Create the ReferenceXP, which fails.
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkXpTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = referenceXPRepository.findAll().size();
        // set the field null
        referenceXP.setXpType(null);

        // Create the ReferenceXP, which fails.
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        restReferenceXPMockMvc.perform(post("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReferenceXPS() throws Exception {
        // Initialize the database
        referenceXPRepository.saveAndFlush(referenceXP);

        // Get all the referenceXPList
        restReferenceXPMockMvc.perform(get("/api/reference-xps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceXP.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].minXp").value(hasItem(DEFAULT_MIN_XP)))
            .andExpect(jsonPath("$.[*].maxXp").value(hasItem(DEFAULT_MAX_XP)))
            .andExpect(jsonPath("$.[*].xpType").value(hasItem(DEFAULT_XP_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getReferenceXP() throws Exception {
        // Initialize the database
        referenceXPRepository.saveAndFlush(referenceXP);

        // Get the referenceXP
        restReferenceXPMockMvc.perform(get("/api/reference-xps/{id}", referenceXP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(referenceXP.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.minXp").value(DEFAULT_MIN_XP))
            .andExpect(jsonPath("$.maxXp").value(DEFAULT_MAX_XP))
            .andExpect(jsonPath("$.xpType").value(DEFAULT_XP_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReferenceXP() throws Exception {
        // Get the referenceXP
        restReferenceXPMockMvc.perform(get("/api/reference-xps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenceXP() throws Exception {
        // Initialize the database
        referenceXPRepository.saveAndFlush(referenceXP);

        int databaseSizeBeforeUpdate = referenceXPRepository.findAll().size();

        // Update the referenceXP
        ReferenceXP updatedReferenceXP = referenceXPRepository.findById(referenceXP.getId()).get();
        // Disconnect from session so that the updates on updatedReferenceXP are not directly saved in db
        em.detach(updatedReferenceXP);
        updatedReferenceXP
            .level(UPDATED_LEVEL)
            .minXp(UPDATED_MIN_XP)
            .maxXp(UPDATED_MAX_XP)
            .xpType(UPDATED_XP_TYPE);
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(updatedReferenceXP);

        restReferenceXPMockMvc.perform(put("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isOk());

        // Validate the ReferenceXP in the database
        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeUpdate);
        ReferenceXP testReferenceXP = referenceXPList.get(referenceXPList.size() - 1);
        assertThat(testReferenceXP.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testReferenceXP.getMinXp()).isEqualTo(UPDATED_MIN_XP);
        assertThat(testReferenceXP.getMaxXp()).isEqualTo(UPDATED_MAX_XP);
        assertThat(testReferenceXP.getXpType()).isEqualTo(UPDATED_XP_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingReferenceXP() throws Exception {
        int databaseSizeBeforeUpdate = referenceXPRepository.findAll().size();

        // Create the ReferenceXP
        ReferenceXPDTO referenceXPDTO = referenceXPMapper.toDto(referenceXP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceXPMockMvc.perform(put("/api/reference-xps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceXPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceXP in the database
        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferenceXP() throws Exception {
        // Initialize the database
        referenceXPRepository.saveAndFlush(referenceXP);

        int databaseSizeBeforeDelete = referenceXPRepository.findAll().size();

        // Delete the referenceXP
        restReferenceXPMockMvc.perform(delete("/api/reference-xps/{id}", referenceXP.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReferenceXP> referenceXPList = referenceXPRepository.findAll();
        assertThat(referenceXPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
