package we.games;

public class Card {
  private int suit = -1;
  private int face = -1;

  /**
    Creates a card with a suit stored 1-4 from the 1st perameter and a face value of the 2nd perameter
  */
  public Card(int s, int f){
    suit = s;
    face = f;
  }

  /**
    Gives a string in the format of "Face of Suit"

    Returns: a String 
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
    Gives the face of the card

    Returns: an integer
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
