package com.example.pontes_stefane_esig.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Listt extends Model{

    private long project_id;
    private String name;
    private List<Card> cards;

    public Listt() {
        cards = new ArrayList<>();
    }

    public Listt(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public Listt(long id, String name, long project_id) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
        cards = new ArrayList<>();
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public double getTotal() {
        double total = 0;
        for (Card card : cards)
            total += card.getPoints();
        return total;
    }

    @Override
    public String toString() {
        return name;
    }
}
