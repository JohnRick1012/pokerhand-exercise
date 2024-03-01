package com.practice.advancejava;

import java.util.*;
import com.practice.advancejava.Main.HandRanking;
public class PokerGame {
    public static int compareHands(String[] player1Cards, String[] player2Cards) {
        HandRanking player1Ranking = evaluateHand(player1Cards);
        HandRanking player2Ranking = evaluateHand(player2Cards);

        if (player1Ranking.getValue() > player2Ranking.getValue()) {
            return 1;
        } else if (player1Ranking.getValue() < player2Ranking.getValue()) {
            return -1;
        } else {
            return compareHighCards(player1Ranking.getSortedValues(), player2Ranking.getSortedValues());
        }
    }

    private static HandRanking evaluateHand(String[] cards) {
        Arrays.sort(cards, Comparator.comparing(c -> getValueIndex(c.charAt(0))));
        Map<Character, Integer> valueCount = new HashMap<>();
        Map<Character, Integer> suitCount = new HashMap<>();
        for (String card : cards) {
            char value = card.charAt(0);
            char suit = card.charAt(1);
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
            suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
        }
        int maxCount = Collections.max(valueCount.values());

        if (valueCount.size() == 5 && suitCount.size() == 1) {
            // Straight flush
            return new HandRanking(9, valuesToIndices(valueCount.keySet()));
        } else if (maxCount == 4) {
            // Four of a kind
            return new HandRanking(8, valuesToIndices(valueCount.keySet()));
        } else if (maxCount == 3 && valueCount.size() == 2) {
            // Full house
            return new HandRanking(7, valuesToIndices(valueCount.keySet()));
        } else if (suitCount.size() == 1) {
            // Flush
            return new HandRanking(6, valuesToIndices(valueCount.keySet()));
        } else if (valueCount.size() == 5 && isConsecutive(valuesToIndices(valueCount.keySet()))) {
            // Straight
            return new HandRanking(5, valuesToIndices(valueCount.keySet()));
        } else if (maxCount == 3) {
            // Three of a kind
            return new HandRanking(4, valuesToIndices(valueCount.keySet()));
        } else if (maxCount == 2 && valueCount.size() == 3) {
            // Two pairs
            return new HandRanking(3, valuesToIndices(valueCount.keySet()));
        } else if (maxCount == 2) {
            // One pair
            return new HandRanking(2, valuesToIndices(valueCount.keySet()));
        } else {
            // High card
            return new HandRanking(1, valuesToIndices(valueCount.keySet()));
        }
    }

    private static int[] valuesToIndices(Set<Character> values) {
        int[] indices = new int[values.size()];
        int i = 0;
        for (char value : values) {
            indices[i++] = getValueIndex(value);
        }
        return indices;
    }

    private static int getValueIndex(char value) {
        switch (value) {
            case 'T':
                return 10;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'A':
                return 14;
            default:
                return Character.getNumericValue(value);
        }
    }

    private static boolean isConsecutive(int[] values) {
        Arrays.sort(values);
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[i - 1] + 1) {
                return false;
            }
        }
        return true;
    }

    private static int compareHighCards(int[] player1Values, int[] player2Values) {
        for (int i = 0; i < player1Values.length; i++) {
            if (player1Values[i] > player2Values[i]) {
                return 1;
            } else if (player1Values[i] < player2Values[i]) {
                return -1;
            }
        }
        return 0;
    }
}
