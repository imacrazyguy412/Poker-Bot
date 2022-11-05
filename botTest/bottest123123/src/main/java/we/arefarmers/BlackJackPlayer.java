package we.arefarmers;

import java.util.ArrayList;

public class BlackJackPlayer{
  private int points;
  private int bet;
  private int score;
  private boolean isBust, isStood;
  private ArrayList<Card> hand = new ArrayList<Card>();
  private ArrayList<Card> splitHand = new ArrayList<Card>();
  private int splitHandScore;
  private boolean splitHandIsBust, splitHandIsStood;
  private String name;

  public BlackJackPlayer(){
    //I need to have some sort of method defined for constructing the dealer, so this is empty
  }

  public BlackJackPlayer(String n){
    points = 1000;
    name = n;
  }

  public Card getCard(int i){
    return hand.get(i);
  }

  /**
  gives a player a card and adjusts their score accordingly. If they bust or get to 21, they stop playing.
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

  public void split(){
    splitHand.add(hand.get(1));
    hand.remove(1);
  }

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

  public void stand(){
    isStood = true;
  }

  public boolean isPlaying(){
    if(isBust || isStood){
      return false;
    }
    return true;
  }

  public void splitStand(){
    splitHandIsStood = true;
  }

  public boolean splitHandIsPlaying(){
    if(splitHandIsBust || splitHandIsStood || (splitHand.size() == 0)){
      return false;
    }
    return true;
    //if the size of splitHand is null (there are no cards) the program will return false in addition to if the split hand is stood or bust
  }

  public void bet(int b){
    bet = b;
  }

  public int getScore(){
    return score;
  }

  public int getSplitScore(){
    return splitHandScore;
  }

  public int getPoints(){
    return points;
  }

  public ArrayList<Card> getHand(){
    return hand;
  }

  public ArrayList<Card> getSplitHand(){
    return splitHand;
  }

  public String getName(){
    return name;
  }

  public int getBet(){
    return bet;
  }

  /**
  these methods either increase or decrease the player's points based on their bet
  */
  public void payBet(){
    points += bet;
  }

  public void loseBet(){
    points -= bet;
  }

  /**
  resets bet without changing points
  */
  public void resetBet(){
    bet = 0;
  }

  public void blackJack(){
    points += (int)(bet*1.5);
  }

  private static int calcScore(ArrayList<Card> hand){
    int aces = 0;
    int handScore = 0;

    for(int i = 0; i < hand.size(); i++){
      if(hand.get(i).getFace() == 14){
        aces++;
        handScore += 1;
      } else if(hand.get(i).getFace() >= 10){
        handScore += 10;
      } else{
        handScore += hand.get(i).getFace();
      }
    }
    
    for(int i = 0; i < aces; i++){
      if(handScore <= 11){
        handScore += 10;
      }
    }//probably doesn't work, but if it does it's still dumb

    return handScore;
  }

  public void clearHand(){
    while(!hand.isEmpty()){
      hand.remove(0);
    }
    while(!splitHand.isEmpty()){
      splitHand.remove(0);
    }

    isBust = false;
    isStood = false;
    splitHandIsBust = false;
    splitHandIsStood = false;
  }
}