package fr.sortyquizz.service.dto;

import fr.sortyquizz.domain.enumeration.QuestionType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.sortyquizz.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String question;

    @NotNull
    private QuestionType type;


    private Long packId;

    private String packName;

    private Set<AnswerDTO> answers = new HashSet<>();

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public Set<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (questionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", type='" + getType() + "'" +
            ", packId=" + getPackId() +
            "}";
    }
}
