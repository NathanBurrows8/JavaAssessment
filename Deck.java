/**
 *
 *
 * Author: Nathan Burrows
 * File: Deck.java
 *
 */

package assessment;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Deck implements Serializable,Iterable<Card> {
    private final Card[] cards;

    protected Deck() {
        this.cards = new Card[52];                          //deck is created, with an empty array of size 52
    }

    protected void fillDeck() {
        int i = 0;
        for (Card.Suit s : Card.Suit.values()) {            //populates array with every card in a standard deck
            for (Card.Rank r : Card.Rank.values()) {
                cards[i] = new Card(r, s);
                i++;
            }
        }
    }

    private int size() {
        int size = 0;
        for (int i = 0; i < cards.length; i++) {            //returns number of cards in deck
            if (cards[i] != null) {
                size++;
            }
        }
        return size;
    }

    @Override
    public Iterator<Card> iterator() {
        return new CardsRemainingIterator();        //iterable, using a CardsRemainingIterator, below:
    }


    public class CardsRemainingIterator implements Iterator<Card> {
        private int position = 0;

        @Override
        public boolean hasNext() {              //standard implementation of methods
            return position < cards.length;
        }

        @Override
        public Card next() {
            return cards[position++];
        }
        public int getPosition() {
            return position;
        }
    }

    public class DealIterator implements Iterator<Card> {
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < cards.length;
        }
        public int getPosition() {
            return position;
        }
        @Override
        public Card next() {                    //here it 'deletes' the card after it has been dealt
            Card cardReturned = cards[position];
            cards[position] = null;
            position++;
            return cardReturned;
        }
    }
    protected void shuffle(){
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--){     //looping backwards through deck
            int position = random.nextInt(i+1); //since upper bound is exclusive, need to +1 here
            Card card = cards[position];    //finding random card
            cards[position] = cards[i];     //exchanging position of loop and random card
            cards[i] = card;
        }
    }
    private boolean contains(Card c) {
        for (Card card : cards) {           //overriding contains, to see if two cards are equal
            if (card == null) {
                continue;
            }
            if (c.compareTo(card) == 0) {
                return true;
            }
        }
        return false;
    }
    private boolean add(Card c) {
        if ((!contains(c)) && (size() < 52)){       //making sure there is space in the deck to add, and that it is not a duplicate
            int position = 0;
            for (Card card : cards) {
                if (card == null) {                 //will add wherever there is an empty space - so can be used AFTER cards are dealt(<--AKA deleted).
                    cards[position] = c;
                    return true;
                }
                position++;
            }
        }

        return false;
    }
    public static void saveObject(Deck d, String filename) {
        try {
            FileOutputStream f = new FileOutputStream(filename);        //same method as before for serializing
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(d);
            o.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        catch (IOException e){
            System.out.println("Error in writing object.");
        }
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        d.fillDeck();                               //first creating a deck then populating it in new deck order.
        for (Card c : d) {
            System.out.println(c);                                  //printing out in new deck order
        }
        ArrayList<Card> hand1 = new ArrayList<>();
        ArrayList<Card> hand2 = new ArrayList<>();
        DealIterator dealIterator = d.new DealIterator();              //making 2 hands and an iterator
        while (dealIterator.getPosition() < 20) {
            hand1.add(dealIterator.next());
            hand2.add(dealIterator.next());                         //dealing to them both consecutively for the first 20 cards
        }
        System.out.println(hand1);                                  //should print out card1, card3, card5, etc...
        System.out.println(hand2);                                  //should print out card2, card4, card6, etc...
        System.out.println(d.size());                               //should print out 32
        d.shuffle();                                                //shuffling remaining cards
        CardsRemainingIterator remainingIterator = d.new CardsRemainingIterator();
        while (remainingIterator.hasNext()){
            Card c = remainingIterator.next();
            if (c !=null) {
                System.out.println(c);                              //printing remaining cards after being shuffled using iterator
            }
        }
        System.out.println(d.contains(new Card(Card.Rank.ACE, Card.Suit.SPADES)));  //does deck contain Ace of Spades? - should return true
        System.out.println(d.contains(new Card(Card.Rank.TWO, Card.Suit.CLUBS)));   //does deck contain Two of Clubs? - should return false
        System.out.println(d.add(new Card(Card.Rank.ACE, Card.Suit.SPADES)));       //try to add in Ace of Spaces - should return false
        System.out.println(d.add(new Card(Card.Rank.TWO, Card.Suit.CLUBS)));        //try to add in Two of Clubs - should return true
        d.fillDeck();                                   //repopulating deck again
        System.out.println(d.size());                   //should print 52
    }
}