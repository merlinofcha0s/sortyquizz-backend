package fr.sortyquizz.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import fr.sortyquizz.domain.enumeration.ValueType;
import fr.sortyquizz.domain.enumeration.SortingType;

/**
 * A DTO for the {@link fr.sortyquizz.domain.Card} entity.
 */
public class CardDTO implements Serializable {

    private Long id;

    @NotNull
    private String display;

    @NotNull
    private String valueToSort;

    @NotNull
    private ValueType valueType;


    @Lob
    private byte[] picture;

    private String pictureContentType;
    @NotNull
    private SortingType sortingType;

    private Integer order;


    private Long packId;

    private String packName;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValueToSort() {
        return valueToSort;
    }

    public void setValueToSort(String valueToSort) {
        this.valueToSort = valueToSort;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public void setSortingType(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

        CardDTO cardDTO = (CardDTO) o;
        if (cardDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cardDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + getId() +
            ", display='" + getDisplay() + "'" +
            ", valueToSort='" + getValueToSort() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", picture='" + getPicture() + "'" +
            ", sortingType='" + getSortingType() + "'" +
            ", order=" + getOrder() +
            ", packId=" + getPackId() +
            "}";
    }
}
