package we.games;

public class Card {
  private int suit = -1;
  private int face = -1;

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
    
    switch(suit){
      case 4:
        suitStr = "Spades";
        break;

      case 3:
        suitStr = "Hearts";
        break;

      case 2:
        suitStr = "Diamonds";
        break;

      case 1:
        suitStr = "Clubs";
    }

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
    Gives the suit of the card

    Returns: an integer
  */
  public int getSuit(){
    return suit;
  }
}
