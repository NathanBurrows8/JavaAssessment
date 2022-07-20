# JavaAssessment
For Programming Second Year Module
An Object-Oriented set of questions that also includes enums, nested classes, generics, lambdas, comparators, serialisation, and threading.

Card.java: A class that defines a Card object. Contains Rank and Suit data, and are Comparable. 

Deck.java: A class that defines a Deck of Card objects. Methods include filling the deck, contains/shuffle/add, and iterating through a Deck.

GameBoard: A simple threaded game utilising Card and Deck, where players attempt to place down cards. Everyone starts with a full deck. Players take turns to draw a card, and attempt to place it down to a shared board. They can only place a card if has not already been placed.  The winner has the least amount of cards at the end.

Player: An class to define a Player object, used in the GameBoard game. 
