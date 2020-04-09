package fr.sortyquizz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import fr.sortyquizz.domain.enumeration.ValueType;

import fr.sortyquizz.domain.enumeration.SortingType;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "display", nullable = false)
    private String display;

    @NotNull
    @Column(name = "value_to_sort", nullable = false)
    private String valueToSort;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false)
    private ValueType valueType;

    
    @Lob
    @Column(name = "picture", nullable = false)
    private byte[] picture;

    @Column(name = "picture_content_type", nullable = false)
    private String pictureContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sorting_type", nullable = false)
    private SortingType sortingType;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties("cards")
    private Pack pack;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public Card display(String display) {
        this.display = display;
        return this;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getValueToSort() {
        return valueToSort;
    }

    public Card valueToSort(String valueToSort) {
        this.valueToSort = valueToSort;
        return this;
    }

    public void setValueToSort(String valueToSort) {
        this.valueToSort = valueToSort;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public Card valueType(ValueType valueType) {
        this.valueType = valueType;
        return this;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Card picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Card pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public SortingType getSortingType() {
        return sortingType;
    }

    public Card sortingType(SortingType sortingType) {
        this.sortingType = sortingType;
        return this;
    }

    public void setSortingType(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    public Integer getOrder() {
        return order;
    }

    public Card order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Pack getPack() {
        return pack;
    }

    public Card pack(Pack pack) {
        this.pack = pack;
        return this;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return id != null && id.equals(((Card) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", display='" + getDisplay() + "'" +
            ", valueToSort='" + getValueToSort() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", sortingType='" + getSortingType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
