package we.games.poker;

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
    giveHand(hand);
  }

  public PokerHandEvaluation(int value){
    this.value = value;
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
    return null;
  }

  public boolean equals(Object other){
    if(other.getClass() != this.getClass()) return false;

    return ((PokerHandEvaluation)other).value() == this.value();
  }
}