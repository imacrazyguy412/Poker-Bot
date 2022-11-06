
package we.games;

import java.util.ArrayList;
import java.util.Collections;

public class Deck{
  private ArrayList<Card> cards = new ArrayList<Card>();
  private Card tempCard;

  public Deck(){
    for(int i = 1; i <= 4; i++){
      for(int j = 2; j <= 14; j++){ //currently stores ace as 14
      //for(int j = 1; j <= 13; j++){ //uncomment this to store an ace as a 1
        tempCard = new Card(i, j);
        cards.add(tempCard);
      }
    }
  }

  public Card dealRandomCard(){
    int index = (int)(cards.size()*Math.random());
    tempCard = cards.get(index);
    cards.remove(cards.get(index));

    return tempCard;
  }

  public Card dealTopCard(){
    tempCard = cards.get(0);
    cards.remove(0);

    return tempCard;
  }

  public void shuffle(){
    Collections.shuffle(cards);
  }

  public boolean isEmpty(){
    return cards.isEmpty();
  }
}