package fr.sortyquizz.repository;

import fr.sortyquizz.domain.ThemeScore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ThemeScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThemeScoreRepository extends JpaRepository<ThemeScore, Long> {
}
