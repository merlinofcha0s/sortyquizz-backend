package fr.sortyquizz.service;

import fr.sortyquizz.domain.Theme;
import fr.sortyquizz.repository.ThemeRepository;
import fr.sortyquizz.service.dto.ThemeDTO;
import fr.sortyquizz.service.mapper.ThemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Theme}.
 */
@Service
@Transactional
public class ThemeService {

    private final Logger log = LoggerFactory.getLogger(ThemeService.class);

    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    public ThemeService(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    /**
     * Save a theme.
     *
     * @param themeDTO the entity to save.
     * @return the persisted entity.
     */
    public ThemeDTO save(ThemeDTO themeDTO) {
        log.debug("Request to save Theme : {}", themeDTO);
        Theme theme = themeMapper.toEntity(themeDTO);
        theme = themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }

    /**
     * Save a theme entity.
     *
     * @param theme the entity to save.
     * @return the persisted entity.
     */
    public Theme save(Theme theme) {
        log.debug("Request to save Theme : {}", theme);
        return themeRepository.save(theme);
    }

    /**
     * Get all the themes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ThemeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Themes");
        return themeRepository.findAll(pageable)
            .map(themeMapper::toDto);
    }

    /**
     * Get one theme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ThemeDTO> findOne(Long id) {
        log.debug("Request to get Theme : {}", id);
        return themeRepository.findById(id)
            .map(themeMapper::toDto);
    }

    /**
     * Delete the theme by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Theme : {}", id);
        themeRepository.deleteById(id);
    }
}
