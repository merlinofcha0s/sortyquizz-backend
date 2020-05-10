package fr.sortyquizz.service.dto.imports.openquizzdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ThemeQuizzDBDTO implements Serializable {

    @JsonProperty("thème")
    private String theme;

    @JsonProperty("quizz")
    private QuizzDBDTO quizzDBDTO;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public QuizzDBDTO getQuizzDBDTO() {
        return quizzDBDTO;
    }

    public void setQuizzDBDTO(QuizzDBDTO quizzDBDTO) {
        this.quizzDBDTO = quizzDBDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThemeQuizzDBDTO that = (ThemeQuizzDBDTO) o;
        return Objects.equals(theme, that.theme) &&
            Objects.equals(quizzDBDTO, that.quizzDBDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theme, quizzDBDTO);
    }

    @Override
    public String toString() {
        return "ThemeQuizzDBDTO{" +
            "theme='" + theme + '\'' +
            ", quizzDBDTO=" + quizzDBDTO +
            '}';
    }
}
