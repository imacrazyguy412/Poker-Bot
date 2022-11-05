package we.arefarmers;

import java.util.Scanner;
import java.util.ArrayList;

public class PokerGame {
  private static final int STARTCHIPS = 500, SMALLBLIND = 10;
  private int dealer = 0;

  //private int[] pots;
  //private int currentSidePot = 0;
  //there will be multiple possible pots. pots[0] will always be the main pot while other pots in the array will be side pots

  private Scanner input = new Scanner(System.in);
  private Deck pokerDeck = new Deck();
  private ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
  private ArrayList<Card> communityCards = new ArrayList<Card>();
  private PokerPlayer tempPlayer;

  public PokerGame(){

  }

  private static void setTable(){
    System.out.print("How many players are there");
    int numPlayers;
    //try{
       // numPlayers = input.nextInt(); //fix or something
   // }
    
  }
}
