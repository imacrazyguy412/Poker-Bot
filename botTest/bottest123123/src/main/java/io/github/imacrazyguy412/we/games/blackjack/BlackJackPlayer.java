package io.github.imacrazyguy412.we.games.blackjack;

import io.github.imacrazyguy412.we.games.util.*;

public class BlackJackPlayer extends Player{
  private int bet;
  private int score;
  private boolean isBust, isStood, hasJustJoined;
  private Hand hand = new Hand();
  private Hand splitHand = new Hand();
  private int splitHandScore;
  private boolean splitHandIsBust, splitHandIsStood;

  /**
   * Constructs an emtpy BlackJackPlayer. Used to make the dealer
   */
  public BlackJackPlayer(){
    super(0, null);
  }

  /**
   * Creats a BlackJackPlayer with the name n
   * 
   * @param n -- the name of the player as a String
   */
  public BlackJackPlayer(String n){
    super(1000, n);
  }

  /**
   * Creates a BlackJackPlayer with the name n and join status j
   * @param n -- the name of the player as a String
   * @param j -- whether or not the player hasJustJoined
   */
  public BlackJackPlayer(String n, boolean j){
    super(1000, n);
    hasJustJoined = j;
  }

  /**
   * Returns a specific card in a player's hand
   * 
   * @param i -- the index of the card to be returned
   * @return {@code Card} -- the specified card in the player's hand
   */
  public Card getCard(int i){
    return hand.get(i);
  }

  /**
   * Adds a card to a player's hand and adjusts the value of their hand accordingly
   * 
   * @param newCard -- the card to be added
   * @see #addCardSplit(Card)
   */
  public void addCard(Card newCard){
    hand.add(newCard);
    score = calcScore(hand);

    if(score > 21){
      isBust = true;
    }
    if(score == 21){
      isStood = true;
    } //a player automatically stands on 21
  }

  /**
   * Takes the second card of the hand and moves it to a new splitHand
   * 
   * @see #addCardSplit(Card)
   * @see #splitHandIsPlaying()
   * @see #splitStand()
   * @see #getSplitHand()
   * @see #getSplitScore()
   */
  public void split(){
    splitHand.add(hand.get(1));
    hand.remove(1);
  }

  /**
   * Adds a card to a player's splitHand and adjusts the value of their hand accordingly
   * 
   * @param newCard -- the card to be added to the split hand
   * @see #split()
   * @see #addCard(Card)
   */
  public void addCardSplit(Card newCard){
    splitHand.add(newCard);
    splitHandScore = calcScore(splitHand);

    if(splitHandScore > 21){
      splitHandIsBust = true;
    }
    if(splitHandScore == 21){
      splitHandIsStood = true;
    } //a player automatically stands on 21
  }

  /**
   * Sets a player's isStood status to true
   * 
   * @see #splitStand()
   * @see #isStood
   */
  public void stand(){
    isStood = true;
  }

  /**
   * Checks if a player's main hand is still playing
   * 
   * @return {@code true} if the player has not stood or bust, {@code false} otherwise
   */
  public boolean isPlaying(){
    if(isBust || isStood){
      return false;
    }
    return true;
  }

  /**
   * Sets a player's splitHandIsStood status to true
   * 
   * @see #stand()
   * @see #splitHandIsStood
   */
  public void splitStand(){
    splitHandIsStood = true;
  }

  /**
   * Checks if a player's split hand is still playing
   * 
   * @return {@code true} if the player has not stood or bust and has at least one card in their split hand, {@code false} otherwise
   */
  public boolean splitHandIsPlaying(){
    if(splitHandIsBust || splitHandIsStood || (splitHand.size() == 0)){
      return false;
    }
    return true;
    //if the size of splitHand is null (there are no cards) the program will return false in addition to if the split hand is stood or bust
  }

  /**
   * Sets the player's bet to b
   * 
   * @param b -- the amount to be bet
   * @see #bet
   * @see #getBet()
   * @see #loseBet()
   * @see #payBet()
   * @see #resetBet()
   */
  public void bet(int b){
    bet = b;
  }

  /**
   * Returns the value of the player's hand
   * 
   * @return {@code score} -- the value of the player's main hand
   * @see #getSplitScore()
   */
  public int getScore(){
    return score;
  }


  /**
   * Returns the value of the player's split hand
   * 
   * @return {@code splitHandScore} -- the value of the player's main hand
   * @see #getSplitScore()
   */
  public int getSplitScore(){
    return splitHandScore;
  }

  /**
   * Returns the player's hand
   * 
   * @return {@code hand} -- the player's hand as an ArrayList of Cards
   */
  public Hand getHand(){
    return hand;
  }

  /**
   * Returns the player's split hand
   * 
   * @return {@code splitHand} -- the player's split hand as an ArrayList of Cards
   * @see #split()
   * @see #addCardSplit(Card)
   * @see #splitHandIsPlaying()
   * @see #splitStand()
   * @see #getSplitHand()
   * @see #getSplitScore()
   */
  public Hand getSplitHand(){
    return splitHand;
  }

  /**
   * Returns the player's bet
   * 
   * @return {@code bet} -- the player's bet
   */
  public int getBet(){
    return bet;
  }

  /**
   * Increases the player's chips by their bet
   */
  public void payBet(){
    chips += bet;
  }

  /**
   * Increases the player's chips by their bet multiplied by a given multipier
   * @param mult the multiplier to influence the payout by
   */
  public void payBet(float mult){
    chips += (int)(bet * mult);
  }

  /**
   * Increases the player's chips by their bet multiplied by a given multipier
   * @param mult the multiplier to influence the payout by
   */
  public void payBet(double mult){
    chips += (int)(bet * mult);
  }

  /**
   * Decreases the player's chips by their bet
   */
  public void loseBet(){
    chips -= bet;
  }

  /**
   * Resets bet without changing chips
   */
  public void resetBet(){
    bet = 0;
  }

  /**
   * calculates the value of a hand
   * 
   * @param hand -- the hand to be calculated
   * @return the value of the hand
   */
  private static int calcScore(Hand hand){
    int aces = 0;
    int handScore = 0;

    for(int i = 0; i < hand.size(); i++){
      if(hand.get(i).face == 14 || hand.get(i).face == 1){
        aces++;
        handScore += 1;
      } else if(hand.get(i).face >= 10){
        handScore += 10;
      } else{
        handScore += hand.get(i).face;
      }
    }
    
    for(int i = 0; i < aces; i++){
      if(handScore <= 11){
        handScore += 10;
      }
    }

    return handScore;
  }

  /**
   * Removes all cards from the player's hand
   */
  public void clearHand(){
    hand.clear();
    splitHand.clear();

    isBust = false;
    isStood = false;
    splitHandIsBust = false;
    splitHandIsStood = false;
  }

  /**
   * Checks if the player has joined before the current round
   * 
   * @return {@code hasJustJoined} -- whether or not the player has joined during the las round
   */
  public boolean hasJustJoined(){
    return hasJustJoined;
  }

  /**
   * Sets the status of when the player has joined
   * 
   * @param j -- what to set the status to as a {@code boolean}
   */
  public void setJoined(boolean j){
    hasJustJoined = j;
  }
}