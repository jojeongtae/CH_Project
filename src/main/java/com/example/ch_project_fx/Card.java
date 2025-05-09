package com.example.ch_project_fx;

import javafx.scene.image.Image;

public class Card {
    private String suit;   // 무늬 (♥, ♠, ♣, ◆)
    private String rank;   // 숫자 (A, 2~10, J, Q, K)
    private int value; // 점수 (A=1, 2~10=숫자값, JQK=10)
    Image img;

    public Card(String suit, String rank, int value, String img) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
        this.img = new Image(getClass().getResource(img).toExternalForm());
    }

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
