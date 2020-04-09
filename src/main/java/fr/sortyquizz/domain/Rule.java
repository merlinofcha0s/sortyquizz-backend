package fr.sortyquizz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nb_max_questions", nullable = false)
    private Integer nbMaxQuestions;

    @NotNull
    @Column(name = "time_per_question", nullable = false)
    private Integer timePerQuestion;

    @NotNull
    @Column(name = "time_for_sorting", nullable = false)
    private Integer timeForSorting;

    @NotNull
    @Column(name = "nb_min_card_to_win", nullable = false)
    private Integer nbMinCardToWin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbMaxQuestions() {
        return nbMaxQuestions;
    }

    public Rule nbMaxQuestions(Integer nbMaxQuestions) {
        this.nbMaxQuestions = nbMaxQuestions;
        return this;
    }

    public void setNbMaxQuestions(Integer nbMaxQuestions) {
        this.nbMaxQuestions = nbMaxQuestions;
    }

    public Integer getTimePerQuestion() {
        return timePerQuestion;
    }

    public Rule timePerQuestion(Integer timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
        return this;
    }

    public void setTimePerQuestion(Integer timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public Integer getTimeForSorting() {
        return timeForSorting;
    }

    public Rule timeForSorting(Integer timeForSorting) {
        this.timeForSorting = timeForSorting;
        return this;
    }

    public void setTimeForSorting(Integer timeForSorting) {
        this.timeForSorting = timeForSorting;
    }

    public Integer getNbMinCardToWin() {
        return nbMinCardToWin;
    }

    public Rule nbMinCardToWin(Integer nbMinCardToWin) {
        this.nbMinCardToWin = nbMinCardToWin;
        return this;
    }

    public void setNbMinCardToWin(Integer nbMinCardToWin) {
        this.nbMinCardToWin = nbMinCardToWin;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", nbMaxQuestions=" + getNbMaxQuestions() +
            ", timePerQuestion=" + getTimePerQuestion() +
            ", timeForSorting=" + getTimeForSorting() +
            ", nbMinCardToWin=" + getNbMinCardToWin() +
            "}";
    }
}
