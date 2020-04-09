package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.sortyquizz.domain.enumeration.XPType;

/**
 * A DTO for the {@link fr.sortyquizz.domain.ReferenceXP} entity.
 */
public class ReferenceXPDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer level;

    @NotNull
    private Integer minXp;

    @NotNull
    private Integer maxXp;

    @NotNull
    private XPType xpType;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMinXp() {
        return minXp;
    }

    public void setMinXp(Integer minXp) {
        this.minXp = minXp;
    }

    public Integer getMaxXp() {
        return maxXp;
    }

    public void setMaxXp(Integer maxXp) {
        this.maxXp = maxXp;
    }

    public XPType getXpType() {
        return xpType;
    }

    public void setXpType(XPType xpType) {
        this.xpType = xpType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReferenceXPDTO referenceXPDTO = (ReferenceXPDTO) o;
        if (referenceXPDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referenceXPDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferenceXPDTO{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", minXp=" + getMinXp() +
            ", maxXp=" + getMaxXp() +
            ", xpType='" + getXpType() + "'" +
            "}";
    }
}
