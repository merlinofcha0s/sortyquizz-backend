package fr.sortyquizz.web.rest;

import fr.sortyquizz.DBClearer;
import fr.sortyquizz.SortyquizzApp;
import fr.sortyquizz.domain.*;
import fr.sortyquizz.domain.enumeration.SortingType;
import fr.sortyquizz.domain.enumeration.ValueType;
import fr.sortyquizz.repository.CardRepository;
import fr.sortyquizz.service.CardService;
import fr.sortyquizz.service.dto.CardDTO;
import fr.sortyquizz.service.dto.FinishStep2DTO;
import fr.sortyquizz.service.dto.enumeration.ResultStep;
import fr.sortyquizz.service.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CardResource} REST controller.
 */
@SpringBootTest(classes = SortyquizzApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CardResourceIT {

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TO_SORT = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TO_SORT = "BBBBBBBBBB";

    private static final ValueType DEFAULT_VALUE_TYPE = ValueType.DATE;
    private static final ValueType UPDATED_VALUE_TYPE = ValueType.NUMBER;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final SortingType DEFAULT_SORTING_TYPE = SortingType.NATURAL;
    private static final SortingType UPDATED_SORTING_TYPE = SortingType.MANUAL;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardMockMvc;

    private Card card;

    @Autowired
    private DBClearer dbClearer;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createEntity(EntityManager em) {
        Card card = new Card()
            .display(DEFAULT_DISPLAY)
            .valueToSort(DEFAULT_VALUE_TO_SORT)
            .valueType(DEFAULT_VALUE_TYPE)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .sortingType(DEFAULT_SORTING_TYPE)
            .order(DEFAULT_ORDER);
        return card;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createUpdatedEntity(EntityManager em) {
        Card card = new Card()
            .display(UPDATED_DISPLAY)
            .valueToSort(UPDATED_VALUE_TO_SORT)
            .valueType(UPDATED_VALUE_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .sortingType(UPDATED_SORTING_TYPE)
            .order(UPDATED_ORDER);
        return card;
    }

    @BeforeEach
    public void initTest() {
        card = createEntity(em);
        dbClearer.clearAllTables();
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);
        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testCard.getValueToSort()).isEqualTo(DEFAULT_VALUE_TO_SORT);
        assertThat(testCard.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCard.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testCard.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testCard.getSortingType()).isEqualTo(DEFAULT_SORTING_TYPE);
        assertThat(testCard.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card with an existing ID
        card.setId(1L);
        CardDTO cardDTO = cardMapper.toDto(card);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDisplayIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setDisplay(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueToSortIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setValueToSort(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setValueType(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setSortingType(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc.perform(post("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY)))
            .andExpect(jsonPath("$.[*].valueToSort").value(hasItem(DEFAULT_VALUE_TO_SORT)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].sortingType").value(hasItem(DEFAULT_SORTING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }

    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY))
            .andExpect(jsonPath("$.valueToSort").value(DEFAULT_VALUE_TO_SORT))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.sortingType").value(DEFAULT_SORTING_TYPE.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).get();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .display(UPDATED_DISPLAY)
            .valueToSort(UPDATED_VALUE_TO_SORT)
            .valueType(UPDATED_VALUE_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .sortingType(UPDATED_SORTING_TYPE)
            .order(UPDATED_ORDER);
        CardDTO cardDTO = cardMapper.toDto(updatedCard);

        restCardMockMvc.perform(put("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cardList.get(cardList.size() - 1);
        assertThat(testCard.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testCard.getValueToSort()).isEqualTo(UPDATED_VALUE_TO_SORT);
        assertThat(testCard.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCard.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testCard.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testCard.getSortingType()).isEqualTo(UPDATED_SORTING_TYPE);
        assertThat(testCard.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCard() throws Exception {
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc.perform(put("/api/cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Delete the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(value = "sorter-user")
    public void validateSortShouldWorkFailWithLife() throws Exception {
        List<CardDTO> cardDTOS = initBasicData(2, 3, 1, 2);
        FinishStep2DTO finishStep2DTO = new FinishStep2DTO();
        finishStep2DTO.setCards(cardDTOS);
        finishStep2DTO.setNbCards(cardDTOS.size());
        finishStep2DTO.setPassedTime(20);

        restCardMockMvc.perform(post("/api/cards/validate-sort")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finishStep2DTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.resultStep").value(ResultStep.FAIL_WITH_LIFE.toString()));
    }

    @Test
    @Transactional
    @WithMockUser(value = "sorter-user")
    public void validateSortShouldWorkSucessSort() throws Exception {
        List<CardDTO> cardDTOS = initBasicData(2, 1, 2, 3);
        FinishStep2DTO finishStep2DTO = new FinishStep2DTO();
        finishStep2DTO.setCards(cardDTOS);
        finishStep2DTO.setNbCards(cardDTOS.size());
        finishStep2DTO.setPassedTime(20);

        restCardMockMvc.perform(post("/api/cards/validate-sort")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finishStep2DTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.resultStep").value(ResultStep.SUCCEED.toString()));
    }


    @Test
    @Transactional
    @WithMockUser(value = "sorter-user")
    public void validateSortShouldWorkFailWithoutLife() throws Exception {
        List<CardDTO> cardDTOS = initBasicData(1, 3, 2, 1);
        FinishStep2DTO finishStep2DTO = new FinishStep2DTO();
        finishStep2DTO.setCards(cardDTOS);
        finishStep2DTO.setNbCards(cardDTOS.size());
        finishStep2DTO.setPassedTime(20);

        restCardMockMvc.perform(post("/api/cards/validate-sort")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(finishStep2DTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.resultStep").value(ResultStep.FAIL_WITHOUT_LIFE.toString()));
    }

    public List<CardDTO> initBasicData(int lifeLeft, int orderCard1, int orderCard2, int orderCard3) {
        User newUser = UserResourceIT.createEntity(em);
        newUser.setLogin("sorter-user");
        em.persist(newUser);

        Theme newTheme = ThemeResourceIT.createEntity(em);
        em.persist(newTheme);

        Pack newPack = PackResourceIT.createEntity(em);
        newPack.setTheme(newTheme);
        em.persist(newPack);

        Profile newProfile = ProfileResourceIT.createEntity(em);
        newProfile.setUser(newUser);

        em.persist(newProfile);

        UserPack userPack = UserPackResourceIT.createEntity(em);

        userPack.setPack(newPack);
        userPack.setLifeLeft(lifeLeft);
        newProfile.addUserPack(userPack);
        userPack.setProfile(newProfile);

        em.persist(newProfile);
        em.persist(userPack);

        Card card1 = createEntity(em);
        card1.setOrder(orderCard1);
        Card card2 = createEntity(em);
        card2.setOrder(orderCard2);
        Card card3 = createEntity(em);
        card3.setOrder(orderCard3);

        card1.setPack(newPack);
        card2.setPack(newPack);
        card3.setPack(newPack);
        newPack.addCard(card1);
        newPack.addCard(card2);
        newPack.addCard(card3);

        em.persist(card1);
        em.persist(card2);
        em.persist(card3);
        em.persist(newPack);

        return Stream.of(card1, card2, card3).map(cardMapper::toDto).collect(Collectors.toList());
    }
}
