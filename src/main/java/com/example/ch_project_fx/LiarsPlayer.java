package com.example.ch_project_fx;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiarsPlayer implements Liars{
    String name;
    List<Card> PlayerDeck = new ArrayList<>();
    Card FieldCard;
    String mainRank;
    Random random = new Random();
    LiarsStrategy strategy;
    Image img;
    public LiarsPlayer() {
        String[] FirstName = {"김", "이", "박", "최", "장", "조", "황","손","지"};
        String[] LastName = {"정태", "주언", "재호", "현범", "둘리", "흥민", "시현","준홍","선아","민규","창현"};
        this.name = FirstName[random.nextInt(9)] + LastName[random.nextInt(11)];
        setRandomStrategy();
        if (this.name.equals("조정태")) {
            setStrategy(LiarsStrategy.CHEATER);
        }
    }

    public List<Card> selectAct(List<Card> gameDeck) {
        List<Card> playerSelect = new ArrayList<>();
        while (playerSelect.isEmpty()) {
            switch (this.strategy) {
                case DEFENSIVE:
                    int roll = random.nextInt(10);
                    int randomCount = random.nextInt(2) + 1;
                    if (roll < 9) {
                        for (Card c : this.PlayerDeck) {
                            if (c.getRank().equals(this.mainRank) || c.getRank().equals("Joker")) {
                                playerSelect.add(c);
                                if (randomCount <= playerSelect.size()) {
                                    break;
                                }
                            }

                        }
                    } else {
                        for (Card c : this.PlayerDeck) {
                            playerSelect.add(c);
                            if (randomCount <= playerSelect.size()) {
                                break;
                            }
                        }

                    }
                    break;
                case AGGRESSIVE:
                    roll = random.nextInt(10);
                    randomCount = Math.max(2,random.nextInt(3) + 1);
                    if (roll < 2) {
                        for (Card c : this.PlayerDeck) {
                            if (c.getRank().equals(this.mainRank) || c.getRank().equals("Joker")) {
                                playerSelect.add(c);
                                if (randomCount <= playerSelect.size()) {
                                    break;
                                }
                            }

                        }
                    } else {
                        for (Card c : this.PlayerDeck) {
                            playerSelect.add(c);
                            if (randomCount <= playerSelect.size()) {
                                break;
                            }
                        }

                    }
                    break;
                case RANDOM:
                    int choice = random.nextInt(2);
                    randomCount = random.nextInt(3) + 1;
                    switch (choice) {
                        case 0:

                            for (Card c : this.PlayerDeck) {
                                if (c.getRank().equals(this.mainRank) || c.getRank().equals("Joker")) {
                                    playerSelect.add(c);
                                    if (randomCount <= playerSelect.size()) {
                                        break;
                                    }
                                }

                            }
                            break;
                        case 1:
                            for (Card c : this.PlayerDeck) {
                                playerSelect.add(c);
                                if (randomCount <= playerSelect.size()) {
                                    break;
                                }
                            }
                            break;

                    }
                    break;
                case CHEATER:
                    roll = random.nextInt(10);
                    randomCount = random.nextInt(2) + 1;
                    if (roll < 10) {
                        for (Card c : this.PlayerDeck) {
                            if (c.getRank().equals(this.mainRank) || c.getRank().equals("Joker")) {
                                playerSelect.add(c);
                                if (randomCount <= playerSelect.size()) {
                                    break;
                                }
                            }

                        }
                    } else {
                        for (Card c : this.PlayerDeck) {
                            playerSelect.add(c);
                            if (randomCount <= playerSelect.size()) {
                                break;
                            }
                        }

                    }
                    break;
            }
        }
        this.PlayerDeck.removeAll(playerSelect);
//        SP.s(this.name + "(이)가 카드를 내면서 말합니다! 😁 [" + this.mainRank + "] " + playerSelect.size() + "장! ", 1000);
        return playerSelect;

    }

    public boolean StrikeLiar(List<Card> LastPlayerCard) {
        int validCount = 0;
        for (Card c : LastPlayerCard) {
            if (c.getRank().equals(this.mainRank) || c.getRank().equals("Joker")) {
                validCount++;
            }
        }
        // 모든 카드가 mainRank 또는 joker로 이루어져 있으면 정직
        return validCount < LastPlayerCard.size(); // 하나라도 다르면 → 거짓말(true)
    }

    public void setRandomStrategy() {
        LiarsStrategy[] strategies = LiarsStrategy.values();
        this.strategy = strategies[random.nextInt(strategies.length)];
    }

    public void setStrategy(LiarsStrategy strategy) {
        this.strategy = strategy;
    }

    public LiarsStrategy getStrategy() {
        return strategy;
    }
    public List<Card> getHand() {
        return this.PlayerDeck;
    }

    public String getName() {
        return this.name;
    }

}
