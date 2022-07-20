/**
 *
 *
 * Author: Nathan Burrows
 * File: GameBoard.java
 *
 */

package assessment;
import java.util.ArrayList;

public class GameBoard {
    protected static boolean end=false;
    protected static final ArrayList<Card> cards=new ArrayList<>();
    protected static ArrayList<Integer> totalAmount = new ArrayList<>();
    protected static boolean isFull() {
        return cards.size() ==52;                                       //checking to see if deck is full (no duplicates are allowed from Player)
    }
    protected static int getSize() {
        return cards.size();
    }               //getter for size of deck
    private void singlePlayer() {
        System.out.println("Singleplayer started!");
        Player player1 = new Player(1);                     //single player method - this creates one thread and plays the game.
        player1.start();                                                //no print statement really necessary for a 1 player game
        System.out.println("No print statement really necessary for a 1 player game");
        System.out.println("Singleplayer finished!\n");
    }
    private void multiPlayer(int numOfPlayers) {                        //multiplayer method - this creates however many threads are specified
        Player[] player = new Player[6];                                                //array of players - allows us to join(), and print things like final scores
        for (int i = 1; i < numOfPlayers; i++) {
            player[i] = new Player(i + 1);                   //creates N number of threads and starts the game, multiplayer
            player[i].start();
        }
        for (int i = 1; i < numOfPlayers; i++) {
            try {
                player[i].join();                                              //joining the threads

            } catch (InterruptedException e) {
                System.out.println("Thread could not be joined");
            }
        }
        int index = 0;
        int toBeat = 0;
        int total = 0;
        System.out.println("Final Multiplayer Scores:");            //adding up scores for each thread
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println("Player " + (i+1) + " placed " + totalAmount.get(i) + " cards, so had " + (52-totalAmount.get(i)) + " cards remaining.") ;
            if (totalAmount.get(i) > toBeat){
                toBeat = totalAmount.get(i);                            //seeing which score was highest
                index = i;
            }
            total = total + totalAmount.get(i);
        }
        int count = 0;
        for (int i = 0; i < numOfPlayers; i++){
            if(totalAmount.get(i) == toBeat){                       //checking to see if there was a draw
                count++;
            }
        }
        System.out.println("------------------");
        if (count < 2) {
            System.out.println("Player " + (index + 1) + " wins!");
        }
        else {
            System.out.println("Its a draw!");
        }
    }

    public static void main(String[] args) {
        GameBoard game = new GameBoard();
        game.singlePlayer();
        game.multiPlayer(5);
    }

}
