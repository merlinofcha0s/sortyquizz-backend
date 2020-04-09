package fr.sortyquizz.repository;

import fr.sortyquizz.domain.ReferenceXP;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReferenceXP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReferenceXPRepository extends JpaRepository<ReferenceXP, Long> {
}
