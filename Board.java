import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This is a class for storing storageCards and handling board of game
 */
public class Board {
    private Queue<Card> storageCards = new LinkedList<>();

    public Board() {
        createShuffleAndAddCards();
    }

    /**
     * This is a method to get the top card on storage cards
     *
     * @return
     */
    public Card getPresentCard() {
        return storageCards.peek();
    }

    /**
     * This is a method to give a card from storage cards to a player
     *
     * @return
     */
    public Card giveMeACardForPlayer() {
        Card cardToGive = new Card(storageCards.peek().getCardMode(), storageCards.peek().getColor(), storageCards.peek().getScore());
        storageCards.remove();
        return cardToGive;
    }

    /**
     * This is a method to move top card to end of storage cards
     */
    public void shuffleThePresentCardToTheEnd() {
        storageCards.add(storageCards.remove());
    }

    /**
     * This is a method to remove the top card
     */
    public void removePresentCard() {
        storageCards.remove();
    }

    /**
     * This is a method to add a card to the end of storage cards
     *
     * @param cardToAdd
     */
    public void addCard(Card cardToAdd) {
        storageCards.add(cardToAdd);
    }


    /**
     * This is a method for creating all 108 cards and shuffling them
     */
    private void createShuffleAndAddCards() {
        //Creating all 108 cards and saving them into an arraylist
        ArrayList<Card> cards = new ArrayList<>();
        String[] colors = {"red", "blue", "yellow", "green"};
        for (String color : colors) {
            for (int i = 1; i < 10; i++) {
                for (int j = 0; j < 2; j++) {
                    cards.add(new Card("Numeric", color, i));
                }
            }
            cards.add(new Card("Numeric", color, 0));
            for (int i = 0; i < 2; i++) {
                cards.add(new Card("Skip", color, 20));
                cards.add(new Card("Draw2", color, 20));
                cards.add(new Card("Reverse", color, 20));
            }
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new Card("WildDraw4", "none", 50));
            cards.add(new Card("WildColorChanger", "none", 50));
        }
        //shuffling cards and adding them into the storageCards main list
        Collections.shuffle(cards);
        for (Card card : cards) {
            storageCards.add(card);
        }
        cards.clear();
    }
}
