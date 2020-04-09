package fr.sortyquizz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import fr.sortyquizz.domain.enumeration.PackType;

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
    @Column(name = "level", nullable = false)
    private Integer level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PackType type;

    @NotNull
    @Column(name = "life", nullable = false)
    private Integer life;

    @OneToOne
    @JoinColumn(unique = true)
    private Rule rule;

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy = "pack")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserPack> userPacks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("packs")
    private Theme theme;

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

    public Integer getLevel() {
        return level;
    }

    public Pack level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public PackType getType() {
        return type;
    }

    public Pack type(PackType type) {
        this.type = type;
        return this;
    }

    public void setType(PackType type) {
        this.type = type;
    }

    public Integer getLife() {
        return life;
    }

    public Pack life(Integer life) {
        this.life = life;
        return this;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Rule getRule() {
        return rule;
    }

    public Pack rule(Rule rule) {
        this.rule = rule;
        return this;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
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

    public Set<UserPack> getUserPacks() {
        return userPacks;
    }

    public Pack userPacks(Set<UserPack> userPacks) {
        this.userPacks = userPacks;
        return this;
    }

    public Pack addUserPack(UserPack userPack) {
        this.userPacks.add(userPack);
        userPack.setPack(this);
        return this;
    }

    public Pack removeUserPack(UserPack userPack) {
        this.userPacks.remove(userPack);
        userPack.setPack(null);
        return this;
    }

    public void setUserPacks(Set<UserPack> userPacks) {
        this.userPacks = userPacks;
    }

    public Theme getTheme() {
        return theme;
    }

    public Pack theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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
            ", level=" + getLevel() +
            ", type='" + getType() + "'" +
            ", life=" + getLife() +
            "}";
    }
}
