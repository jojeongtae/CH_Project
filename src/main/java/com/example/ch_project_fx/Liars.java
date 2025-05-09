package com.example.ch_project_fx;

import java.util.ArrayList;
import java.util.List;

public interface Liars {
        String getName();
        List<Card> selectAct(List<Card> gameDeck );
        boolean StrikeLiar( List<Card> LastPlayerCard);
        List<Card> getHand();
        LiarsStrategy getStrategy();
}
