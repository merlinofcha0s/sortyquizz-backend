package fr.sortyquizz.web.rest;

import fr.sortyquizz.DBClearer;
import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.*;
import fr.sortyquizz.domain.enumeration.PackState;
import fr.sortyquizz.repository.UserPackRepository;
import fr.sortyquizz.service.UserPackService;
import fr.sortyquizz.service.dto.UserPackDTO;
import fr.sortyquizz.service.mapper.UserPackMapper;
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
 * Integration tests for the {@link UserPackResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserPackResourceIT {

    private static final PackState DEFAULT_STATE = PackState.OPEN;
    private static final PackState UPDATED_STATE = PackState.COMPLETED;

    private static final Integer DEFAULT_LIFE_LEFT = 1;
    private static final Integer UPDATED_LIFE_LEFT = 2;

    private static final Integer DEFAULT_NB_QUESTIONS_TO_SUCCEED = 1;
    private static final Integer UPDATED_NB_QUESTIONS_TO_SUCCEED = 2;

    private static final Integer DEFAULT_TIME_AT_QUIZZ_STEP = 1;
    private static final Integer UPDATED_TIME_AT_QUIZZ_STEP = 2;

    private static final Integer DEFAULT_TIME_AT_SORTING_STEP = 1;
    private static final Integer UPDATED_TIME_AT_SORTING_STEP = 2;

    @Autowired
    private UserPackRepository userPackRepository;

    @Autowired
    private UserPackMapper userPackMapper;

    @Autowired
    private UserPackService userPackService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPackMockMvc;

    private UserPack userPack;

    @Autowired
    private DBClearer dbClearer;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPack createEntity(EntityManager em) {
        UserPack userPack = new UserPack()
            .state(DEFAULT_STATE)
            .lifeLeft(DEFAULT_LIFE_LEFT)
            .nbQuestionsToSucceed(DEFAULT_NB_QUESTIONS_TO_SUCCEED)
            .timeAtQuizzStep(DEFAULT_TIME_AT_QUIZZ_STEP)
            .timeAtSortingStep(DEFAULT_TIME_AT_SORTING_STEP);
        return userPack;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPack createUpdatedEntity(EntityManager em) {
        UserPack userPack = new UserPack()
            .state(UPDATED_STATE)
            .lifeLeft(UPDATED_LIFE_LEFT)
            .nbQuestionsToSucceed(UPDATED_NB_QUESTIONS_TO_SUCCEED)
            .timeAtQuizzStep(UPDATED_TIME_AT_QUIZZ_STEP)
            .timeAtSortingStep(UPDATED_TIME_AT_SORTING_STEP);
        return userPack;
    }

    @BeforeEach
    public void initTest() {
        userPack = createEntity(em);
        dbClearer.clearAllTables();
    }

    @Test
    @Transactional
    public void createUserPack() throws Exception {
        int databaseSizeBeforeCreate = userPackRepository.findAll().size();

        // Create the UserPack
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);
        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPack in the database
        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeCreate + 1);
        UserPack testUserPack = userPackList.get(userPackList.size() - 1);
        assertThat(testUserPack.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserPack.getLifeLeft()).isEqualTo(DEFAULT_LIFE_LEFT);
        assertThat(testUserPack.getNbQuestionsToSucceed()).isEqualTo(DEFAULT_NB_QUESTIONS_TO_SUCCEED);
        assertThat(testUserPack.getTimeAtQuizzStep()).isEqualTo(DEFAULT_TIME_AT_QUIZZ_STEP);
        assertThat(testUserPack.getTimeAtSortingStep()).isEqualTo(DEFAULT_TIME_AT_SORTING_STEP);
    }

    @Test
    @Transactional
    public void createUserPackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPackRepository.findAll().size();

        // Create the UserPack with an existing ID
        userPack.setId(1L);
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPack in the database
        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPackRepository.findAll().size();
        // set the field null
        userPack.setState(null);

        // Create the UserPack, which fails.
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLifeLeftIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPackRepository.findAll().size();
        // set the field null
        userPack.setLifeLeft(null);

        // Create the UserPack, which fails.
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbQuestionsToSucceedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPackRepository.findAll().size();
        // set the field null
        userPack.setNbQuestionsToSucceed(null);

        // Create the UserPack, which fails.
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeAtQuizzStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPackRepository.findAll().size();
        // set the field null
        userPack.setTimeAtQuizzStep(null);

        // Create the UserPack, which fails.
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeAtSortingStepIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPackRepository.findAll().size();
        // set the field null
        userPack.setTimeAtSortingStep(null);

        // Create the UserPack, which fails.
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        restUserPackMockMvc.perform(post("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPacks() throws Exception {
        // Initialize the database
        userPackRepository.saveAndFlush(userPack);

        // Get all the userPackList
        restUserPackMockMvc.perform(get("/api/user-packs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPack.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].lifeLeft").value(hasItem(DEFAULT_LIFE_LEFT)))
            .andExpect(jsonPath("$.[*].nbQuestionsToSucceed").value(hasItem(DEFAULT_NB_QUESTIONS_TO_SUCCEED)))
            .andExpect(jsonPath("$.[*].timeAtQuizzStep").value(hasItem(DEFAULT_TIME_AT_QUIZZ_STEP)))
            .andExpect(jsonPath("$.[*].timeAtSortingStep").value(hasItem(DEFAULT_TIME_AT_SORTING_STEP)));
    }

    @Test
    @Transactional
    public void getUserPack() throws Exception {
        // Initialize the database
        userPackRepository.saveAndFlush(userPack);

        // Get the userPack
        restUserPackMockMvc.perform(get("/api/user-packs/{id}", userPack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPack.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.lifeLeft").value(DEFAULT_LIFE_LEFT))
            .andExpect(jsonPath("$.nbQuestionsToSucceed").value(DEFAULT_NB_QUESTIONS_TO_SUCCEED))
            .andExpect(jsonPath("$.timeAtQuizzStep").value(DEFAULT_TIME_AT_QUIZZ_STEP))
            .andExpect(jsonPath("$.timeAtSortingStep").value(DEFAULT_TIME_AT_SORTING_STEP));
    }

    @Test
    @Transactional
    public void getNonExistingUserPack() throws Exception {
        // Get the userPack
        restUserPackMockMvc.perform(get("/api/user-packs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPack() throws Exception {
        // Initialize the database
        userPackRepository.saveAndFlush(userPack);

        int databaseSizeBeforeUpdate = userPackRepository.findAll().size();

        // Update the userPack
        UserPack updatedUserPack = userPackRepository.findById(userPack.getId()).get();
        // Disconnect from session so that the updates on updatedUserPack are not directly saved in db
        em.detach(updatedUserPack);
        updatedUserPack
            .state(UPDATED_STATE)
            .lifeLeft(UPDATED_LIFE_LEFT)
            .nbQuestionsToSucceed(UPDATED_NB_QUESTIONS_TO_SUCCEED)
            .timeAtQuizzStep(UPDATED_TIME_AT_QUIZZ_STEP)
            .timeAtSortingStep(UPDATED_TIME_AT_SORTING_STEP);
        UserPackDTO userPackDTO = userPackMapper.toDto(updatedUserPack);

        restUserPackMockMvc.perform(put("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isOk());

        // Validate the UserPack in the database
        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeUpdate);
        UserPack testUserPack = userPackList.get(userPackList.size() - 1);
        assertThat(testUserPack.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserPack.getLifeLeft()).isEqualTo(UPDATED_LIFE_LEFT);
        assertThat(testUserPack.getNbQuestionsToSucceed()).isEqualTo(UPDATED_NB_QUESTIONS_TO_SUCCEED);
        assertThat(testUserPack.getTimeAtQuizzStep()).isEqualTo(UPDATED_TIME_AT_QUIZZ_STEP);
        assertThat(testUserPack.getTimeAtSortingStep()).isEqualTo(UPDATED_TIME_AT_SORTING_STEP);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPack() throws Exception {
        int databaseSizeBeforeUpdate = userPackRepository.findAll().size();

        // Create the UserPack
        UserPackDTO userPackDTO = userPackMapper.toDto(userPack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPackMockMvc.perform(put("/api/user-packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userPackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPack in the database
        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserPack() throws Exception {
        // Initialize the database
        userPackRepository.saveAndFlush(userPack);

        int databaseSizeBeforeDelete = userPackRepository.findAll().size();

        // Delete the userPack
        restUserPackMockMvc.perform(delete("/api/user-packs/{id}", userPack.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPack> userPackList = userPackRepository.findAll();
        assertThat(userPackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(value = "counted-user", roles = "USER")
    public void getCountUserPackByConnectedUser() throws Exception {
        // Initialize the database
        initBasicData(true);

        // Get the userPack
        restUserPackMockMvc.perform(get("/api/user-packs/get-count-number-by-user"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("2"));
    }


    @Test
    @Transactional
    @WithMockUser(value = "counted-user", roles = "USER")
    public void getUserPackByConnectedUserShouldReturnUserPack() throws Exception {
        // Initialize the database
        initBasicData(true);

        // Get the userPack
        restUserPackMockMvc.perform(get("/api/user-packs/get-by-user"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPack.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].lifeLeft").value(hasItem(DEFAULT_LIFE_LEFT)))
            .andExpect(jsonPath("$.[*].nbQuestionsToSucceed").value(hasItem(DEFAULT_NB_QUESTIONS_TO_SUCCEED)))
            .andExpect(jsonPath("$.[*].timeAtQuizzStep").value(hasItem(DEFAULT_TIME_AT_QUIZZ_STEP)))
            .andExpect(jsonPath("$.[*].timeAtSortingStep").value(hasItem(DEFAULT_TIME_AT_SORTING_STEP)));
    }

    @Test
    @Transactional
    @WithMockUser(value = "counted-user", roles = "USER")
    public void getUserPackByConnectedUserShouldReturnZeroUserPack() throws Exception {
        // Initialize the database
        initBasicData(false);

        // Get the userPack
        restUserPackMockMvc.perform(get("/api/user-packs/get-by-user"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("[]"));
    }

    public void initBasicData(boolean withUserPack) {
        User newUser = UserResourceIT.createEntity(em);
        newUser.setLogin("counted-user");
        em.persist(newUser);

        Theme newTheme = ThemeResourceIT.createEntity(em);
        em.persist(newTheme);

        Pack newPack = PackResourceIT.createEntity(em);
        newPack.setTheme(newTheme);
        em.persist(newPack);

        Profile newProfile = ProfileResourceIT.createEntity(em);
        newProfile.setUser(newUser);

        em.persist(newProfile);

        if (withUserPack) {
            userPack.setPack(newPack);
            newProfile.addUserPack(userPack);
            userPack.setProfile(newProfile);
        }
        em.persist(newProfile);

        if (withUserPack) {
            em.persist(userPack);
            UserPack secondUserPack = UserPackResourceIT.createEntity(em);
            secondUserPack.setPack(newPack);
            secondUserPack.setProfile(newProfile);

            em.persist(secondUserPack);
        }
    }
}
