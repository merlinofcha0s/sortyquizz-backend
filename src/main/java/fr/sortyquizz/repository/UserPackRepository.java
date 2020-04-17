package fr.sortyquizz.repository;

import fr.sortyquizz.domain.UserPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserPack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPackRepository extends JpaRepository<UserPack, Long> {

    int countAllByProfileUserLogin(String login);

    List<UserPack> findAllByProfileUserLogin(String login);

    Optional<UserPack> findByIdAndProfileUserLogin(Long id, String login);

    Optional<UserPack> findByPackIdAndProfileUserLogin(Long packId, String login);

}
