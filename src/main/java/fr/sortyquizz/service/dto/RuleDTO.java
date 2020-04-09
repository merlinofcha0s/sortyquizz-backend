package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.sortyquizz.domain.Rule} entity.
 */
public class RuleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer nbMaxQuestions;

    @NotNull
    private Integer timePerQuestion;

    @NotNull
    private Integer timeForSorting;

    @NotNull
    private Integer nbMinCardToWin;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbMaxQuestions() {
        return nbMaxQuestions;
    }

    public void setNbMaxQuestions(Integer nbMaxQuestions) {
        this.nbMaxQuestions = nbMaxQuestions;
    }

    public Integer getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(Integer timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public Integer getTimeForSorting() {
        return timeForSorting;
    }

    public void setTimeForSorting(Integer timeForSorting) {
        this.timeForSorting = timeForSorting;
    }

    public Integer getNbMinCardToWin() {
        return nbMinCardToWin;
    }

    public void setNbMinCardToWin(Integer nbMinCardToWin) {
        this.nbMinCardToWin = nbMinCardToWin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RuleDTO ruleDTO = (RuleDTO) o;
        if (ruleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RuleDTO{" +
            "id=" + getId() +
            ", nbMaxQuestions=" + getNbMaxQuestions() +
            ", timePerQuestion=" + getTimePerQuestion() +
            ", timeForSorting=" + getTimeForSorting() +
            ", nbMinCardToWin=" + getNbMinCardToWin() +
            "}";
    }
}
