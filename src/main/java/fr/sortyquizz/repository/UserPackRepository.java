package fr.sortyquizz.repository;

import fr.sortyquizz.domain.UserPack;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserPack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPackRepository extends JpaRepository<UserPack, Long> {

    int countAllByProfileUserLogin(String login);

}
