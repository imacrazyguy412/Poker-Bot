package io.github.imacrazyguy412.we.games.poker;

import io.github.imacrazyguy412.we.games.util.*;

/**
 * Represents an evaluation of a poker hand
 * <p>
 * The {@code PokerHandEvaluation} class stores all the information about
 * a poker hand that would be needed, included the cards that formed the
 * best hand, kickers, etc.
 * <p>
 * This implements the {@link Comparable} interface, and can be compared
 * to other instances of {@code PokerHandEvaluation} by their value.
 * @see #value()
 * @see #compareTo(PokerHandEvaluation)
 */
public class PokerHandEvaluation implements Comparable<PokerHandEvaluation> {
  Hand bestHand;

  Card kicker;

  int value;

  public PokerHandEvaluation(){

  }

  public PokerHandEvaluation(Hand hand){
    
  }

  public PokerHandEvaluation(int value){
    this.value = value;
  }

  public Hand getBestHand(){
    return bestHand;
  }

  @Override
  public int compareTo(PokerHandEvaluation other){
    return this.value - other.value;
  }

  @Override
  public String toString(){
    return null;
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof PokerHandEvaluation)) return false;

    return ((PokerHandEvaluation)other).value == this.value;
  }
}