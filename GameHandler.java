import java.util.Scanner;

/**
 * This is a class for handling the game and core
 * This class controls and handles the core of the game
 */
public class GameHandler {
    private int playersNum;
    private Player[] players;
    private Board board = new Board();
    private int playerTurn = 0;
    private int rotationDirection = 1;
    private Card presentCard;
    private String presentColor = "";
    private Scanner sc = new Scanner(System.in);
    private boolean firstTime = true;
    private int checkForDraw2Chaining = 0;
    private int checkForWildDraw4Chaining = 0;


    public GameHandler() {
    }

    /**
     * This is a method to set number of players
     *
     * @param playersNum
     */
    public void setAndVerifyNumOfPlayers(int playersNum) {
        this.playersNum = playersNum;
        players = new Player[playersNum];
    }

    /**
     * This is a method to create players in multi player mode
     */
    private void initializePlayers() {
        for (int i = 0; i < playersNum; i++) {
            String name = "Player" + (i + 1);
//            System.out.println("Enter your name");
//            name=sc.next();
            players[i] = new HumanPlayer(name);
        }
    }

    /**
     * This is a method to create player and bot players in single player mode
     */
    private void initializeBotsAndPlayer() {
        String playerName;
        //playerName = "Player";
        System.out.println("Enter your name");
        playerName = sc.next();
        players[0] = new HumanPlayer(playerName);
        for (int i = 1; i < playersNum; i++) {
            players[i] = new BotPlayer("BotPlayer" + i);
        }
    }

    /**
     * This is a method to initialize first of the game(setting first card and giving 7 cards to players and etc)
     */
    private void initialization() {
        for (int i = 0; i < playersNum; i++) {
            for (int j = 0; j < 7; j++) {
                players[i].addCardToMyCards(board.giveMeACardForPlayer());
            }
        }
        presentCard = board.getPresentCard();
        //handling special cases
        while (presentCard.getCardMode().equals("WildDraw4") || presentCard.getCardMode().equals("WildColorChanger")) {
            board.shuffleThePresentCardToTheEnd();
            presentCard = board.getPresentCard();
        }
        presentColor = presentCard.getColor();
        //handling special cases
        if (presentCard.getCardMode().equals("Skip")) {
            nextTurn();
        }
        //handling special cases
        if (presentCard.getCardMode().equals("Reverse")) {
            rotationDirection *= -1;
            nextTurn();
        }
        //handling special cases
        if (presentCard.getCardMode().equals("Draw2")) {
            board.shuffleThePresentCardToTheEnd();
            players[playerTurn].addCardToMyCards(board.giveMeACardForPlayer());
            players[playerTurn].addCardToMyCards(board.giveMeACardForPlayer());
            nextTurn();
        }
        System.out.println("*********************************************************************************");
        System.out.println();
    }

    /**
     * This is the play method of multi player mode
     * it controls the loop of the game
     */
    public void playMultiPlayer() {
        initializePlayers();
        initialization();
        do {
            HumanPlayer player = (HumanPlayer) players[playerTurn];
            showSituation(players[playerTurn]);
            presentCard.print("normal");
            player.showMyCards();
            playTurnForMultiPlayer(player);
        } while (!endGame());
        printResult();
    }

    /**
     * This is the play method of single player mode
     * it controls the loop of the game
     */
    public void playSinglePlayer() {
        initializeBotsAndPlayer();
        initialization();
        do {
            showSituation(players[playerTurn]);
            presentCard.print("normal");
            playTurnForSinglePlayer(players[playerTurn]);
        } while (!endGame());
        printResult();
    }

    /**
     * This is a method to check if the game is finished
     *
     * @return
     */
    private boolean endGame() {
        if (checkIfAnyoneHasWon()) {
            return true;
        }
        return false;
    }

