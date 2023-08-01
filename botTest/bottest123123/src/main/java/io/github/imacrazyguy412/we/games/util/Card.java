package io.github.imacrazyguy412.we.games.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code Card} class represents a single playing card per instance.
 * <p>
 * Each card stores its own suit and rank as integers. The cards are displayed as the 
 * <a href="https://en.wikipedia.org/wiki/French-suited_playing_cards">French Suited</a>,
 * <a href="https://en.wikipedia.org/wiki/Standard_52-card_deck">Standerd 52-card deck</a>
 * using the 
 * <a href=https://en.wikipedia.org/wiki/French-suited_playing_cards#English_pattern>English Pattern</a>.
 * 
 * 
 * @see #rank()
 * @see #suit()
 * @see Deck
 * @see https://en.wikipedia.org/wiki/Playing_card
 */
public class Card implements Comparable<Card>, Cloneable {
  public static final Logger log = LoggerFactory.getLogger(Card.class);

  public static final String[] suits = {"Null", "Clubs", "Diamonds", "Hearts", "Spades"};
  public static final String[] ranks = {
    "Null", "Ace", "Two", "Three", "Four", "Five",
    "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
    "Queen", "King", "Ace"
  };

  public static final int NONE      = 0;

  public static final int CLUBS     = 1;
  public static final int DIAMONDS  = 2;
  public static final int HEARTS    = 3;
  public static final int SPADES    = 4;

  public static final int ACE       = 14; // ace is currently high
  public static final int TWO       = 2;
  public static final int THREE     = 3;
  public static final int FOUR      = 4;
  public static final int FIVE      = 5;
  public static final int SIX       = 6;
  public static final int SEVEN     = 7;
  public static final int EIGHT     = 8;
  public static final int NINE      = 9;
  public static final int TEN       = 10;
  public static final int JACK      = 11;
  public static final int QUEEN     = 12;
  public static final int KING      = 13;

  /**
   * The suit of the card. Readonly
   */
  public final int suit;
  public final int rank;

  /**
   * Creates a card with a suit s and rank value f
   * 
   * @param suit -- the suit of the card. 1 is Clubs, 2 is Diamonds, 3 is Hearts, and 4 is Spades
   * @param rank -- the rank value of the card
   */
  public Card(int suit, int rank){
    if(rank < 0 || rank > 14){
      throw new IllegalArgumentException(String.format("Illegal value for rank (%d)", rank));
    }

    if(suit < 0 || suit > 4){
      throw new IllegalArgumentException(String.format("Illegal value for suit (%d)", suit));
    }

    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Gives a string in the format of "Face of Suit"
   *
   * @return String -- the card as a String 
   */
  @Override
  public String toString(){
    return ranks[rank] + " of " + suits[suit];
  }

  @Override
  public int compareTo(Card o) {
    final int thisValue = this.rank * 4 + this.suit;
    final int oValue = o.rank * 4 + o.suit;
    return thisValue - oValue;
  }

  public boolean equals(Card other){
    if(other == null){
      return false;
    }

    return this.suit == other.suit && this.rank == other.rank;
  }

  @Override
  public boolean equals(Object other){
    if(!(other instanceof Card)){
      return false;
    }

    return this.equals((Card)other);
  }

  @Override
  public Object clone(){
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      log.error(String.format("Clone of card (%s) failed.", this), e);
      return new Card(suit, rank);
    }
  }
}
