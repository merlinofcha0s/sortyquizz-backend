package fr.sortyquizz.web.rest;

import fr.sortyquizz.DBClearer;
import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.*;
import fr.sortyquizz.domain.enumeration.PackType;
import fr.sortyquizz.domain.enumeration.QuestionType;
import fr.sortyquizz.repository.PackRepository;
import fr.sortyquizz.service.PackService;
import fr.sortyquizz.service.dto.PackDTO;
import fr.sortyquizz.service.mapper.PackMapper;
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
 * Integration tests for the {@link PackResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PackResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final PackType DEFAULT_TYPE = PackType.FREE;
    private static final PackType UPDATED_TYPE = PackType.PREMIUM;

    private static final Integer DEFAULT_LIFE = 1;
    private static final Integer UPDATED_LIFE = 2;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private PackMapper packMapper;

    @Autowired
    private PackService packService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackMockMvc;

    private Pack pack;

    @Autowired
    private DBClearer dbClearer;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pack createEntity(EntityManager em) {
        Pack pack = new Pack()
            .name(DEFAULT_NAME)
            .level(DEFAULT_LEVEL)
            .type(DEFAULT_TYPE)
            .life(DEFAULT_LIFE);
        return pack;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pack createUpdatedEntity(EntityManager em) {
        Pack pack = new Pack()
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .type(UPDATED_TYPE)
            .life(UPDATED_LIFE);
        return pack;
    }

    @BeforeEach
    public void initTest() {
        pack = createEntity(em);
        dbClearer.clearAllTables();
    }

    @Test
    @Transactional
    public void createPack() throws Exception {
        int databaseSizeBeforeCreate = packRepository.findAll().size();

        // Create the Pack
        PackDTO packDTO = packMapper.toDto(pack);
        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isCreated());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate + 1);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPack.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testPack.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPack.getLife()).isEqualTo(DEFAULT_LIFE);
    }

    @Test
    @Transactional
    public void createPackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packRepository.findAll().size();

        // Create the Pack with an existing ID
        pack.setId(1L);
        PackDTO packDTO = packMapper.toDto(pack);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packRepository.findAll().size();
        // set the field null
        pack.setName(null);

        // Create the Pack, which fails.
        PackDTO packDTO = packMapper.toDto(pack);

        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = packRepository.findAll().size();
        // set the field null
        pack.setLevel(null);

        // Create the Pack, which fails.
        PackDTO packDTO = packMapper.toDto(pack);

        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = packRepository.findAll().size();
        // set the field null
        pack.setType(null);

        // Create the Pack, which fails.
        PackDTO packDTO = packMapper.toDto(pack);

        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLifeIsRequired() throws Exception {
        int databaseSizeBeforeTest = packRepository.findAll().size();
        // set the field null
        pack.setLife(null);

        // Create the Pack, which fails.
        PackDTO packDTO = packMapper.toDto(pack);

        restPackMockMvc.perform(post("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPacks() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get all the packList
        restPackMockMvc.perform(get("/api/packs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pack.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].life").value(hasItem(DEFAULT_LIFE)));
    }

    @Test
    @Transactional
    public void getPack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get the pack
        restPackMockMvc.perform(get("/api/packs/{id}", pack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.life").value(DEFAULT_LIFE));
    }

    @Test
    @Transactional
    public void getNonExistingPack() throws Exception {
        // Get the pack
        restPackMockMvc.perform(get("/api/packs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Update the pack
        Pack updatedPack = packRepository.findById(pack.getId()).get();
        // Disconnect from session so that the updates on updatedPack are not directly saved in db
        em.detach(updatedPack);
        updatedPack
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .type(UPDATED_TYPE)
            .life(UPDATED_LIFE);
        PackDTO packDTO = packMapper.toDto(updatedPack);

        restPackMockMvc.perform(put("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isOk());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPack.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testPack.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPack.getLife()).isEqualTo(UPDATED_LIFE);
    }

    @Test
    @Transactional
    public void updateNonExistingPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Create the Pack
        PackDTO packDTO = packMapper.toDto(pack);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackMockMvc.perform(put("/api/packs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(packDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeDelete = packRepository.findAll().size();

        // Delete the pack
        restPackMockMvc.perform(delete("/api/packs/{id}", pack.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(value = "counted-user", roles = "USER")
    public void getPackByUserPackIdAndLoginShouldWork() throws Exception {
        // Initialize the database
        UserPack userPack = UserPackResourceIT.createEntity(em);
        User newUser = UserResourceIT.createEntity(em);
        newUser.setLogin("counted-user");
        em.persist(newUser);

        Theme newTheme = ThemeResourceIT.createEntity(em);
        em.persist(newTheme);

        pack.setTheme(newTheme);
        em.persist(pack);

        Rule rule = new Rule();
        rule.setNbMaxQuestions(1);
        rule.setNbMinCardToWin(1);
        rule.setTimeForSorting(1);
        rule.setTimePerQuestion(1);

        em.persist(rule);

        Question question = new Question();
        question.setPack(pack);
        question.setType(QuestionType.SIMPLE);
        question.setQuestion("Looool lool ?");
        em.persist(question);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setIsTheRightAnswer(true);
        answer.setAnswer("looooooooooooooooooool");
        em.persist(answer);

        question.addAnswer(answer);
        em.persist(question);

        Profile newProfile = ProfileResourceIT.createEntity(em);
        newProfile.setUser(newUser);

        em.persist(newProfile);
        userPack.setPack(pack);
        newProfile.addUserPack(userPack);
        userPack.setProfile(newProfile);

        em.persist(userPack);

        pack.addQuestion(question);
        pack.setRule(rule);

        em.persist(pack);

        // Get all the packList
        restPackMockMvc.perform(get("/api/packs/get-by-userpackid-and-user/{userPackId}", userPack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.life").value(DEFAULT_LIFE))
            .andExpect(jsonPath("$.rule.id").value(rule.getId()))
            .andExpect(jsonPath("$.questions.[*].id").value(hasItem(question.getId().intValue())));
    }

    @Test
    @Transactional
    @WithMockUser(value = "counted-user-fail", roles = "USER")
    public void getPackByUserPackIdAndLoginShouldFailNotTheRightUserConnected() throws Exception {
        // Initialize the database
        UserPack userPack = UserPackResourceIT.createEntity(em);
        User newUser = UserResourceIT.createEntity(em);
        newUser.setLogin("counted-user");
        em.persist(newUser);

        Theme newTheme = ThemeResourceIT.createEntity(em);
        em.persist(newTheme);

        pack.setTheme(newTheme);
        em.persist(pack);

        Rule rule = new Rule();
        rule.setNbMaxQuestions(1);
        rule.setNbMinCardToWin(1);
        rule.setTimeForSorting(1);
        rule.setTimePerQuestion(1);

        em.persist(rule);

        Question question = new Question();
        question.setPack(pack);
        question.setType(QuestionType.SIMPLE);
        question.setQuestion("Looool lool ?");
        em.persist(question);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setIsTheRightAnswer(true);
        answer.setAnswer("looooooooooooooooooool");
        em.persist(answer);

        question.addAnswer(answer);
        em.persist(question);

        Profile newProfile = ProfileResourceIT.createEntity(em);
        newProfile.setUser(newUser);

        em.persist(newProfile);
        userPack.setPack(pack);
        newProfile.addUserPack(userPack);
        userPack.setProfile(newProfile);

        em.persist(userPack);

        pack.addQuestion(question);
        pack.setRule(rule);

        em.persist(pack);

        // Get all the packList
        restPackMockMvc.perform(get("/api/packs/get-by-userpackid-and-user/{userPackId}", userPack.getId()))
            .andExpect(status().isNotFound());
    }
}
