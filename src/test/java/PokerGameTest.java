import com.practice.advancejava.PokerGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokerGameTest {

    @Test
    public void testCompareHands() {
        // Test case where player 1 has a straight flush and player 2 has a full house
        String[] player1Cards = {"2H", "3H", "4H", "5H", "6H"};
        String[] player2Cards = {"2S", "2H", "2D", "3C", "3D"};
        assertEquals(1, PokerGame.compareHands(player1Cards, player2Cards));

        // Test case where both players have a flush, but player 1 has a higher rank
        String[] player1Cards2 = {"2H", "4H", "6H", "8H", "TH"};
        String[] player2Cards2 = {"2S", "4S", "6S", "8S", "9S"};
        assertEquals(1, PokerGame.compareHands(player1Cards2, player2Cards2));

        // Test case where both players have a straight, but player 2 has a higher rank
        String[] player1Cards3 = {"2H", "3S", "4C", "5D", "6H"};
        String[] player2Cards3 = {"3D", "4H", "5S", "6D", "7C"};
        assertEquals(-1, PokerGame.compareHands(player1Cards3, player2Cards3));

        // Test case where both players have the same high card
        String[] player1Cards4 = {"2H", "4S", "6C", "8D", "TC"};
        String[] player2Cards4 = {"2S", "4H", "6D", "8C", "TD"};
        assertEquals(0, PokerGame.compareHands(player1Cards4, player2Cards4));

        // Test case where both players have a two pair, but player 2 has a higher rank
        String[] player1Cards6 = {"2H", "2S", "4C", "4D", "5H"};
        String[] player2Cards6 = {"3S", "3H", "5D", "5C", "6S"};
        assertEquals(-1, PokerGame.compareHands(player1Cards6, player2Cards6));
    }
}
