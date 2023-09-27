package we.games.poker;

import we.games.util.*;

/**
 * Represents an evaluation of a poker hand
 * <p>
 * The {@code PokerHandEvaluation} class stores all the information about
 * a poker hand that would be needed, included the cards that formed the
 * best hand, kickers, etc.
 * <p>
 * This implements the {@link Comparable} interface, and can be compared
 * to other instances of {@code PokerHandEvaluation} by their value.
 * @see #compareTo(PokerHandEvaluation)
 */
public class PokerHandEvaluation implements Comparable<PokerHandEvaluation> {
  Hand fullHand;
  Hand bestHand;

  int kicker;

  public PokerHandEvaluation(){

  }

  public PokerHandEvaluation(Hand hand){
    fullHand = hand;
  }

  public Hand getBestHand(){
    return bestHand;
  }

  public int value(){
    return 0;
  }

  public int compareTo(PokerHandEvaluation other){
    return this.value() - other.value();
  }

  public String toString(){
    for(int i = 0; i < fullHand.size(); i++){
      
    }

    return null;
  }
}