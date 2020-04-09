package fr.sortyquizz.service;

import fr.sortyquizz.domain.ReferenceXP;
import fr.sortyquizz.repository.ReferenceXPRepository;
import fr.sortyquizz.service.dto.ReferenceXPDTO;
import fr.sortyquizz.service.mapper.ReferenceXPMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReferenceXP}.
 */
@Service
@Transactional
public class ReferenceXPService {

    private final Logger log = LoggerFactory.getLogger(ReferenceXPService.class);

    private final ReferenceXPRepository referenceXPRepository;

    private final ReferenceXPMapper referenceXPMapper;

    public ReferenceXPService(ReferenceXPRepository referenceXPRepository, ReferenceXPMapper referenceXPMapper) {
        this.referenceXPRepository = referenceXPRepository;
        this.referenceXPMapper = referenceXPMapper;
    }

    /**
     * Save a referenceXP.
     *
     * @param referenceXPDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferenceXPDTO save(ReferenceXPDTO referenceXPDTO) {
        log.debug("Request to save ReferenceXP : {}", referenceXPDTO);
        ReferenceXP referenceXP = referenceXPMapper.toEntity(referenceXPDTO);
        referenceXP = referenceXPRepository.save(referenceXP);
        return referenceXPMapper.toDto(referenceXP);
    }

    /**
     * Get all the referenceXPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenceXPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferenceXPS");
        return referenceXPRepository.findAll(pageable)
            .map(referenceXPMapper::toDto);
    }

    /**
     * Get one referenceXP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReferenceXPDTO> findOne(Long id) {
        log.debug("Request to get ReferenceXP : {}", id);
        return referenceXPRepository.findById(id)
            .map(referenceXPMapper::toDto);
    }

    /**
     * Delete the referenceXP by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferenceXP : {}", id);
        referenceXPRepository.deleteById(id);
    }
}
