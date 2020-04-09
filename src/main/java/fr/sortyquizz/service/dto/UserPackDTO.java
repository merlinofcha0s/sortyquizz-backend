package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import fr.sortyquizz.domain.enumeration.PackState;

/**
 * A DTO for the {@link fr.sortyquizz.domain.UserPack} entity.
 */
public class UserPackDTO implements Serializable {
    
    private Long id;

    @NotNull
    private PackState state;

    @NotNull
    private Integer lifeLeft;

    @NotNull
    private Integer nbQuestionsToSucceed;

    @NotNull
    private Integer timeAtQuizzStep;

    @NotNull
    private Integer timeAtSortingStep;


    private Long profileId;

    private Long packId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackState getState() {
        return state;
    }

    public void setState(PackState state) {
        this.state = state;
    }

    public Integer getLifeLeft() {
        return lifeLeft;
    }

    public void setLifeLeft(Integer lifeLeft) {
        this.lifeLeft = lifeLeft;
    }

    public Integer getNbQuestionsToSucceed() {
        return nbQuestionsToSucceed;
    }

    public void setNbQuestionsToSucceed(Integer nbQuestionsToSucceed) {
        this.nbQuestionsToSucceed = nbQuestionsToSucceed;
    }

    public Integer getTimeAtQuizzStep() {
        return timeAtQuizzStep;
    }

    public void setTimeAtQuizzStep(Integer timeAtQuizzStep) {
        this.timeAtQuizzStep = timeAtQuizzStep;
    }

    public Integer getTimeAtSortingStep() {
        return timeAtSortingStep;
    }

    public void setTimeAtSortingStep(Integer timeAtSortingStep) {
        this.timeAtSortingStep = timeAtSortingStep;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPackDTO userPackDTO = (UserPackDTO) o;
        if (userPackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPackDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", lifeLeft=" + getLifeLeft() +
            ", nbQuestionsToSucceed=" + getNbQuestionsToSucceed() +
            ", timeAtQuizzStep=" + getTimeAtQuizzStep() +
            ", timeAtSortingStep=" + getTimeAtSortingStep() +
            ", profileId=" + getProfileId() +
            ", packId=" + getPackId() +
            "}";
    }
}
