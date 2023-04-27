package we.games;

public class Card implements Comparable<Card>{
  public static final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

  private final int suit;
  private final int face;

  /**
   * Creates a card with a suit s and face value f
   * 
   * @param s -- the suit of the card. 1 is Clubs, 2 is Diamonds, 3 is Hearts, and 4 is Spades
   * @param f -- the face value of the card
   */
  public Card(int s, int f){
    suit = s;
    face = f;
  }

  /**
   * Gives a string in the format of "Face of Suit"
   *
   * @return String -- the card as a String 
   */
  @Override
  public String toString(){
    String suitStr = "Error";
    //if something goes wrong and the card strings are messed up, it will say error
    
    suitStr = suits[suit - 1]; //index starts at 0

    if(face < 11 && face != 1 && face != 14){
      return face + " of " + suitStr;
    } else if(face == 11){
      return "Jack of " + suitStr;
    } else if(face == 12){
      return "Queen of " + suitStr;
    } else if(face == 13){
      return "King of " + suitStr;
    }
    return "Ace of " + suitStr;
  }

  /**
   * Returns the face value of the card
   * 
   * @return face -- the face value of the card
   */
  public int getFace(){
    return face;
  }

  /**
   * Returns the suit of the card
   * <p>
   * <pre>
   * The suit is stored as in integer where 
   * 1: Clubs
   * 2: Diamonds
   * 3: Hearts
   * 4: Spades
   * </pre>
   * 
   * @return suit -- the suit of the card as an int
   */
  public int getSuit(){
    return suit;
  }

  @Override
  public int compareTo(Card o) {
    final int thisValue = this.face * 4 + this.suit;
    final int oValue = o.face * 4 + o.suit;
    return thisValue - oValue;
  }
}