    /**
     * This is a method to check if any player has thrown all it's cards
     *
     * @return
     */
    private boolean checkIfAnyoneHasWon() {
        for (Player player : players) {
            if (player.getSizeOfCards() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * This is a method to show the results at the end of the game
     */
    private void printResult() {
        System.out.println("\n***************Game Finished!****************\n");
        Player winner = null;
        String winnerName = "";
        //Sorting players by their scores
        for (Player player : players) {
            if (player.getSizeOfCards() == 0) {
                winner = player;
                System.out.println(winner.getName() + " : " + winner.getScore() + " points!");
                winnerName = winner.getName();
                player.setName("!*#?1223wer&&");
                break;
            }
        }
        for (int i = 0; i < playersNum; i++) {
            int minimumScore = -1;
            for (Player player : players) {
                if (!player.getName().equals("!*#?1223wer&&")) {
                    minimumScore = player.getScore();
                    break;
                }
            }
            for (Player player : players) {
                if (!player.getName().equals("!*#?1223wer&&")) {
                    if (player.getScore() < minimumScore) {
                        minimumScore = player.getScore();
                    }
                }
            }
            for (Player player : players) {
                if (!player.getName().equals("!*#?1223wer&&")) {
                    if (player.getScore() == minimumScore) {
                        System.out.println(player.getName() + " : " + player.getScore() + " points!");
                        player.setName("!*#?1223wer&&");
                    }
                }
            }
        }
        System.out.println("**********************************************");
        System.out.println();
        System.out.println("\u001B[32m" + "The winner is " + "\u001B[0m" + winnerName);
        System.out.println();
        System.out.println("**********************************************");
        System.out.println();
        System.out.println("#####################################################################################################################");
    }

    /**
     * This is a method to show situation of other players and present color and present rotation direction
     *
     * @param presentPlayer
     */
    private void showSituation(Player presentPlayer) {
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        for (Player player : players) {
            if (!presentPlayer.equals(player)) {
                System.out.print(player.getName() + " : " + player.getSizeOfCards() + " cards            ");
            }
        }
        System.out.println();
        System.out.println();
        System.out.println(presentPlayer.getName() + " it's your turn!");
        String presentDirection = (rotationDirection == 1) ? "Clockwise" : "Anti-Clockwise";
        System.out.println("Present Color : " + presentColor + "                Present Direction : " + presentDirection);
        System.out.println();
    }

    /**
     * This is a method to handle player and bots moves in single player mode
     *
     * @param player
     */
    public void playTurnForSinglePlayer(Player player) {
        //checking if player is human
        if (player instanceof HumanPlayer) {
            ((HumanPlayer) player).showMyCards();
            playTurnForMultiPlayer((HumanPlayer) player);
        } else {
            //delaying on showing bots moves
            waitFunction();
            //checking if player can't throw anything
            if (player.allCardsNotAvailable(presentCard, presentColor)) {
                Card getCard = board.giveMeACardForPlayer();
                player.addCardToMyCards(getCard);
                //check if even now player can't throw
                if (player.allCardsNotAvailable(presentCard, presentColor)) {
                    System.out.println(player.getName() + " picked a new card!");
                    nextTurn();
                    return;
                }
                getCard.print("newCard");
                System.out.println(player.getName() + " used it's new card!");
                applyChanges(getCard);
                return;
            }
            //checking chaining cases
            if (presentCard.getCardMode().equals("Draw2") && checkForDraw2Chaining != 0) {
                System.out.println(player.getName() + " also used a draw2 card!");
                Card chosenCard = player.getDraw2Card();
                applyChanges(chosenCard);
                return;
            }
            //checking chaining cases
            if (presentCard.getCardMode().equals("WildDraw4") && checkForWildDraw4Chaining != 0) {
                System.out.println(player.getName() + " also used a WildDraw4 card!");
                Card chosenCard = player.getWildDraw4Card();
                applyChanges(chosenCard);
                return;
            }
            Card chosenCard = ((BotPlayer) player).getAvailableCard(presentCard, presentColor);
            //applying changes
            applyChanges(chosenCard);

        }

    }

    /**
     * This is a method to handle player moves in multi player mode
     *
     * @param player
     */
    public void playTurnForMultiPlayer(HumanPlayer player) {
        //checking if player can't throw anything
        if (player.allCardsNotAvailable(presentCard, presentColor)) {
            Card getCard = board.giveMeACardForPlayer();
            player.addCardToMyCards(getCard);
            //check if even now player can't throw
            if (player.allCardsNotAvailable(presentCard, presentColor)) {
                nextTurn();
                getCard.print("newCard");
                System.out.println("You can't also use this. so press enter to continue...");
                pressAnyKeyToContinue();
                return;
            }
            getCard.print("newCard");
            System.out.println("Press Enter to throw the card...");
            pressAnyKeyToContinue();
            applyChanges(getCard);
            return;

        }
        //checking chaining cases
        if (presentCard.getCardMode().equals("Draw2") && checkForDraw2Chaining != 0) {
            System.out.println("Press Enter to select Draw2 card...");
            pressAnyKeyToContinue();
            Card chosenCard = player.getDraw2Card();
            applyChanges(chosenCard);
            return;
        }
        //checking chaining cases
        if (presentCard.getCardMode().equals("WildDraw4") && checkForWildDraw4Chaining != 0) {
            System.out.println("Press Enter to select WildDraw4 card...");
            pressAnyKeyToContinue();
            Card chosenCard = player.getWildDraw4Card();
            applyChanges(chosenCard);
            return;
        }
        //Giving input for selecting card
        System.out.println("Please enter a Card ID ");
        int cardID = sc.nextInt() - 1;
        Card chosenCard = player.getCardByID(cardID);
        while (chosenCard == null || !chosenCard.isItAvailable(presentCard, presentColor) || (chosenCard.getCardMode().equals("WildDraw4") && player.checkIfPlayerCanThrowAnythingExceptWildDraw4(presentCard, presentColor))) {
            if (chosenCard == null) {
                System.out.println("Invalid ID!");
            } else {
                System.out.println("You can't use this card!");
            }
            System.out.println("Please enter a Card ID ");
            cardID = sc.nextInt() - 1;
            chosenCard = player.getCardByID(cardID);
        }
        //applying changes
        applyChanges(chosenCard);

    }

    /**
     * This is a method to apply changes in game
     *
     * @param cardToThrow
     */
    private void applyChanges(Card cardToThrow) {
        //adding the last card to the end of storage cards
        board.addCard(presentCard);
        //setting present color and present card
        presentColor = cardToThrow.getColor();
        presentCard = cardToThrow;
        //checking if player is bot
        if (players[playerTurn] instanceof BotPlayer) {
            presentCard.print("chosenCard");
        }
        //removing the thrown card from player's card
        players[playerTurn].removeCard(cardToThrow);

        //Special mode : It is for handling endGame when the last card is draw2 or WildDraw4 to stop chaining .
        if ((presentCard.getCardMode().equals("Draw2") || presentCard.getCardMode().equals("WildDraw4")) && players[playerTurn].getSizeOfCards() == 0) {
            return;
        }

        //if it is numeric
        if (cardToThrow.getCardMode().equals("Numeric")) {
            nextTurn();
            if (firstTime) {
                board.removePresentCard();
                firstTime = false;
                return;
            }
            //if it is skip
        } else if (cardToThrow.getCardMode().equals("Skip")) {
            nextTurn();
            nextTurn();
            if (firstTime) {
                board.removePresentCard();
                firstTime = false;
                return;
            }
            //if it is draw2
        } else if (cardToThrow.getCardMode().equals("Draw2")) {
            nextTurn();
            if (firstTime) {
                board.removePresentCard();
                firstTime = false;
            }
            //checking for chaining draw2
            if (players[playerTurn].hasDraw2Card()) {
                checkForDraw2Chaining += 2;
                if (players[playerTurn] instanceof HumanPlayer) {
                    HumanPlayer player = (HumanPlayer) players[playerTurn];
                    showSituation(players[playerTurn]);
                    presentCard.print("normal");
                    player.showMyCards();
                    playTurnForMultiPlayer(player);
                } else {
                    showSituation(players[playerTurn]);
                    presentCard.print("normal");
                    playTurnForSinglePlayer(players[playerTurn]);
                }
            } else {
                checkForDraw2Chaining += 2;
                for (int i = 0; i < checkForDraw2Chaining; i++) {
                    players[playerTurn].addCardToMyCards(board.giveMeACardForPlayer());
                }
                checkForDraw2Chaining = 0;
                nextTurn();
            }
            //if it is reverse
        } else if (cardToThrow.getCardMode().equals("Reverse")) {
            rotationDirection *= -1;
            nextTurn();
            if (firstTime) {
                board.removePresentCard();
                firstTime = false;
            }
            //if it is a wild card
        } else if (cardToThrow.getCardMode().equals("WildDraw4") || cardToThrow.getCardMode().equals("WildColorChanger")) {
            if (firstTime) {
                board.removePresentCard();
                firstTime = false;
            }
            if (players[playerTurn] instanceof HumanPlayer) {
                System.out.println("Change Color :");
                System.out.println("1)blue   2)red   3)green   4)yellow");
                int color;
                do {
                    color = sc.nextInt();
                    switch (color) {
                        case 1:
                            presentColor = "blue";
                            break;
                        case 2:
                            presentColor = "red";
                            break;
                        case 3:
                            presentColor = "green";
                            break;
                        case 4:
                            presentColor = "yellow";
                            break;
                        default:
                            System.out.println("Invalid Input!");
                            break;
                    }
                } while (color != 1 && color != 2 && color != 3 && color != 4);
            } else {
                String chosenColor = ((BotPlayer) players[playerTurn]).getBestColor();
                presentColor = chosenColor;
                System.out.println(players[playerTurn].getName() + " chose " + chosenColor + " color!");
            }
            nextTurn();
            if (cardToThrow.getCardMode().equals("WildDraw4")) {
                //checking for chaining WildDraw4
                if (players[playerTurn].hasWildDraw4Card()) {
                    checkForWildDraw4Chaining += 4;
                    if (players[playerTurn] instanceof HumanPlayer) {
                        HumanPlayer player = (HumanPlayer) players[playerTurn];
                        showSituation(players[playerTurn]);
                        presentCard.print("normal");
                        player.showMyCards();
                        playTurnForMultiPlayer(player);
                    } else {
                        showSituation(players[playerTurn]);
                        presentCard.print("normal");
                        playTurnForSinglePlayer(players[playerTurn]);
                    }

                } else {
                    checkForWildDraw4Chaining += 4;
                    for (int i = 0; i < checkForWildDraw4Chaining; i++) {
                        players[playerTurn].addCardToMyCards(board.giveMeACardForPlayer());
                    }
                    checkForWildDraw4Chaining = 0;
                    nextTurn();
                }
            }
            //for debugging purposes
        } else {
            return;
        }
    }


    /**
     * This is a method to changing turns into next player
     */
    private void nextTurn() {
        playerTurn = ((playerTurn + rotationDirection) % playersNum + playersNum) % playersNum;
    }

    /**
     * This is a method to changing turns into previous player
     * Used for debugging purposes
     */
    private void previousTurn() {
        playerTurn = ((playerTurn - rotationDirection) % playersNum + playersNum) % playersNum;
    }

    public int getRotationDirection() {
        return rotationDirection;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String getPresentColor() {
        return presentColor;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public void setPresentColor(String presentColor) {
        this.presentColor = presentColor;
    }

    /**
     * This is a method to show the menu in a proper way
     */
    public void showMenu() {
        System.out.println("***************************UNO Game***************************");
        System.out.println();
        System.out.println("1)Play Single player\n2)Play Multi player\n3)Exit");
    }

    /**
     * This is a method to show number of players menu options
     */
    public void showNumberOfPlayersMenu() {
        System.out.println("Please Enter Number of Players [3-5]");
    }

    /**
     * This is a method to wait until user presses enter key
     */
    private void pressAnyKeyToContinue() {
        //System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    /**
     * This is a method to delay for 2 seconds on showing bots moves
     */
    private void waitFunction() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
    }

}
