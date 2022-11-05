package we.arefarmers;

import java.util.ArrayList;


public class PokerPlayer{

  int chips;
  int blind;
  int playerBet;
  boolean isPlaying; //keeps track of if a player has folded
  boolean hasBet; //keeps track of if a player is eligable to bet
  
  String name;
  ArrayList<Card> hand = new ArrayList<Card>();

    public PokerPlayer(int chips, String name){
      this.chips = chips;
      this.name = name;
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

}