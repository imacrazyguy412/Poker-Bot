package we.games.util;

/**
 * The {@code Card} class represents a single playing card per instance.
 * <p>
 * Each card stores its own suit and face as integers. The cards are displayed as the 
 * <a href="https://en.wikipedia.org/wiki/French-suited_playing_cards">French Suited</a>,
 * <a href="https://en.wikipedia.org/wiki/Standard_52-card_deck">Standerd 52-card deck</a>
 * using the 
 * <a href=https://en.wikipedia.org/wiki/French-suited_playing_cards#English_pattern>English Pattern</a>.
 * 
 * 
 * @see #face()
 * @see #suit()
 * @see Deck
 * @see https://en.wikipedia.org/wiki/Playing_card
 */
public class Card implements Comparable<Card>{
  public static final String[] suits = {"Null", "Clubs", "Diamonds", "Hearts", "Spades"};
  public static final String[] faces = {
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
  public final int face;

  /**
   * Creates a card with a suit s and face value f
   * 
   * @param suit -- the suit of the card. 1 is Clubs, 2 is Diamonds, 3 is Hearts, and 4 is Spades
   * @param face -- the face value of the card
   */
  public Card(int suit, int face){
    this.suit = suit;
    this.face = face;
  }

  /**
   * Gives a string in the format of "Face of Suit"
   *
   * @return String -- the card as a String 
   */
  @Override
  public String toString(){
    return faces[face] + " of " + suits[suit];
  }

  @Override
  public int compareTo(Card o) {
    final int thisValue = this.face * 4 + this.suit;
    final int oValue = o.face * 4 + o.suit;
    return thisValue - oValue;
  }
}
