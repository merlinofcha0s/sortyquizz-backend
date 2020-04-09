package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.sortyquizz.domain.ThemeScore} entity.
 */
public class ThemeScoreDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer xp;

    @NotNull
    private Integer level;


    private Long profileId;

    private Long themeId;

    private String themeName;

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

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
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

        ThemeScoreDTO themeScoreDTO = (ThemeScoreDTO) o;
        if (themeScoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeScoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThemeScoreDTO{" +
            "id=" + getId() +
            ", xp=" + getXp() +
            ", level=" + getLevel() +
            ", profileId=" + getProfileId() +
            ", themeId=" + getThemeId() +
            "}";
    }
}
