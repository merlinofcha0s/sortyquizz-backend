package fr.sortyquizz.web.rest;

import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.Rule;
import fr.sortyquizz.repository.RuleRepository;
import fr.sortyquizz.service.RuleService;
import fr.sortyquizz.service.dto.RuleDTO;
import fr.sortyquizz.service.mapper.RuleMapper;

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
 * Integration tests for the {@link RuleResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RuleResourceIT {

    private static final Integer DEFAULT_NB_MAX_QUESTIONS = 1;
    private static final Integer UPDATED_NB_MAX_QUESTIONS = 2;

    private static final Integer DEFAULT_TIME_PER_QUESTION = 1;
    private static final Integer UPDATED_TIME_PER_QUESTION = 2;

    private static final Integer DEFAULT_TIME_FOR_SORTING = 1;
    private static final Integer UPDATED_TIME_FOR_SORTING = 2;

    private static final Integer DEFAULT_NB_MIN_CARD_TO_WIN = 1;
    private static final Integer UPDATED_NB_MIN_CARD_TO_WIN = 2;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleMockMvc;

    private Rule rule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createEntity(EntityManager em) {
        Rule rule = new Rule()
            .nbMaxQuestions(DEFAULT_NB_MAX_QUESTIONS)
            .timePerQuestion(DEFAULT_TIME_PER_QUESTION)
            .timeForSorting(DEFAULT_TIME_FOR_SORTING)
            .nbMinCardToWin(DEFAULT_NB_MIN_CARD_TO_WIN);
        return rule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createUpdatedEntity(EntityManager em) {
        Rule rule = new Rule()
            .nbMaxQuestions(UPDATED_NB_MAX_QUESTIONS)
            .timePerQuestion(UPDATED_TIME_PER_QUESTION)
            .timeForSorting(UPDATED_TIME_FOR_SORTING)
            .nbMinCardToWin(UPDATED_NB_MIN_CARD_TO_WIN);
        return rule;
    }

    @BeforeEach
    public void initTest() {
        rule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRule() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule
        RuleDTO ruleDTO = ruleMapper.toDto(rule);
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isCreated());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate + 1);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getNbMaxQuestions()).isEqualTo(DEFAULT_NB_MAX_QUESTIONS);
        assertThat(testRule.getTimePerQuestion()).isEqualTo(DEFAULT_TIME_PER_QUESTION);
        assertThat(testRule.getTimeForSorting()).isEqualTo(DEFAULT_TIME_FOR_SORTING);
        assertThat(testRule.getNbMinCardToWin()).isEqualTo(DEFAULT_NB_MIN_CARD_TO_WIN);
    }

    @Test
    @Transactional
    public void createRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule with an existing ID
        rule.setId(1L);
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNbMaxQuestionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setNbMaxQuestions(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimePerQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setTimePerQuestion(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeForSortingIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setTimeForSorting(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNbMinCardToWinIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setNbMinCardToWin(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc.perform(post("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].nbMaxQuestions").value(hasItem(DEFAULT_NB_MAX_QUESTIONS)))
            .andExpect(jsonPath("$.[*].timePerQuestion").value(hasItem(DEFAULT_TIME_PER_QUESTION)))
            .andExpect(jsonPath("$.[*].timeForSorting").value(hasItem(DEFAULT_TIME_FOR_SORTING)))
            .andExpect(jsonPath("$.[*].nbMinCardToWin").value(hasItem(DEFAULT_NB_MIN_CARD_TO_WIN)));
    }
    
    @Test
    @Transactional
    public void getRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rule.getId().intValue()))
            .andExpect(jsonPath("$.nbMaxQuestions").value(DEFAULT_NB_MAX_QUESTIONS))
            .andExpect(jsonPath("$.timePerQuestion").value(DEFAULT_TIME_PER_QUESTION))
            .andExpect(jsonPath("$.timeForSorting").value(DEFAULT_TIME_FOR_SORTING))
            .andExpect(jsonPath("$.nbMinCardToWin").value(DEFAULT_NB_MIN_CARD_TO_WIN));
    }

    @Test
    @Transactional
    public void getNonExistingRule() throws Exception {
        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Update the rule
        Rule updatedRule = ruleRepository.findById(rule.getId()).get();
        // Disconnect from session so that the updates on updatedRule are not directly saved in db
        em.detach(updatedRule);
        updatedRule
            .nbMaxQuestions(UPDATED_NB_MAX_QUESTIONS)
            .timePerQuestion(UPDATED_TIME_PER_QUESTION)
            .timeForSorting(UPDATED_TIME_FOR_SORTING)
            .nbMinCardToWin(UPDATED_NB_MIN_CARD_TO_WIN);
        RuleDTO ruleDTO = ruleMapper.toDto(updatedRule);

        restRuleMockMvc.perform(put("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getNbMaxQuestions()).isEqualTo(UPDATED_NB_MAX_QUESTIONS);
        assertThat(testRule.getTimePerQuestion()).isEqualTo(UPDATED_TIME_PER_QUESTION);
        assertThat(testRule.getTimeForSorting()).isEqualTo(UPDATED_TIME_FOR_SORTING);
        assertThat(testRule.getNbMinCardToWin()).isEqualTo(UPDATED_NB_MIN_CARD_TO_WIN);
    }

    @Test
    @Transactional
    public void updateNonExistingRule() throws Exception {
        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Create the Rule
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleMockMvc.perform(put("/api/rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Delete the rule
        restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
