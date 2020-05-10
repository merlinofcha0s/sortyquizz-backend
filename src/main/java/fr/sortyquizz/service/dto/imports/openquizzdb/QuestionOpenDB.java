package fr.sortyquizz.service.dto.imports.openquizzdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionOpenDB implements Serializable  {

    private int id;

    private String question;

    @JsonProperty("propositions")
    private List<String> answers = new ArrayList<>();

    @JsonProperty("réponse")
    private String reponse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionOpenDB that = (QuestionOpenDB) o;
        return id == that.id &&
            Objects.equals(question, that.question) &&
            Objects.equals(answers, that.answers) &&
            Objects.equals(reponse, that.reponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answers, reponse);
    }

    @Override
    public String toString() {
        return "QuestionOpenDB{" +
            "id=" + id +
            ", question='" + question + '\'' +
            ", answers=" + answers +
            ", reponse='" + reponse + '\'' +
            '}';
    }
}
