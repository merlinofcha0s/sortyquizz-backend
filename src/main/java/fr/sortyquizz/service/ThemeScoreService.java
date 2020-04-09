package fr.sortyquizz.service;

import fr.sortyquizz.domain.ThemeScore;
import fr.sortyquizz.repository.ThemeScoreRepository;
import fr.sortyquizz.service.dto.ThemeScoreDTO;
import fr.sortyquizz.service.mapper.ThemeScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThemeScore}.
 */
@Service
@Transactional
public class ThemeScoreService {

    private final Logger log = LoggerFactory.getLogger(ThemeScoreService.class);

    private final ThemeScoreRepository themeScoreRepository;

    private final ThemeScoreMapper themeScoreMapper;

    public ThemeScoreService(ThemeScoreRepository themeScoreRepository, ThemeScoreMapper themeScoreMapper) {
        this.themeScoreRepository = themeScoreRepository;
        this.themeScoreMapper = themeScoreMapper;
    }

    /**
     * Save a themeScore.
     *
     * @param themeScoreDTO the entity to save.
     * @return the persisted entity.
     */
    public ThemeScoreDTO save(ThemeScoreDTO themeScoreDTO) {
        log.debug("Request to save ThemeScore : {}", themeScoreDTO);
        ThemeScore themeScore = themeScoreMapper.toEntity(themeScoreDTO);
        themeScore = themeScoreRepository.save(themeScore);
        return themeScoreMapper.toDto(themeScore);
    }

    /**
     * Get all the themeScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ThemeScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeScores");
        return themeScoreRepository.findAll(pageable)
            .map(themeScoreMapper::toDto);
    }

    /**
     * Get one themeScore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ThemeScoreDTO> findOne(Long id) {
        log.debug("Request to get ThemeScore : {}", id);
        return themeScoreRepository.findById(id)
            .map(themeScoreMapper::toDto);
    }

    /**
     * Delete the themeScore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ThemeScore : {}", id);
        themeScoreRepository.deleteById(id);
    }
}
