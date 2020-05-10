package fr.sortyquizz.service.dto.imports.openquizzdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LocalizedQuizzDB implements Serializable {

    @JsonProperty("débutant")
    private List<QuestionOpenDB> debutant = new ArrayList<>();

    @JsonProperty("confirmé")
    private List<QuestionOpenDB> confirme = new ArrayList<>();

    @JsonProperty("expert")
    private List<QuestionOpenDB> expert = new ArrayList<>();

    public List<QuestionOpenDB> getDebutant() {
        return debutant;
    }

    public void setDebutant(List<QuestionOpenDB> debutant) {
        this.debutant = debutant;
    }

    public List<QuestionOpenDB> getConfirme() {
        return confirme;
    }

    public void setConfirme(List<QuestionOpenDB> confirme) {
        this.confirme = confirme;
    }

    public List<QuestionOpenDB> getExpert() {
        return expert;
    }

    public void setExpert(List<QuestionOpenDB> expert) {
        this.expert = expert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedQuizzDB that = (LocalizedQuizzDB) o;
        return Objects.equals(debutant, that.debutant) &&
            Objects.equals(confirme, that.confirme) &&
            Objects.equals(expert, that.expert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debutant, confirme, expert);
    }

    @Override
    public String toString() {
        return "LocalizedQuizzDB{" +
            "debutant=" + debutant +
            ", confirme=" + confirme +
            ", expert=" + expert +
            '}';
    }
}
