package we.games;

import java.util.ArrayList;


public class PokerPlayer{

  private int chips;
  //private int blind;
  private int playerBet;
  private boolean isPlaying; //keeps track of if a player has folded
  
  String name;
  ArrayList<Card> hand;

    public PokerPlayer(int chips, String name){
      this.chips = chips;
      this.name = name;
      hand = new ArrayList<Card>();
    }

    public PokerPlayer(String name){
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

    public ArrayList<Card> getHand(){
        return hand;
    }

    public boolean isPlaying(){
        return isPlaying;
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
            //hand.set(j+1, currentValue);
            hand.set(j+1, hand.get(currentValue)); //is this what its meant to be?
        }
    }

  @Override
  public boolean equals(Object obj){
    PokerPlayer p = (PokerPlayer)obj;

    if(p.getName().equals(this.getName())){
      return true;
    }
    return false;
  }

}