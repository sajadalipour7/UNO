import java.util.ArrayList;

/**
 * This is a class for Human Player and it's details.
 * Also this class inherits from Player class.
 */
public class HumanPlayer extends Player {
    private String name = getName();
    private ArrayList<Card> myCards = getMyCards();

    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * This is a method to show player's cards in a user friendly way
     */
    public void showMyCards() {
        final String ANSI_RESET = "\u001B[0m";
        int cardID = 1;
        for (Card card : myCards) {
            System.out.print("\u001B[35m" + "  Card ID: " + cardID + ANSI_RESET + "    ");
            cardID++;
        }
        System.out.println();
        for (Card card : myCards) {
            System.out.print(detectColor(card.getColor()) + "#------------#" + ANSI_RESET + "  ");
        }
        System.out.println();
        for (int i = 0; i < 2; i++) {
            for (Card card : myCards) {
                System.out.print(detectColor(card.getColor()) + "|            |" + ANSI_RESET + "  ");
            }
            System.out.println();
        }

        for (Card card : myCards) {
            String cardInfo = "";
            switch (card.getCardMode()) {
                case "Numeric":
                    cardInfo = "     " + card.getScore() + "      ";
                    break;
                case "Skip":
                    cardInfo = "    Skip    ";
                    break;
                case "Draw2":
                    cardInfo = "     +2     ";
                    break;
                case "Reverse":
                    cardInfo = "  Reverse   ";
                    break;
                case "WildDraw4":
                    cardInfo = "     +4     ";
                    break;
                case "WildColorChanger":
                    cardInfo = " ChangeColor";
            }
            System.out.print(detectColor(card.getColor()) + "|" + cardInfo + "|" + ANSI_RESET + "  ");
        }
        System.out.println();
        for (int i = 0; i < 2; i++) {
            for (Card card : myCards) {
                System.out.print(detectColor(card.getColor()) + "|            |" + ANSI_RESET + "  ");
            }
            System.out.println();
        }
        for (Card card : myCards) {
            System.out.print(detectColor(card.getColor()) + "#------------#" + ANSI_RESET + "  ");
        }
        System.out.println();
    }

    /**
     * This is a method to get ANSI code of a color
     *
     * @param color
     * @return
     */
    private String detectColor(String color) {
        final String ANSI_WHITE = "\u001B[37m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        switch (color) {
            case "red":
                return ANSI_RED;
            case "blue":
                return ANSI_BLUE;
            case "yellow":
                return ANSI_YELLOW;
            case "green":
                return ANSI_GREEN;
            default:
                return ANSI_WHITE;
        }
    }
}
