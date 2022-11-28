package we.games;

import java.util.ArrayList;


public class PokerPlayer{

  int chips;
  int blind;
  int playerBet;
  boolean isPlaying; //keeps track of if a player has folded
  boolean hasBet; //keeps track of if a player is eligable to bet
  
  String name;
  ArrayList<Card> hand;

    public PokerPlayer(int chips, String name){
      this.chips = chips;
      this.name = name;
      hand = new ArrayList<Card>();
    }

    public void placeBet (int bet) {
        chips -= bet;
    }


    public int getChips(){
        return chips;
    }

    public void setChips(int c){
        chips = c;
    }

    public int getPlayerBet(){
        return playerBet;
    }

    public void setPlayerBet(int b){
        playerBet = b;
    }

    public String getName(){
        return name;
    }

    //should sort the hand of the player from highest to lowest i think?
    //idk I didn't write it and I am kinda dumb
    public void sortHand(){
        for(int i = 1; i<hand.size(); i++){
            int currentValue = hand.get(i).getFace();
            int j = i-1;
            while(j>=0 && hand.get(j).getFace() > currentValue){ //I added a .getFace() in this line
                hand.set(j+1, hand.get(j));
                j--;
            }
            hand.set(j+1, currentValue);
        }
    }

}