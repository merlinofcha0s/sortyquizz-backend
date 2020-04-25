package fr.sortyquizz.service;

import fr.sortyquizz.domain.Card;
import fr.sortyquizz.domain.UserPack;
import fr.sortyquizz.repository.CardRepository;
import fr.sortyquizz.service.dto.CardDTO;
import fr.sortyquizz.service.dto.FinishStep2DTO;
import fr.sortyquizz.service.dto.UserPackDTO;
import fr.sortyquizz.service.dto.enumeration.ResultStep;
import fr.sortyquizz.service.mapper.CardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Card}.
 */
@Service
@Transactional
public class CardService {

    private final Logger log = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    private final CardMapper cardMapper;
    private final UserPackService userPackService;

    public CardService(CardRepository cardRepository, CardMapper cardMapper, UserPackService userPackService) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.userPackService = userPackService;
    }

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save.
     * @return the persisted entity.
     */
    public CardDTO save(CardDTO cardDTO) {
        log.debug("Request to save Card : {}", cardDTO);
        Card card = cardMapper.toEntity(cardDTO);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }

    /**
     * Get all the cards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cards");
        return cardRepository.findAll(pageable)
            .map(cardMapper::toDto);
    }

    /**
     * Get one card by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CardDTO> findOne(Long id) {
        log.debug("Request to get Card : {}", id);
        return cardRepository.findById(id)
            .map(cardMapper::toDto);
    }

    /**
     * Delete the card by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Card : {}", id);
        cardRepository.deleteById(id);
    }

    /**
     * Validate the step 2 of the game
     * We validate that the card or sorted properly
     *
     * @param finishStep2DTO containing the list of the cards and the stats.
     * @return the persisted entity.
     */
    public UserPackDTO validateSort(FinishStep2DTO finishStep2DTO, String userLogin) {
        log.debug("Request to validate sorting of Cards : {}", finishStep2DTO);
        List<CardDTO> cardsSortedByUser = finishStep2DTO.getCards();
        CardDTO firstCard = cardsSortedByUser.stream().findFirst().orElseThrow();
        UserPack loadedPack = userPackService.findByPackIdAndProfileUserLogin(firstCard.getPackId(), userLogin).orElseThrow();
        loadedPack.setTimeAtSortingStep(finishStep2DTO.getPassedTime());
        UserPackDTO userPackRefreshed = userPackService.save(loadedPack);

        List<Card> cardsSortedCorrectly = loadedPack.getPack().getCards()
            .stream()
            .sorted(Comparator.comparing(Card::getOrder))
            .collect(Collectors.toList());

        int lifeleft = loadedPack.getLifeLeft();
        boolean isSortRight = true;

        for (int i = 0; i < cardsSortedByUser.size(); i++) {
            CardDTO usercard = cardsSortedByUser.get(i);
            Card correctSortCard = cardsSortedCorrectly.get(i);
            if (!usercard.getId().equals(correctSortCard.getId())) {
                isSortRight = false;
                break;
            }
        }

        if (isSortRight) {
            userPackRefreshed.setResultStep(ResultStep.SUCCEED);
        } else if (lifeleft - 1 > 0) {
            userPackRefreshed.setResultStep(ResultStep.FAIL_WITH_LIFE);
        } else {
            userPackRefreshed.setResultStep(ResultStep.FAIL_WITHOUT_LIFE);
        }

        return userPackRefreshed;
    }
}
