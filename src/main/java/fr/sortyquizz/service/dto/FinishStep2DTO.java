package fr.sortyquizz.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FinishStep2DTO implements Serializable {

    private int passedTime;

    private int nbCards;

    private List<CardDTO> cards;

    public int getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(int passedTime) {
        this.passedTime = passedTime;
    }

    public int getNbCards() {
        return nbCards;
    }

    public void setNbCards(int nbCards) {
        this.nbCards = nbCards;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinishStep2DTO that = (FinishStep2DTO) o;
        return passedTime == that.passedTime &&
            nbCards == that.nbCards &&
            Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passedTime, nbCards, cards);
    }

    @Override
    public String toString() {
        return "FinishStep2DTO{" +
            "passedTime=" + passedTime +
            ", nbCards=" + nbCards +
            ", cards=" + cards +
            '}';
    }
}
