import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This is a class for bot Player and calculations for it's movements.
 * Also this class inherits from Player class.
 */
public class BotPlayer extends Player {
    private String name = getName();
    private ArrayList<Card> myCards = getMyCards();

    public BotPlayer(String name) {
        super(name);
    }

    /**
     * This is a method to get first available card in bot's cards
     *
     * @param presentCard
     * @param presentColor
     * @return
     */
    public Card getAvailableCard(Card presentCard, String presentColor) {
        for (Card card : myCards) {
            if (card.isItAvailable(presentCard, presentColor)) {
                return card;
            }
        }
        return null;
    }

    /**
     * This is a method to choose the best color(most appeared in bot's cards) for wild color changer or wild draw4 card
     *
     * @return
     */
    public String getBestColor() {
        int reds = 0;
        int blues = 0;
        int greens = 0;
        int yellows = 0;
        for (Card card : myCards) {
            switch (card.getColor()) {
                case "red":
                    reds++;
                    break;
                case "blue":
                    blues++;
                    break;
                case "yellow":
                    yellows++;
                    break;
                case "green":
                    greens++;
                    break;
            }
        }
        Integer[] colors = {reds, blues, greens, yellows};
        int maximum = Collections.max(Arrays.asList(colors));
        if (maximum == reds) {
            return "red";
        } else if (maximum == blues) {
            return "blue";
        } else if (maximum == yellows) {
            return "yellow";
        } else if (maximum == greens) {
            return "green";
        }
        return "";
    }
}
