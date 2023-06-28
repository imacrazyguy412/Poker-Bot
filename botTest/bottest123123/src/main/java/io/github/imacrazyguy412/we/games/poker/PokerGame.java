package io.github.imacrazyguy412.we.games.poker;

import java.util.ArrayList;

import io.github.imacrazyguy412.we.games.util.*;

public class PokerGame extends Game implements Joinable, Betting {
  private static final int START_CHIPS = 500, SMALL_BLIND = 10;
  public static final int MAX_BET = 100;
  private int dealer = 0;

  //TODO - side pots
  private int mainPot;

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
    while(players.size() < 2); //wait until there are enough players

    pokerDeck.shuffle();

    //TODO: blinds

    //The players bet
    betting();

    //The flop
    communityCards.add(pokerDeck.dealTopCard());
    communityCards.add(pokerDeck.dealTopCard());
    communityCards.add(pokerDeck.dealTopCard());

    //Display the community cards
    message(communityCards);

    //The players bet again
    betting();

    //The turn
    communityCards.add(pokerDeck.dealTopCard());

    //Display the community cards
    message(communityCards);

    //More betting
    betting();

    //The river
    communityCards.add(pokerDeck.dealTopCard());

    //Display the community cards
    message(communityCards);

    //Final betting
    betting();

    //TODO: showdown

  }

  private void createTable(String startingPlayerName){
    players.add(new PokerPlayer(START_CHIPS, startingPlayerName));

    message("waiting for player to join...\nType /join to join " + startingPlayerName);
    while(players.size() < 2){
      try {
        wait();
      } catch (InterruptedException e) {
        break;
      }
    } //wait until there are enough players
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
      PokerPlayer player = players.get(i);
      //checks if the player is playing
      if(player.isPlaying()){
        //if so, it goes through with the player betting

        message(player.getName() + ", it is your turn to bet. The current bet is " + bet + ".\nUse -1 to fold or 0 to call");

        int playerBet = Integer.parseInt(input());

        if(playerBet < 0){
          player.setIsPlaying(false);
          continue;
        }

        int totalPlayerBet = playerBet + player.getPlayerBet();
        player.setPlayerBet(totalPlayerBet); //increases the player's bet by the given amount

        //makes the player lose the chips
        player.placeBet(totalPlayerBet);

        //checks if the player is not calling
        if(playerBet != 0){
          bet = totalPlayerBet;
          //if they did raise, the last player to bet is adjusted
          if(i > 0){
            last = i - 1;
          } else{
            last = players.size() - 1;
          }
        }
      }
      //loops through
      if(i < players.size() - 1){
        i++;
      } else{
        i = 0;
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
   * <b>royal flush:</b> The suit of the royal flush + 1500000000
   * <p>
   * <b>royal flush range:</b> [1500000001, 1500000004]
   * <p>
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
      PokerPlayer player = players.get(i);
      player.sortHand();
      //sorts the hand first thing
      //mainly for straights, but other checks could use it

      currentStrength = PokerHandEvaluator.getHandStrength(player.getHand());

      if(currentStrength > winningStrength){
        winningStrength = currentStrength;
        winner = i;
      }

    }

    return winner;
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

    boolean justStarted = players.size() == 2;
    if(justStarted){
      Thread.currentThread().notify();
    }

    return justStarted;
  }

  @Override
  public Player addPlayer(Player player) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'join'");
  }

  @Override
  public Player removePlayer(Player player) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'leave'");
  }

  @Override
  public int getPlayerToTurn() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getPlayerToTurn'");
  }

  @Override
  public void placeBet(int bet, int forPlayerIndex) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'placeBet'");
  }

  @Override
  public void placeBet(int bet, Player forPlayer) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'placeBet'");
  }

  @Override
  public boolean isBetting() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isBetting'");
  }
}
