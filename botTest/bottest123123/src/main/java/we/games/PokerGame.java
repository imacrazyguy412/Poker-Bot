package we.games;

import we.arefarmers.DiscordBot;

import java.util.Scanner;

import java.util.ArrayList;

public class PokerGame extends Game{
  private static final int START_CHIPS = 500, SMALL_BLIND = 10;
  private int dealer = 0;

  //private int[] pots;
  //private int currentSidePot = 0;
  //there will be multiple possible pots. pots[0] will always be the main pot
  //while other pots in the array will be side pots
  private ArrayList<Integer> pots = new ArrayList<Integer>();
  //changed to an arraylist for dynamic size
  //pots.get(0) should still be the main pot

  //this probably isnt gonna work btw, we might need to store pots on the
  //player or as an object

  //private Scanner input = new Scanner(System.in);
  private Deck pokerDeck = new Deck();
  private ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
  private Hand communityCards = new Hand();
  //private PokerPlayer tempPlayer; //IM FKUKING DUMB. its much simpler to not
  //take up space with a temp player

  public PokerGame(net.dv8tion.jda.api.entities.channel.middleman.MessageChannel channel){
    super(channel);
  }

  public PokerGame(String startingPlayerName, net.dv8tion.jda.api.entities.channel.middleman.MessageChannel channel){
    super(channel);
    createTable(startingPlayerName);
  }

  public void play(){

  }

  private void createTable(String startingPlayerName){
    players.add(new PokerPlayer(START_CHIPS, startingPlayerName));

    DiscordBot.message("waiting for player to join...\nType /join to join " + startingPlayerName, channel);
    //TODO: make the bot wait for another player to join
  }

  private void betting(){
    int start, last;

    //checks if the dealer is the last player in the array
    if(dealer == players.size() - 1){
      //if so, the starting player is set to the first player in the array
      start = 0;
    } else{
      //otherwise, the starting player is set to the next player in the array
      start = dealer + 1;
    }
    //the last player to bet always starts as the dealer
    last = dealer;

    int i = start;
    int bet = 0;
    do{
      PokerPlayer player = playes.get(i);
      //checks if the player is playing
      if(player.isPlaying()){
        //if so, it goes through with the player betting

        message(player.getName() + ", it is your turn to bet");
        //TODO: make the bot wait for the player to bet
        input();

        int playerBet = Integer.parseUnsignedInt(choice);

        player.setPlayerBet(playerBet); //TODO: get and set the player's bet

        //makes the player lose the chips
        player.placeBet(playerBet);

        //checks if the player is betting above what is needed to call
        if(playerBet - player.getPlayerBet() > bet){ //this might change
          //if they did raise, the last player to bet is adjusted
          if(i > 0){
            last = i - 1;
          } else{
            last = players.size() - 1;
          }
        }


        //loops through
        if(i < players.size() - 1){
          i++;
        } else{
          i = 0;
        }
      }  
    } while(i != last);
  }

