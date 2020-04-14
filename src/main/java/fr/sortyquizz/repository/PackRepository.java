package fr.sortyquizz.repository;

import fr.sortyquizz.domain.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Pack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

}
