package com.practice.advancejava;

import java.util.*;

import static com.practice.advancejava.PokerGame.compareHands;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int player1Wins = 0;
        int player2Wins = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break; // Exit loop if empty line encountered

            String[] cards = line.split(" ");
            if (cards.length < 10) {
                System.err.println("Invalid input: Each line must contain at least 10 card values.");
                continue;
            }

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

    public static class HandRanking {
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
