package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.sortyquizz.domain.enumeration.PackType;

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
