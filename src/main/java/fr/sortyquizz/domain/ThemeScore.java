package fr.sortyquizz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ThemeScore.
 */
@Entity
@Table(name = "theme_score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThemeScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "xp", nullable = false)
    private Integer xp;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @ManyToOne
    @JsonIgnoreProperties("themeScores")
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties("themeScores")
    private Theme theme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getXp() {
        return xp;
    }

    public ThemeScore xp(Integer xp) {
        this.xp = xp;
        return this;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public ThemeScore level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Profile getProfile() {
        return profile;
    }

    public ThemeScore profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Theme getTheme() {
        return theme;
    }

    public ThemeScore theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeScore)) {
            return false;
        }
        return id != null && id.equals(((ThemeScore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ThemeScore{" +
            "id=" + getId() +
            ", xp=" + getXp() +
            ", level=" + getLevel() +
            "}";
    }
}
