package com.practice.advancejava;

import java.util.*;

public class Main {
    public static void main(String [] args) {

        Scanner scanner = new Scanner(System.in);
        int player1Wins = 0;
        int player2Wins = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break; // Exit loop if empty line encountered

            String[] cards = line.split(" ");
            String[] player1Cards = Arrays.copyOfRange(cards, 0, 5);
            String[] player2Cards = Arrays.copyOfRange(cards, 5, 10);

            int result = compareHands(player1Cards, player2Cards);
            if (result > 0) {
                player1Wins++;
            } else if (result < 0) {
                player2Wins++;
            }
        }

        System.out.println("Player 1: " + player1Wins + " hands");
        System.out.println("Player 2: " + player2Wins + " hands");

    }

    private static int compareHands(String[] player1Cards, String[] player2Cards) {
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

        if (valueCount.size() == 5 && !suitCount.containsValue(5) && !isConsecutive(valuesToIndices(valueCount.keySet()))) {
            return new HandRanking(1, valuesToIndices(valueCount.keySet()));
        } else if (valueCount.size() == 4) {
            return new HandRanking(2, valuesToIndices(valueCount.keySet()));
        } else if (valueCount.size() == 3 && (maxCount == 3 || maxCount == 2)) {
            return new HandRanking(maxCount == 3 ? 3 : 7, valuesToIndices(valueCount.keySet()));
        } else if (valueCount.size() == 2 && (maxCount == 4 || maxCount == 1)) {
            return new HandRanking(maxCount == 4 ? 8 : 4, valuesToIndices(valueCount.keySet()));
        } else {
            return new HandRanking(10, valuesToIndices(valueCount.keySet()));
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
        for (int i = player1Values.length - 1; i >= 0; i--) {
            if (player1Values[i] > player2Values[i]) {
                return 1;
            } else if (player1Values[i] < player2Values[i]) {
                return -1;
            }
        }
        return 0;
    }

    private static class HandRanking {
        private int value;
        private int[] sortedValues;

        public HandRanking(int value, int[] sortedValues) {
            this.value = value;
            this.sortedValues = sortedValues;
        }

        public int getValue() {
            return value;
        }

        public int[] getSortedValues() {
            return sortedValues;
        }
    }
}
