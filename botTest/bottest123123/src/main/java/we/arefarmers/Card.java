package we.arefarmers;

public class Card {
  private int suit = -1;
  private int face = -1;

  public Card(int s, int f){
    suit = s;
    face = f;
  }

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

    if(face < 11 && face != 1 && face != 14){ //card doesnt care if 1 or 14 represents an ace. useful for when ace needs to be changed based on the game.
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

  public int getFace(){
    return face;
  }

  public int getSuit(){
    return suit;
  }
}
