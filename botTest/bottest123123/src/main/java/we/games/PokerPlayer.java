package we.games;

import java.util.ArrayList;


public class PokerPlayer{

  /**
   * the amount of chips the player has
   */
  private int chips;
  //private int blind;
  /**
   * the bet the player has posted this round of betting
   */
  private int playerBet;

  /**
   * keeps track of if a player is playing
   */
  private boolean isPlaying;
  
  /**
   * the player's name as a string
   */
  String name;

  /**
   * the player's hand as an ArrayList of Cards
   */
  ArrayList<Card> hand;

  /**
   * Creates a player with chips chips and a name of name
   * @param chips -- the amount of chips the player starts with
   * @param name -- the name of the player
   */
    public PokerPlayer(int chips, String name){
      this.chips = chips;
      this.name = name;
      hand = new ArrayList<Card>();
    }

    /**
     * Creates an empty player with a specified name
     * 
     * @param name -- the name of the player
     * @see #equals(Object)
     */
    public PokerPlayer(String name){
      this.name = name;
    }

    /**
     * reduces a player's chips by bet
     * 
     * @param bet -- the amount to reduce the player's chips by
     * @see 
     */
    public void placeBet (int bet) {
      chips -= bet;
    }

    /**
     * Returns the number of chips a player has
     * 
     * @return {@code chips} -- the number of chips the player has
     */
    public int getChips(){
      return chips;
    }

    /**
     * Sets a player's chips to c
     * 
     * @param c -- the number of chips to set the player's chips to
     * @see #placeBet(Integer)
     */
    public void setChips(int c){
      chips = c;
    }

    /**
     * Returns the total bet a player has made this round
     * 
     * @return {@code playerBet} -- the total the player has bet this round of betting
     */
    public int getPlayerBet(){
      return playerBet;
    }

    /**
     * sets the player's bet to b
     * 
     * @param b -- the player's bet
     */
    public void setPlayerBet(int b){
      playerBet = b;
    }

    /**
     * Returns the name of the player
     * 
     * @return {@code name} -- the name of the player
     */
    public String getName(){
      return name;
    }

    /**
     * Returns the hand as an ArrayList of Cards
     * 
     * @return {@code hand} -- the player's hand
     */
    public ArrayList<Card> getHand(){
      return hand;
    }

    /**
     * Checks if the player is playing
     * 
     * @return {@code true} if the player is playing, {@code false} otherwise
     */
    public boolean isPlaying(){
      return isPlaying;
    }

    /**
     * Sets the status of if the player is playing to b
     * 
     * @param b -- whether or not the player is playing
     */
    public void setIfPlaying(boolean b){
      isPlaying = b;
    }

    /**
     * Sorts the player's hand from highest to lowest by face value
     */
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