package fr.sortyquizz.repository;

import fr.sortyquizz.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findByPackId(Long id);

}
