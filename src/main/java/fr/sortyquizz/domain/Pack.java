package fr.sortyquizz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pack.
 */
@Entity
@Table(name = "pack")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Card> cards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pack name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public Pack category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Pack questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Pack addQuestion(Question question) {
        this.questions.add(question);
        question.setPack(this);
        return this;
    }

    public Pack removeQuestion(Question question) {
        this.questions.remove(question);
        question.setPack(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public Pack cards(Set<Card> cards) {
        this.cards = cards;
        return this;
    }

    public Pack addCard(Card card) {
        this.cards.add(card);
        card.setPack(this);
        return this;
    }

    public Pack removeCard(Card card) {
        this.cards.remove(card);
        card.setPack(null);
        return this;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pack)) {
            return false;
        }
        return id != null && id.equals(((Pack) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pack{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
