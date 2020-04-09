package fr.sortyquizz.service;

import fr.sortyquizz.domain.Rule;
import fr.sortyquizz.repository.RuleRepository;
import fr.sortyquizz.service.dto.RuleDTO;
import fr.sortyquizz.service.mapper.RuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rule}.
 */
@Service
@Transactional
public class RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleService.class);

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    public RuleService(RuleRepository ruleRepository, RuleMapper ruleMapper) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
    }

    /**
     * Save a rule.
     *
     * @param ruleDTO the entity to save.
     * @return the persisted entity.
     */
    public RuleDTO save(RuleDTO ruleDTO) {
        log.debug("Request to save Rule : {}", ruleDTO);
        Rule rule = ruleMapper.toEntity(ruleDTO);
        rule = ruleRepository.save(rule);
        return ruleMapper.toDto(rule);
    }

    /**
     * Get all the rules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll(pageable)
            .map(ruleMapper::toDto);
    }

    /**
     * Get one rule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RuleDTO> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findById(id)
            .map(ruleMapper::toDto);
    }

    /**
     * Delete the rule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
    }
}
