
package we.games;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{
  private ArrayList<Card> cards = new ArrayList<Card>();
  private Card tempCard;

  /**
    Creates a deck of 52 cards
  */
  public Deck(){
    for(int i = 1; i <= 4; i++){
      for(int j = 2; j <= 14; j++){ //comment this to store an ace as a 14
      //for(int j = 1; j <= 13; j++){ //uncomment this to store an ace as a 1
        tempCard = new Card(i, j);
        cards.add(tempCard);
      }
    }
  }

  /**
    Deal a card from a random location in the deck, removing it from the deck

    Returns: A Card object
  */
  public Card dealRandomCard(){
    int index = (int)(cards.size()*Math.random());
    tempCard = cards.get(index);
    cards.remove(cards.get(index));

    return tempCard;
  }

  /**
    Deal a card from the top of the deck (index 0), removing it from the deck

    Returns: A Card object
  */
  public Card dealTopCard(){
    tempCard = cards.get(0);
    cards.remove(0);

    return tempCard;
  }

  /**
    Randomizes the location of every card in the deck
  */
  public void shuffle(){
    Collections.shuffle(cards);
  }

  /**
    Checks if the deck has no cards in it
    
    Returns: a boolean, true if the deck has 0 cards, false otherwise
  */
  public boolean isEmpty(){
    return cards.isEmpty();
  }
}