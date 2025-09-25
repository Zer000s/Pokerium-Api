package org.example.pokerium_api.engine;

import lombok.Data;
import java.util.*;

@Data
public class Deck {
    private final List<String> cards = new ArrayList<>();
    private int index = 0;

    public Deck() {
        String[] suits = {"h","d","c","s"};
        String[] ranks = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
        for (String r : ranks) {
            for (String s : suits) {
                cards.add(r + s);
            }
        }
        Collections.shuffle(cards);
    }

    public String draw() { return cards.get(index++); }
}