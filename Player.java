import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * This is a class for player and it's details and properties
 */
public class Player {
    private String name;
    private ArrayList<Card> myCards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    /**
     * This is a method to remove a card from player's cards
     *
     * @param cardToRemove
     */
    public void removeCard(Card cardToRemove) {
        Iterator<Card> it = myCards.iterator();
        while (it.hasNext()) {
            Card card = it.next();
            if (card.equals(cardToRemove)) {
                it.remove();
                return;
            }
        }
    }

    /**
     * This is a method to add a card into player's cards
     *
     * @param cardToAdd
     */
    public void addCardToMyCards(Card cardToAdd) {
        myCards.add(cardToAdd);
    }

    public String getName() {
        return name;
    }

    /**
     * This is a method to check if player has draw2 card
     *
     * @return
     */
    public boolean hasDraw2Card() {
        for (Card card : myCards) {
            if (card.getCardMode().equals("Draw2")) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method to check if player has wild draw 4 card
     *
     * @return
     */
    public boolean hasWildDraw4Card() {
        for (Card card : myCards) {
            if (card.getCardMode().equals("WildDraw4")) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method to get draw2 card from player's cards
     *
     * @return
     */
    public Card getDraw2Card() {
        for (Card card : myCards) {
            if (card.getCardMode().equals("Draw2")) {
                return card;
            }
        }
        return null;
    }

    /**
     * This is a method to get WildDraw4 card from player's cards
     *
     * @return
     */
    public Card getWildDraw4Card() {
        for (Card card : myCards) {
            if (card.getCardMode().equals("WildDraw4")) {
                return card;
            }
        }
        return null;
    }

    /**
     * This is a method to get player's score
     *
     * @return
     */
    public int getScore() {
        int sum = 0;
        for (Card card : myCards) {
            sum += card.getScore();
        }
        return sum;
    }

    /**
     * This is a method to check if all of the player's cards are not available for present card
     *
     * @param presentCard
     * @param presentColor
     * @return
     */
    public boolean allCardsNotAvailable(Card presentCard, String presentColor) {
        for (Card card : myCards) {
            if (card.isItAvailable(presentCard, presentColor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This is a method to check if player can throw any card except wild draw 4 card
     *
     * @param presentCard
     * @param presentColor
     * @return
     */
    public boolean checkIfPlayerCanThrowAnythingExceptWildDraw4(Card presentCard, String presentColor) {
        for (Card card : myCards) {
            if (!card.getCardMode().equals("WildDraw4") && card.isItAvailable(presentCard, presentColor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method to get a card by id from player's cards
     *
     * @param cardID
     * @return
     */
    public Card getCardByID(int cardID) {
        if (cardID >= myCards.size() || cardID < 0) {
            return null;
        } else {
            return myCards.get(cardID);
        }
    }

    public ArrayList<Card> getMyCards() {
        return myCards;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This is a method to get number of player's cards
     *
     * @return
     */
    public int getSizeOfCards() {
        return myCards.size();
    }

    /**
     * This is a method to check if two players are the same
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) &&
                Objects.equals(myCards, player.myCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, myCards);
    }
}
