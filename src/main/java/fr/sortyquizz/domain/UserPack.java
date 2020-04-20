package fr.sortyquizz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.sortyquizz.domain.enumeration.PackState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A UserPack.
 */
@Entity
@Table(name = "user_pack")
@NamedEntityGraph(name = "userpack-with-rule-pack-questions-answers-cards",
    attributeNodes = @NamedAttributeNode(value = "pack", subgraph = "pack"),
    subgraphs = {
        @NamedSubgraph(name = "pack", attributeNodes = {@NamedAttributeNode(value = "questions", subgraph = "question_answer"),
            @NamedAttributeNode("cards"), @NamedAttributeNode("rule")}),
        @NamedSubgraph(name = "question_answer", attributeNodes = {@NamedAttributeNode(value = "answers")})
    })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserPack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private PackState state;

    @NotNull
    @Column(name = "life_left", nullable = false)
    private Integer lifeLeft;

    @NotNull
    @Column(name = "nb_questions_to_succeed", nullable = false)
    private Integer nbQuestionsToSucceed;

    @NotNull
    @Column(name = "time_at_quizz_step", nullable = false)
    private Integer timeAtQuizzStep;

    @NotNull
    @Column(name = "time_at_sorting_step", nullable = false)
    private Integer timeAtSortingStep;

    @ManyToOne
    @JsonIgnoreProperties("userPacks")
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties("userPacks")
    private Pack pack;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackState getState() {
        return state;
    }

    public UserPack state(PackState state) {
        this.state = state;
        return this;
    }

    public void setState(PackState state) {
        this.state = state;
    }

    public Integer getLifeLeft() {
        return lifeLeft;
    }

    public UserPack lifeLeft(Integer lifeLeft) {
        this.lifeLeft = lifeLeft;
        return this;
    }

    public void setLifeLeft(Integer lifeLeft) {
        this.lifeLeft = lifeLeft;
    }

    public Integer getNbQuestionsToSucceed() {
        return nbQuestionsToSucceed;
    }

    public UserPack nbQuestionsToSucceed(Integer nbQuestionsToSucceed) {
        this.nbQuestionsToSucceed = nbQuestionsToSucceed;
        return this;
    }

    public void setNbQuestionsToSucceed(Integer nbQuestionsToSucceed) {
        this.nbQuestionsToSucceed = nbQuestionsToSucceed;
    }

    public Integer getTimeAtQuizzStep() {
        return timeAtQuizzStep;
    }

    public UserPack timeAtQuizzStep(Integer timeAtQuizzStep) {
        this.timeAtQuizzStep = timeAtQuizzStep;
        return this;
    }

    public void setTimeAtQuizzStep(Integer timeAtQuizzStep) {
        this.timeAtQuizzStep = timeAtQuizzStep;
    }

    public Integer getTimeAtSortingStep() {
        return timeAtSortingStep;
    }

    public UserPack timeAtSortingStep(Integer timeAtSortingStep) {
        this.timeAtSortingStep = timeAtSortingStep;
        return this;
    }

    public void setTimeAtSortingStep(Integer timeAtSortingStep) {
        this.timeAtSortingStep = timeAtSortingStep;
    }

    public Profile getProfile() {
        return profile;
    }

    public UserPack profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Pack getPack() {
        return pack;
    }

    public UserPack pack(Pack pack) {
        this.pack = pack;
        return this;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPack)) {
            return false;
        }
        return id != null && id.equals(((UserPack) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserPack{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", lifeLeft=" + getLifeLeft() +
            ", nbQuestionsToSucceed=" + getNbQuestionsToSucceed() +
            ", timeAtQuizzStep=" + getTimeAtQuizzStep() +
            ", timeAtSortingStep=" + getTimeAtSortingStep() +
            "}";
    }
}
