package fr.sortyquizz.repository;

import fr.sortyquizz.domain.Card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Card entity.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "select distinct card from Card card left join fetch card.profiles",
        countQuery = "select count(distinct card) from Card card")
    Page<Card> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct card from Card card left join fetch card.profiles")
    List<Card> findAllWithEagerRelationships();

    @Query("select card from Card card left join fetch card.profiles where card.id =:id")
    Optional<Card> findOneWithEagerRelationships(@Param("id") Long id);
}
