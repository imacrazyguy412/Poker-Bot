package we.games.poker;

import we.games.util.*;

public class PokerPlayer extends Player{
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
   * the player's hand as an ArrayList of Cards
   */
  private Hand hand;

  /**
   * Creates a player with chips chips and a name of name
   * @param chips -- the amount of chips the player starts with
   * @param name -- the name of the player
   */
    public PokerPlayer(int chips, String name){
      super(chips, name);
      hand = new Hand();
    }

    /**
     * Creates an empty player with a specified name
     * 
     * @param name -- the name of the player
     * @see #equals(Object)
     */
    public PokerPlayer(String name){
      super(0, name);
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
     * Returns the hand as an ArrayList of Cards
     * 
     * @return {@code hand} -- the player's hand
     */
    public Hand getHand(){
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
    public void setIsPlaying(boolean b){
      isPlaying = b;
    }

    /**
     * Sorts the player's hand from highest to lowest by face value
     */
    public void sortHand(){
      //for(int i = 1; i<hand.size(); i++){
      //  int currentValue = hand.get(i).getFace();
      //  int j = i-1;
      //  while(j>=0 && hand.get(j).getFace() > currentValue){ //I added a .getFace() in this line
      //    hand.set(j+1, hand.get(j));
      //    j--;
      //  }
      //  //hand.set(j+1, currentValue);
      //  hand.set(j+1, hand.get(j)); //is this what its meant to be?
      //}
      hand.sort();
    }

}