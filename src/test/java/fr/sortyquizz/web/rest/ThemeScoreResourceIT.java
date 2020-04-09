package fr.sortyquizz.web.rest;

import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.ThemeScore;
import fr.sortyquizz.repository.ThemeScoreRepository;
import fr.sortyquizz.service.ThemeScoreService;
import fr.sortyquizz.service.dto.ThemeScoreDTO;
import fr.sortyquizz.service.mapper.ThemeScoreMapper;

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

/**
 * Integration tests for the {@link ThemeScoreResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ThemeScoreResourceIT {

    private static final Integer DEFAULT_XP = 1;
    private static final Integer UPDATED_XP = 2;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private ThemeScoreRepository themeScoreRepository;

    @Autowired
    private ThemeScoreMapper themeScoreMapper;

    @Autowired
    private ThemeScoreService themeScoreService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThemeScoreMockMvc;

    private ThemeScore themeScore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeScore createEntity(EntityManager em) {
        ThemeScore themeScore = new ThemeScore()
            .xp(DEFAULT_XP)
            .level(DEFAULT_LEVEL);
        return themeScore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThemeScore createUpdatedEntity(EntityManager em) {
        ThemeScore themeScore = new ThemeScore()
            .xp(UPDATED_XP)
            .level(UPDATED_LEVEL);
        return themeScore;
    }

    @BeforeEach
    public void initTest() {
        themeScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createThemeScore() throws Exception {
        int databaseSizeBeforeCreate = themeScoreRepository.findAll().size();

        // Create the ThemeScore
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(themeScore);
        restThemeScoreMockMvc.perform(post("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isCreated());

        // Validate the ThemeScore in the database
        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeCreate + 1);
        ThemeScore testThemeScore = themeScoreList.get(themeScoreList.size() - 1);
        assertThat(testThemeScore.getXp()).isEqualTo(DEFAULT_XP);
        assertThat(testThemeScore.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createThemeScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = themeScoreRepository.findAll().size();

        // Create the ThemeScore with an existing ID
        themeScore.setId(1L);
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(themeScore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThemeScoreMockMvc.perform(post("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeScore in the database
        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkXpIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeScoreRepository.findAll().size();
        // set the field null
        themeScore.setXp(null);

        // Create the ThemeScore, which fails.
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(themeScore);

        restThemeScoreMockMvc.perform(post("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = themeScoreRepository.findAll().size();
        // set the field null
        themeScore.setLevel(null);

        // Create the ThemeScore, which fails.
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(themeScore);

        restThemeScoreMockMvc.perform(post("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isBadRequest());

        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThemeScores() throws Exception {
        // Initialize the database
        themeScoreRepository.saveAndFlush(themeScore);

        // Get all the themeScoreList
        restThemeScoreMockMvc.perform(get("/api/theme-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(themeScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].xp").value(hasItem(DEFAULT_XP)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }
    
    @Test
    @Transactional
    public void getThemeScore() throws Exception {
        // Initialize the database
        themeScoreRepository.saveAndFlush(themeScore);

        // Get the themeScore
        restThemeScoreMockMvc.perform(get("/api/theme-scores/{id}", themeScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(themeScore.getId().intValue()))
            .andExpect(jsonPath("$.xp").value(DEFAULT_XP))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingThemeScore() throws Exception {
        // Get the themeScore
        restThemeScoreMockMvc.perform(get("/api/theme-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThemeScore() throws Exception {
        // Initialize the database
        themeScoreRepository.saveAndFlush(themeScore);

        int databaseSizeBeforeUpdate = themeScoreRepository.findAll().size();

        // Update the themeScore
        ThemeScore updatedThemeScore = themeScoreRepository.findById(themeScore.getId()).get();
        // Disconnect from session so that the updates on updatedThemeScore are not directly saved in db
        em.detach(updatedThemeScore);
        updatedThemeScore
            .xp(UPDATED_XP)
            .level(UPDATED_LEVEL);
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(updatedThemeScore);

        restThemeScoreMockMvc.perform(put("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isOk());

        // Validate the ThemeScore in the database
        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeUpdate);
        ThemeScore testThemeScore = themeScoreList.get(themeScoreList.size() - 1);
        assertThat(testThemeScore.getXp()).isEqualTo(UPDATED_XP);
        assertThat(testThemeScore.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingThemeScore() throws Exception {
        int databaseSizeBeforeUpdate = themeScoreRepository.findAll().size();

        // Create the ThemeScore
        ThemeScoreDTO themeScoreDTO = themeScoreMapper.toDto(themeScore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThemeScoreMockMvc.perform(put("/api/theme-scores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(themeScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ThemeScore in the database
        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThemeScore() throws Exception {
        // Initialize the database
        themeScoreRepository.saveAndFlush(themeScore);

        int databaseSizeBeforeDelete = themeScoreRepository.findAll().size();

        // Delete the themeScore
        restThemeScoreMockMvc.perform(delete("/api/theme-scores/{id}", themeScore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThemeScore> themeScoreList = themeScoreRepository.findAll();
        assertThat(themeScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
