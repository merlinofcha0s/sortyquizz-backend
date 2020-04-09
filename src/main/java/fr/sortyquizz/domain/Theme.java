package fr.sortyquizz.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Theme.
 */
@Entity
@Table(name = "theme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "theme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pack> packs = new HashSet<>();

    @OneToMany(mappedBy = "theme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThemeScore> themeScores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Theme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pack> getPacks() {
        return packs;
    }

    public Theme packs(Set<Pack> packs) {
        this.packs = packs;
        return this;
    }

    public Theme addPack(Pack pack) {
        this.packs.add(pack);
        pack.setTheme(this);
        return this;
    }

    public Theme removePack(Pack pack) {
        this.packs.remove(pack);
        pack.setTheme(null);
        return this;
    }

    public void setPacks(Set<Pack> packs) {
        this.packs = packs;
    }

    public Set<ThemeScore> getThemeScores() {
        return themeScores;
    }

    public Theme themeScores(Set<ThemeScore> themeScores) {
        this.themeScores = themeScores;
        return this;
    }

    public Theme addThemeScore(ThemeScore themeScore) {
        this.themeScores.add(themeScore);
        themeScore.setTheme(this);
        return this;
    }

    public Theme removeThemeScore(ThemeScore themeScore) {
        this.themeScores.remove(themeScore);
        themeScore.setTheme(null);
        return this;
    }

    public void setThemeScores(Set<ThemeScore> themeScores) {
        this.themeScores = themeScores;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Theme)) {
            return false;
        }
        return id != null && id.equals(((Theme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Theme{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
