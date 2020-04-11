package fr.sortyquizz.service;

import fr.sortyquizz.domain.UserPack;
import fr.sortyquizz.repository.UserPackRepository;
import fr.sortyquizz.service.dto.UserPackDTO;
import fr.sortyquizz.service.mapper.UserPackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
}
