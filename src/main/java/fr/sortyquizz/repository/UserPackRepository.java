package fr.sortyquizz.repository;

import fr.sortyquizz.domain.UserPack;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserPack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPackRepository extends JpaRepository<UserPack, Long> {
}
