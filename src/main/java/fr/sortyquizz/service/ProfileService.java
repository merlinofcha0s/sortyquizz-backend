package fr.sortyquizz.service;

import fr.sortyquizz.domain.Profile;
import fr.sortyquizz.domain.User;
import fr.sortyquizz.repository.ProfileRepository;
import fr.sortyquizz.service.dto.ProfileDTO;
import fr.sortyquizz.service.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable)
            .map(profileMapper::toDto);
    }

    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id)
            .map(profileMapper::toDto);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }

    /**
     * Delete the profile by user id.
     *
     * @param userId the id of the user.
     */
    public void deleteByUserId(Long userId) {
        log.debug("Request to delete Profile for user : {}", userId);
//        profileRepository.deleteByUserId(userId);
    }

    /**
     * Create a new profile for a new user.
     *
     * @param user the entity to save.
     * @return the persisted new profile.
     */
    public ProfileDTO createForUser(User user) {
        log.debug("Request to create new profile for : {}", user);
        Profile profile = new Profile();
        profile.setLevel(1);
//        profile.setUser(user);
        ProfileDTO newProfileCreated = profileMapper.toDto(profile);
        return save(newProfileCreated);
    }
}