  //SECTION - hand strength calculation
  /**
   * the method getWinnerIndex() returns the index of the player with the best hand strength.
   * In the event of a tie, idk we'll work that out later
   * also sorts the hands of the players befoer checking
   * might add another method to return the cards used in the final hand, if we want that
   * 
   * <p>
   * 
   * These methods for checking hand strength are split among 9 extra
   * methods (10 total) to check for each type of hand.
   * If the type of hand isn't their (check2Pair() is called with a hand
   * that does not have two pairs), it returns -1.
   * Otherwise, it returns a number based on the strength of them as below:
   * 
   * <hr>
   * 
   * <b>high card:</b> the card's face value (aces are 14)
   * <p>
   * <b>high card range:</b> [2, 14]
   * <p>
   * {@link #checkHighCard(Hand)}
   * 
   * <hr>
   * 
   * <b>pair:</b> the card's face value + 14
   * <p>
   * <b>pair range:</b> [16, 28]
   * <p>
   * {@link #checkPair(Hand)}
   * 
   * <hr>
   * 
   * <b>2pair:</b> a number in the hundreds or thousands. the first two digits
   *  (the thousands and hundreds places) store the strength of the
   *  HIGHER strength pair. The tens and ones place store the strength of
   *  the lower stregth pair, in the same value that high cards are stored
   *  (aces as 14). For example, a 2pair jacks and 4s would be returned as
   *  1104
   * <p>
   * <b>2pair range:</b> [0302, 1413]
   * <p>
   * {@link #check2Pair(Hand)}
   * 
   * <hr>
   * 
   * <b>3 of a kind:</b> The face value of the card multiplied by 1000
   * <p>
   * <b>3 of a kind range:</b> [02000, 14000]
   * <p>
   * {@link #checkTrips(Hand)}
   * 
   * <hr>
   * 
   * <b>straight:</b> Probably the highest card's value multiplied by 1000 + 14000
   * <p>
   * <b>straight range:</b> [20000, 28000]
   * <p>
   * {@link #checkStraight(Hand)}
   * 
   * <hr>
   * 
   * <b>flush:</b> Probably the highest card's value multiplied by 1000 + 28000 +
   *  the suit (so the program can know when saying what the player won
   *  with). The flush value shouldnt affect the game, because their shouldnt
   *  be able to be more than one suit of flush in one game
   * <p>
   * <b>flush range:</b> [060001, 42004]
   * <p>
   * {@link #checkFlush(Hand)}
   * 
   * <hr>
   * 
   * <b>full house:</b> Likely will be similar to 2pair, but instead of the
   *  higher pair its the trips and instead of multiplying by 100, its
   *  multiplied by 100000 + the value of the pair
   * <p>
   * <b>full house range:</b> [0200003, 1400013]
   * <p>
   * {@link #checkFullHouse(Hand)}
   * 
   * <hr>
   * 
   * <b>four of a kind:</b> the value of the cards multiplied by 1000000
   * <p>
   * <b>four of a kind range:</b> [02000000, 14000000]
   * <p>
   * {@link #checkFourOfAKind(Hand)}
   * 
   * <hr>
   * 
   * <b>straight flush:</b> Probablt highest card's value multiplied by 10000000
   *  + suit
   * <p>
   * <b>straight flush range:</b> [060000001, 140000004]
   * <p>
   * {@link #checkStraightFlush(Hand)}
   * 
   * <hr>
   * 
   * <b>royal flush:</b> doesnt matter, a player cant get a better royal flush
   *  than another, so just set to an absurdly high number like 31415926535
   * {@link #checkRoyalFlush(Hand)}
   * 
   * <hr>
   * 
   * The basic goal of the methods of storing the values of hands is that
   * the stronger hand will always return a higher number, making comparisons
   * easier
  */
  private int getWinnerIndex(){
    int winner = 0, winningStrength = 0, currentStrength = 0;

    for(int i = 0; i < players.size(); i++){
      player.sortHand();
      //sorts the hand first thing
      //mainly for straights, but other checks could use it

      currentStrength = getHandStrength(player.getHand());

      if(currentStrength > winningStrength){
        winningStrength = currentStrength;
        winner = i;
      }

    }

    return winner;
  }

  private static int getHandStrength(Hand h){
    int handStrength;

    if(checkRoyalFlush(h) != -1){
      handStrength = checkRoyalFlush(h);
    } else if(checkStraightFlush(h) != -1){
      handStrength = checkStraightFlush(h);
    } else if(checkFourOfAKind(h) != -1){
      handStrength = checkFourOfAKind(h);
    } else if(checkFullHouse(h) != -1){
      handStrength = checkFullHouse(h);
    } else if(checkFlush(h) != -1){
      handStrength = checkFlush(h);
    } else if(checkStraight(h) != -1){
      handStrength = checkStraight(h);
    } else if(checkTrips(h) != -1){
      handStrength = checkTrips(h);
    } else if(check2Pair(h) != -1){
      handStrength = check2Pair(h);
    } else if(checkPair(h) != -1){
      handStrength = checkPair(h);
    } else{
      handStrength = checkHighCard(h);
    }
    
    return handStrength;
  }

  private static int checkHighCard(Hand h){
    int highCard = -1;
    for(int i = 0; i < h.size(); i++){
      if(h.get(i).getFace() > highCard){
        //checks through the hand for the highest card
        highCard = h.get(i).getFace();
      }
    }
    return highCard;
  }

