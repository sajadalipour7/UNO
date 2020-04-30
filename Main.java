import java.util.*;

/**
 * In the name of god
 * <p>
 * --------------------------
 * UNO Game
 * --------------------------
 * This Project is the UNO game in computer
 * It includes Main,GameHandler,Board,Card,Player,BotPlayer,HumanPlayer Classes
 * <p>
 * This class is the driver of project
 *
 * @author MohammadSajad Alipour
 * @version 1.0    2020
 */
public class Main {
    /**
     * Driver or main Function
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //A Menu infinity loop
        do {
            //Creating a gameHandler to handle the game
            GameHandler gameHandler = new GameHandler();
            gameHandler.showMenu();
            int mode = sc.nextInt();
            switch (mode) {
                //SinglePlayer mode
                case 1:
                    gameHandler.showNumberOfPlayersMenu();
                    int numOfPlayersInSinglePlayer = sc.nextInt();
                    while (numOfPlayersInSinglePlayer != 3 && numOfPlayersInSinglePlayer != 4 && numOfPlayersInSinglePlayer != 5) {
                        System.out.println("Invalid input!");
                        numOfPlayersInSinglePlayer = sc.nextInt();
                    }
                    gameHandler.setAndVerifyNumOfPlayers(numOfPlayersInSinglePlayer);
                    gameHandler.playSinglePlayer();
                    break;
                //MultiPlayer mode
                case 2:
                    gameHandler.showNumberOfPlayersMenu();
                    int numOfPlayers = sc.nextInt();
                    while (numOfPlayers != 3 && numOfPlayers != 4 && numOfPlayers != 5) {
                        System.out.println("Invalid input!");
                        numOfPlayers = sc.nextInt();
                    }
                    gameHandler.setAndVerifyNumOfPlayers(numOfPlayers);
                    gameHandler.playMultiPlayer();
                    break;
                //Exit mode
                case 3:
                    return;
                //handling Wrong or invalid input
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        } while (true);
    }
}
