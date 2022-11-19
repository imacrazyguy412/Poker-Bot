/* 
package we.games;

import java.util.ArrayList;

public class Hand{
  private ArrayList<Card> cards;
  
  public Hand(){
    cards = new ArrayList<Card>();
  }
  
  public Hand(ArrayList<Card> h){
    cards = new ArrayList<Card>();
    
    for(int i = 0; i < h.size(); i++){
      cards.add(h.get(i));
    }
    //Im not sure if I need this for loop, but Im gonna do it anyway
  }
  
  public ArrayList<Card> getCards(){
    return cards;
  }
  
  public Card getCard(int index){
    return cards.get(index);
  }


  //TODO: make method static so it sorts array then put into constructor
  public void sort(ArrayList<Card> h){
    //add sorting algorithim
    
    for(int i = 1; i<h.size(); i++){
    int currentValue = h.get(i).getFace();
    int j = i-1;
      while(j>=0 && h.get(j) > currentValue){
        h.set(j+1, h.get(j));
        j--;
      }
      h.set(j+1, currentValue);
    }
    
  }


}
*/