  private static int checkPair(Hand h){
    int pair = -1;

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        if(i != j && h.get(i).getFace() == h.get(j).getFace()){
          //checks if the face value is the same on any two cards, but
          //ignores it if its checking the same index

          if(h.get(i).getFace() > pair){
            pair = h.get(i).getFace() + 14;
          }

          //maybe check for trips and 2 pair here?
          //nevermind
        }
      }
    }

    return pair;
  }

  private static int check2Pair(Hand h){
    int tPair = -1;
    int p = -1, q = -1, pair = -1;

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        if(i != j && h.get(i).getFace() == h.get(j).getFace()){
          //checks if the face value is the same on any two cards, but
          //ignores it if its checking the same index

          if(h.get(i).getFace() > pair){
            pair = h.get(i).getFace();
            p = i;
            q = j;
          }
        }
      }
    }
    //should set p and q to the index values of the two cards of the
    //highest pair

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        if(
          i != j && i != p && i != q && j != p && j != q
          //checks to make sure that all of the cards are different
          //indexes in the hand
           && h.get(i).getFace() == h.get(j).getFace()
          //checks the cards have the same face value
        ){
          if(h.get(i).getFace() > tPair){
            tPair = h.get(i).getFace();
          }
        }
      }
    }
    //the reason Im using this p and q bullshi tis because I want to check
    //for the second pair after the first one resolves. The first pair
    //should always be the higher pair, but I might be dumb

    if(pair == -1 || tPair == -1){
      tPair = -1;
    } else if(tPair > pair){
      tPair = tPair*100 + pair;
      System.out.println("\n\ncheck2Pair() didn't work as intended, keep the redundencies.\n\n");
    } else if(pair > tPair){
      tPair = pair*100 + tPair;
    } else{
      tPair = -1;
      //System.out.println("\n\nError in check2Pair()\n\n");
    }
    //checks which is the higher pair and stores the higher's value in
    //the hundreds and thousands place, and the lower pair in the
    //ones and tens place
    
    return tPair;    
  }

  private static int checkTrips(Hand h){
    int trips = -1;

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        for(int k = 0; k < h.size(); k++){
          if(
            i != j && i != k && j != k
            //checks to make sure all cards are unique
            && h.get(i).getFace() == h.get(j).getFace() && 
            h.get(i).getFace() == h.get(k).getFace()
            //checks if both of the other cards have the same value as 
          ){
            if(h.get(i).getFace() > trips){
               trips = h.get(i).getFace()*1000; 
            }
          }
        }
      }
    }

    return trips;
  }

  private static int checkStraight(Hand h){
    int straight = -1;

    //I don't know how to do this

    return straight;
  }

  private static int checkFlush(Hand h){
    int flush = -1;
    int suit = -1;
    int[] numSuit = {0, 0, 0, 0};
    //0 is clubs, 1 is diamonds, 2 is hearts, 3 is spades

    for(int i = 0; i < h.size(); i++){
      numSuit[h.get(i).getSuit()-1]++;
      //System.out.println(h.get(i).getSuit()-1);
    }
    //checks through the hand to check if there even are five cards of the
    //same suit

    if(numSuit[3] >= 5){
      suit = 4;
    } else if(numSuit[2] >= 5){
      suit = 3;
    } else if(numSuit[1] >= 5){
      suit = 2;
    } else if(numSuit[0] >= 5){
      suit = 1;
    }
    //stores the suit of which cards have the same suit

    for(int i = 0; i < h.size(); i++){
      if(h.get(i).getSuit() == suit && h.get(i).getFace() > flush){
        flush = h.get(i).getFace();
      }
    }
    //checks through the cards for the highest card of the suit of the flush

    if(flush != -1){
      flush = flush*1000 + 28000 + suit;
    }

    return flush;
  }

  private static int checkFullHouse(Hand h){
    int fullHouse = -1;
    int p = -1, q = -1, r = -1, pair = -1, trips = -1;

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        for(int k = 0; k < h.size(); k++){
          if(
            i != j && i != k && j != k
            //checks to make sure all cards are unique
            && h.get(i).getFace() == h.get(j).getFace() && 
            h.get(i).getFace() == h.get(k).getFace()
            //checks if both of the other cards have the same value as 
          ){
            if(h.get(i).getFace() > trips){
              trips = h.get(i).getFace();
              p = i;
              q = j;
              r = k;
            }
          }
        }
      }
    }
    //should set p and q to the index values of the two cards of the
    //highest pair
    //System.out.println("testing: " + trips);

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        if(
          i != j && i != p && i != q && i != r && j != p && j != q && j != r
          //checks to make sure that all of the cards are different
          //indexes in the hand
           && h.get(i).getFace() == h.get(j).getFace()
          //checks the cards have the same face value
        ){
          if(h.get(i).getFace() > pair){
            pair = h.get(i).getFace();
          }
        }
      }
    }

    //System.out.println("testing: " + pair);

    if(trips != -1 && pair != -1){
      fullHouse = trips*100000 + pair;
    }

    return fullHouse;
  }

  private static int checkFourOfAKind(Hand h){
    int fours = -1;

    for(int i = 0; i < h.size(); i++){
      for(int j = 0; j < h.size(); j++){
        for(int k = 0; k < h.size(); k++){
          for(int l = 0; l < h.size(); l++){
            if(
              i != j && i != k && i != l &&
              j != k && j != l &&
              k != l
              //checks that all of the cards are unique
            ){
              if(
                h.get(i).getFace() == h.get(j).getFace() &&
                h.get(i).getFace() == h.get(k).getFace() &&
                h.get(i).getFace() == h.get(l).getFace()
                //checks that all of the cards are the same face
              ){
                fours = h.get(i).getFace()*1000000;
              }
            }
          }
        }
      }
    }
    return fours;
  }

  private static int checkStraightFlush(Hand h){
    int straightFlush = -1;

    //I don't know what to put here

    return straightFlush;
  }

  private static int checkRoyalFlush(Hand h){
    int royalFlush = -1;

    //I don't know what to put here

    return royalFlush;
  }
  //!SECTION

  public ArrayList<PokerPlayer> getPlayers(){
    return players;
  }

  /**
   * Add a player to the game
   * @param player -- the player to add
   * @return {@code true} if the addition was needed to start the game, false otherwise
   */
  public boolean addPlayer(PokerPlayer player){
    players.add(player);

    return players.size() == 2;
  }
}
