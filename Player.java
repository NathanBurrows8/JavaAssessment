/**
 *
 *
 * Author: Nathan Burrows
 * File: Player.java
 *
 */

package assessment;
public class Player extends Thread{

    private int amountPlaced;
    private int playerNum;

    public int getAmountPlaced(){               //allows multiplayer scores at the end
        return amountPlaced;
    }
    public Player(int playerNum){           //Player constructor allows us to have a player number so we can keep track of which thread is which
        this.playerNum = playerNum;
        amountPlaced = 0;
    }

    private synchronized boolean checkCard(Card c) {
        if (GameBoard.getSize() == 0){                          //multiple checks in a small but synchronized method
            return true;                                        //if nobody has played yet, you are free to play
        }
        for (int i =0; i < GameBoard.getSize(); i++)
        {
            Card comparison = GameBoard.cards.get(i);           //if card is already in the game (using previous compareTo), you cannot play it
            if (c.compareTo(comparison) == 0)
                return false;
        }

        return true;
    }
    private void addCard(Card c) {

        if (!GameBoard.isFull()) {
            GameBoard.cards.add(c);                                                         // adds the card specified if gameBoard not full
            amountPlaced++;                                                                 //increments that threads total
        }
        GameBoard.end = true;
    }


    public void run() {
        Deck deckThread = new Deck();
        deckThread.fillDeck();                                  //creating a new deck per each thread
        deckThread.shuffle();                                   //found it more interesting if each deck was shuffled also!
        Deck.DealIterator dealIterator = deckThread.new DealIterator();
        while (!GameBoard.isFull()) {         //while the game can still be played....
            synchronized (GameBoard.cards) {                        //obtaining lock on cards to proceed
                Card cardToAdd = dealIterator.next();               //seeing what card is next to be drawn and dealt from the iterator from Deck.java
                boolean add = checkCard(cardToAdd);                 //checking to see if this card is valid to be played
                if (add) {
                    addCard(cardToAdd);                             //adding it if it is
                }
            }
            try {
                sleep(50);                                  //making complete sure no race conditions are reached
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            synchronized (GameBoard.cards) {

                GameBoard.totalAmount.add(getAmountPlaced());       //adding score of this thread into final scores
            }
        }
}
