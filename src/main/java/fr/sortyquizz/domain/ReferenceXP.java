package fr.sortyquizz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.sortyquizz.domain.enumeration.XPType;

/**
 * A ReferenceXP.
 */
@Entity
@Table(name = "reference_xp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReferenceXP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @NotNull
    @Column(name = "min_xp", nullable = false)
    private Integer minXp;

    @NotNull
    @Column(name = "max_xp", nullable = false)
    private Integer maxXp;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "xp_type", nullable = false)
    private XPType xpType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public ReferenceXP level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMinXp() {
        return minXp;
    }

    public ReferenceXP minXp(Integer minXp) {
        this.minXp = minXp;
        return this;
    }

    public void setMinXp(Integer minXp) {
        this.minXp = minXp;
    }

    public Integer getMaxXp() {
        return maxXp;
    }

    public ReferenceXP maxXp(Integer maxXp) {
        this.maxXp = maxXp;
        return this;
    }

    public void setMaxXp(Integer maxXp) {
        this.maxXp = maxXp;
    }

    public XPType getXpType() {
        return xpType;
    }

    public ReferenceXP xpType(XPType xpType) {
        this.xpType = xpType;
        return this;
    }

    public void setXpType(XPType xpType) {
        this.xpType = xpType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReferenceXP)) {
            return false;
        }
        return id != null && id.equals(((ReferenceXP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReferenceXP{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", minXp=" + getMinXp() +
            ", maxXp=" + getMaxXp() +
            ", xpType='" + getXpType() + "'" +
            "}";
    }
}
