/**
 *
 *
 * Author: Nathan Burrows
 * File: Card.java
 *
 */

package assessment;

import java.io.*;
import java.util.Comparator;

public class Card implements Serializable,Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    @Override
    public int compareTo(Card o) {
        if (rank.getValue() == o.rank.getValue()) {                                 //comparing ranks, or suits if ranks are the same
            return Integer.compare(suit.getSuitValue(), o.suit.getSuitValue());
        }
        return Integer.compare(rank.getValue(), o.rank.getValue());
    }

    public enum Suit{CLUBS,DIAMONDS,HEARTS,SPADES;      //creating Suit enum with getters/setters
        private final int suitValue;
        Suit(){
            this.suitValue = ordinal();
        }
        public int getSuitValue() {
            return suitValue;
        }
    }
                                                                                                        //creating Rank enum with getters/setters
    public enum Rank{TWO, THREE, FOUR, FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE;
        private final int rankValue;
        Rank(){
            this.rankValue = ordinal()+2;
        }
        public int getValue() {
            return rankValue;
        }

    }
    public Card(Rank rank, Suit suit){          //Constructor for Card
        this.rank = rank;
        this.suit = suit;

    }
    @Override
    public String toString() {
        return rank + " of " + suit;       //overriding toString
    }
    private static void saveObject(Card c, String filename) {               //this method serializes an object
        try {
            FileOutputStream f = new FileOutputStream(filename);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(c);
            o.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        catch (IOException e){
            System.out.println("Error in writing object.");
        }
    }
    public static class CompareValue implements Comparator<Card> {         //compares cards just by value
        @Override
        public int compare(Card c1, Card c2){
            return Integer.compare(c1.rank.getValue(), c2.rank.getValue());
        }
    }
    public static void main(String[] args) {
        Card card1 = new Card(Rank.FIVE, Suit.DIAMONDS);        //creating test cards
        Card card2 = new Card(Rank.FIVE, Suit.SPADES);
        Card card3 = new Card(Rank.ACE, Suit.CLUBS);
        CompareValue compare1 = new CompareValue();
        Comparator<Card> compare2 =(a,b)->Integer.compare(a.suit.getSuitValue(), b.suit.getSuitValue());        //lambda to compare suits
        int[] compareToTest = new int[] {card1.compareTo(card2), card1.compareTo(card3), card2.compareTo(card3)};
        int[] comparatorTest = new int[] {compare1.compare(card1,card2), compare1.compare(card1,card3), compare1.compare(card2,card3)};
        int[] lambdaTest = new int[] {compare2.compare(card1,card2), compare2.compare(card1,card3), compare2.compare(card2,card3)};
        System.out.println(compareToTest[0]+","+compareToTest[1]+","+compareToTest[2]); // should give -1,-1,-1
        System.out.println(comparatorTest[0]+","+comparatorTest[1]+","+comparatorTest[2]); //should give 0,-1,-1
        System.out.println(lambdaTest[0]+","+lambdaTest[1]+","+lambdaTest[2]);          //should give -1,1,1
    }
}
