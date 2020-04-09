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
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserPack> userPacks = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ThemeScore> themeScores = new HashSet<>();

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

    public Profile level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Set<UserPack> getUserPacks() {
        return userPacks;
    }

    public Profile userPacks(Set<UserPack> userPacks) {
        this.userPacks = userPacks;
        return this;
    }

    public Profile addUserPack(UserPack userPack) {
        this.userPacks.add(userPack);
        userPack.setProfile(this);
        return this;
    }

    public Profile removeUserPack(UserPack userPack) {
        this.userPacks.remove(userPack);
        userPack.setProfile(null);
        return this;
    }

    public void setUserPacks(Set<UserPack> userPacks) {
        this.userPacks = userPacks;
    }

    public Set<ThemeScore> getThemeScores() {
        return themeScores;
    }

    public Profile themeScores(Set<ThemeScore> themeScores) {
        this.themeScores = themeScores;
        return this;
    }

    public Profile addThemeScore(ThemeScore themeScore) {
        this.themeScores.add(themeScore);
        themeScore.setProfile(this);
        return this;
    }

    public Profile removeThemeScore(ThemeScore themeScore) {
        this.themeScores.remove(themeScore);
        themeScore.setProfile(null);
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
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            "}";
    }
}
