package fr.sortyquizz.service.dto;

import fr.sortyquizz.domain.enumeration.PackType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.sortyquizz.domain.Pack} entity.
 */
public class PackDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer level;

    @NotNull
    private PackType type;

    @NotNull
    private Integer life;

    private Long ruleId;

    private Long themeId;

    private String themeName;

    private RuleDTO rule;

    private Set<QuestionDTO> questions = new HashSet<>();

    private Set<CardDTO> cards = new HashSet<>();

    public RuleDTO getRule() {
        return rule;
    }

    public void setRule(RuleDTO rule) {
        this.rule = rule;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public PackType getType() {
        return type;
    }

    public void setType(PackType type) {
        this.type = type;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PackDTO packDTO = (PackDTO) o;
        if (packDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), packDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PackDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", level=" + getLevel() +
            ", type='" + getType() + "'" +
            ", life=" + getLife() +
            ", ruleId=" + getRuleId() +
            ", themeId=" + getThemeId() +
            "}";
    }
}
