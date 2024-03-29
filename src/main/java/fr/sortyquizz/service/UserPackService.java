package fr.sortyquizz.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import fr.sortyquizz.domain.UserPack;
import fr.sortyquizz.repository.UserPackRepository;
import fr.sortyquizz.service.dto.QuestionDTO;
import fr.sortyquizz.service.dto.UserPackDTO;
import fr.sortyquizz.service.dto.enumeration.ResultStep;
import fr.sortyquizz.service.mapper.UserPackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserPack}.
 */
@Service
@Transactional
public class UserPackService {

    private final Logger log = LoggerFactory.getLogger(UserPackService.class);

    private final UserPackRepository userPackRepository;

    private final UserPackMapper userPackMapper;

    public UserPackService(UserPackRepository userPackRepository, UserPackMapper userPackMapper) {
        this.userPackRepository = userPackRepository;
        this.userPackMapper = userPackMapper;
    }

    /**
     * Save a userPack.
     *
     * @param userPackDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPackDTO save(UserPackDTO userPackDTO) {
        log.debug("Request to save UserPack : {}", userPackDTO);
        UserPack userPack = userPackMapper.toEntity(userPackDTO);
        userPack = userPackRepository.save(userPack);
        return userPackMapper.toDto(userPack);
    }

    /**
     * Get all the userPacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPacks");
        return userPackRepository.findAll(pageable)
            .map(userPackMapper::toDto);
    }

    /**
     * Get one userPack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserPackDTO> findOne(Long id) {
        log.debug("Request to get UserPack : {}", id);
        return userPackRepository.findById(id)
            .map(userPackMapper::toDto);
    }

    /**
     * Delete the userPack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPack : {}", id);
        userPackRepository.deleteById(id);
    }

    /**
     * Count all the userpack for the connected user.
     *
     * @return the number.
     */
    @Transactional(readOnly = true)
    public int countByUserLogin(String userLogin) {
        log.debug("Request to count UserPack for the connected user : {}", userLogin);
        return userPackRepository.countAllByProfileUserLogin(userLogin);
    }

    /**
     * Get all the userpack for the connected user.
     *
     * @return the number.
     */
    @Transactional(readOnly = true)
    public List<UserPackDTO> getByUserLogin(String userLogin) {
        log.debug("Request to get UserPack for the connected user : {}", userLogin);
        return userPackRepository.findAllByProfileUserLogin(userLogin)
            .stream()
            .map(userPackMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get userpack by id and for the connected user.
     *
     * @param id        the id of the entity.
     * @param userLogin the login of the connected user.
     * @return the userpack if one.
     */
    @Transactional(readOnly = true)
    public Optional<UserPack> getByIdAndUserLogin(Long id, String userLogin) {
        log.debug("Request to get UserPack for the connected user : {}", userLogin);
        return userPackRepository.findByIdAndProfileUserLogin(id, userLogin);
    }

    /**
     * Get userpack by id and for the connected user.
     *
     * @param id        the id of the entity.
     * @param userLogin the login of the connected user.
     * @return the userpack if one.
     */
    @Transactional(readOnly = true)
    public Optional<UserPackDTO> getByIdAndUserLoginDTO(Long id, String userLogin) {
        log.debug("Request to get UserPack for the connected user : {}", userLogin);
        Optional<UserPackDTO> userPackDTO = userPackRepository.findByIdAndProfileUserLogin(id, userLogin).map(userPackMapper::toDto);
        if (userPackDTO.isPresent()) {
            Set<QuestionDTO> questions = ImmutableSet.copyOf(Iterables.limit(userPackDTO.get().getPack().getQuestions(),
                userPackDTO.get().getPack().getRule().getNbMaxQuestions()));
            userPackDTO.get().getPack().setQuestions(questions);
        }
        return userPackDTO;
    }

    /**
     * Get all the userpack for the connected user.
     *
     * @return the number.
     */
    @Transactional
    public Optional<UserPackDTO> completePackForStep1(UserPackDTO userPackDTO, String userLogin) {
        log.debug("Request to get UserPack for the connected user : {}", userLogin);
        Optional<UserPack> packToComplete = userPackRepository.findByPackIdAndProfileUserLogin(userPackDTO.getPackId(), userLogin);
        if (packToComplete.isPresent()) {
            if (userPackDTO.getResultStep() == ResultStep.FAIL_WITH_LIFE) {
                packToComplete.get().setLifeLeft(packToComplete.get().getLifeLeft() - 1);
            } else if (userPackDTO.getResultStep() == ResultStep.SUCCEED) {
                packToComplete.get().setNbQuestionsToSucceed(userPackDTO.getNbQuestionsToSucceed());
                packToComplete.get().setTimeAtQuizzStep(userPackDTO.getTimeAtQuizzStep());
            } else {
                packToComplete.get().setLifeLeft(0);
            }

            UserPack save = userPackRepository.save(packToComplete.get());
            UserPackDTO userPackDTOUpdated = userPackMapper.toDto(save);
            userPackDTOUpdated.setResultStep(userPackDTO.getResultStep());
            return Optional.of(userPackDTOUpdated);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Service when user loose step 1.
     *
     * @param userPackId id of the user pack
     * @param userLogin  login of the connected user
     * @return the number.
     */
    @Transactional
    public void looseStep1(Integer userPackId, String userLogin) {
        log.debug("Request to loose userpack by userpack id and userlogin : {}", userLogin);
        Optional<UserPack> userPackByIdAndLogin = userPackRepository.findByIdAndProfileUserLogin(userPackId.longValue(), userLogin);
        if (userPackByIdAndLogin.isPresent()) {
            userPackByIdAndLogin.get().setLifeLeft(userPackByIdAndLogin.get().getLifeLeft() - 1);
            userPackRepository.save(userPackByIdAndLogin.get());
        }
    }

    /**
     * Service when user loose step 1.
     *
     * @param userPackId id of the user pack
     * @param userLogin  login of the connected user
     * @return the number.
     */
    @Transactional
    public void abortStep1(Integer userPackId, String userLogin) {
        log.debug("Request to loose userpack by userpack id and userlogin : {}", userLogin);
        Optional<UserPack> userPackByIdAndLogin = userPackRepository.findByIdAndProfileUserLogin(userPackId.longValue(), userLogin);
        if (userPackByIdAndLogin.isPresent()) {
            userPackByIdAndLogin.get().setLifeLeft(0);
            userPackRepository.save(userPackByIdAndLogin.get());
        }
    }

    public Optional<UserPack> findByPackIdAndProfileUserLogin(Long userPackId, String userLogin) {
        return userPackRepository.findByPackIdAndProfileUserLogin(userPackId, userLogin);
    }

    public UserPackDTO save(UserPack userPack) {
        UserPack userPackRefreshed = userPackRepository.save(userPack);
        return userPackMapper.toDto(userPackRefreshed);
    }
}
