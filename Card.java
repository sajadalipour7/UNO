/**
 * This is a class for card details and attributes
 */
public class Card {
    private String cardMode;
    private String color;
    private int score;

    public Card(String cardMode, String color, int score) {
        this.cardMode = cardMode;
        this.color = color;
        this.score = score;
    }

    /**
     * This is a method to check if it is correct to throw this card on present card
     *
     * @param presentCard
     * @param presentColor
     * @return
     */
    public boolean isItAvailable(Card presentCard, String presentColor) {
        //Wild cards are always allowed (if they are verified by conditions in game handler)
        if (cardMode.equals("WildDraw4") || cardMode.equals("WildColorChanger")) {
            return true;
        }
        //If present card is wild draw 4
        if (presentCard.getCardMode().equals("WildDraw4")) {
            if (presentColor.equals(color)) {
                return true;
            } else {
                return false;
            }
            //If present card is wild color changer
        } else if (presentCard.getCardMode().equals("WildColorChanger")) {
            if (presentColor.equals(color)) {
                return true;
            } else {
                return false;
            }
            //If present card is Numeric
        } else if (presentCard.getCardMode().equals("Numeric")) {
            if (presentColor.equals(color) || presentCard.getScore() == score) {
                return true;
            } else {
                return false;
            }
            //If present card is draw 2
        } else if (presentCard.getCardMode().equals("Draw2")) {
            if (presentColor.equals(color) || presentCard.getCardMode().equals(cardMode)) {
                return true;
            } else {
                return false;
            }
            //If present card is skip
        } else if (presentCard.getCardMode().equals("Skip")) {
            if (presentColor.equals(color) || presentCard.getCardMode().equals(cardMode)) {
                return true;
            } else {
                return false;
            }
            //If present card is reverse
        } else if (presentCard.getCardMode().equals("Reverse")) {
            if (presentColor.equals(color) || presentCard.getCardMode().equals(cardMode)) {
                return true;
            } else {
                return false;
            }
            //for debugging purposes
        } else {
            return false;
        }
    }

    /**
     * This is a method to print a card in a user friendly way .
     *
     * @param printMode
     */
    public void print(String printMode) {
        final String ANSI_RESET = "\u001B[0m";
        String space = "                                                    ";
        if (printMode.equals("normal")) {
            System.out.println(space + " Present Card");
        } else if (printMode.equals("newCard")) {
            System.out.println(space + "Your new Card!");
        } else if (printMode.equals("chosenCard")) {
            System.out.println(space + "chosen card by bot!");
        }
        System.out.print(space);
        System.out.print(detectColor() + "#------------#" + ANSI_RESET + "  ");
        System.out.println();
        for (int i = 0; i < 2; i++) {
            System.out.print(space);
            System.out.print(detectColor() + "|            |" + ANSI_RESET + "  ");
            System.out.println();
        }
        String cardInfo = "";
        switch (cardMode) {
            case "Numeric":
                cardInfo = "     " + score + "      ";
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
        System.out.print(space);
        System.out.print(detectColor() + "|" + cardInfo + "|" + ANSI_RESET + "  ");
        System.out.println();
        for (int i = 0; i < 2; i++) {
            System.out.print(space);
            System.out.print(detectColor() + "|            |" + ANSI_RESET + "  ");

            System.out.println();
        }
        System.out.print(space);
        System.out.print(detectColor() + "#------------#" + ANSI_RESET + "  ");
        System.out.println();
        System.out.println();

    }

    public String getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public String getCardMode() {
        return cardMode;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCardMode(String cardMode) {
        this.cardMode = cardMode;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This is a method to get ANSI code of card's color
     *
     * @return
     */
    private String detectColor() {
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
