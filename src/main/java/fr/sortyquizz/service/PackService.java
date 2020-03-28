package fr.sortyquizz.service;

import fr.sortyquizz.domain.Pack;
import fr.sortyquizz.repository.PackRepository;
import fr.sortyquizz.service.dto.PackDTO;
import fr.sortyquizz.service.mapper.PackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Pack}.
 */
@Service
@Transactional
public class PackService {

    private final Logger log = LoggerFactory.getLogger(PackService.class);

    private final PackRepository packRepository;

    private final PackMapper packMapper;

    public PackService(PackRepository packRepository, PackMapper packMapper) {
        this.packRepository = packRepository;
        this.packMapper = packMapper;
    }

    /**
     * Save a pack.
     *
     * @param packDTO the entity to save.
     * @return the persisted entity.
     */
    public PackDTO save(PackDTO packDTO) {
        log.debug("Request to save Pack : {}", packDTO);
        Pack pack = packMapper.toEntity(packDTO);
        pack = packRepository.save(pack);
        return packMapper.toDto(pack);
    }

    /**
     * Get all the packs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Packs");
        return packRepository.findAll(pageable)
            .map(packMapper::toDto);
    }

    /**
     * Get one pack by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PackDTO> findOne(Long id) {
        log.debug("Request to get Pack : {}", id);
        return packRepository.findById(id)
            .map(packMapper::toDto);
    }

    /**
     * Delete the pack by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pack : {}", id);
        packRepository.deleteById(id);
    }
}
