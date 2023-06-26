package io.github.imacrazyguy412.we.games.blackjack;

import java.util.ArrayList;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.games.util.Betting;
import io.github.imacrazyguy412.we.games.util.Deck;
import io.github.imacrazyguy412.we.games.util.Game;
import io.github.imacrazyguy412.we.games.util.Joinable;
import io.github.imacrazyguy412.we.games.util.Player;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;


public class BlackJackGame extends Game implements Joinable, Betting {
  public static final int MAX_BET = 500, MIN_BET = 2;
  //private Scanner input = new Scanner(System.in);
  private ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
  //private BlackJackPlayer tempPlayer;
  private BlackJackPlayer dealer = new BlackJackPlayer();
  private Deck deck;
  

  private int playerToBet = -1, playerToTurn = -1;

  public BlackJackGame(MessageChannel c){
    super(c);
  }

  public BlackJackGame(MessageChannel c, String startingPlayerName){
    super(c);
    players.add(new BlackJackPlayer(startingPlayerName));
  }
  
  public void play(){
    do{
      setTable();
      
      betting(); //ANCHOR - betting
      System.out.println("Betting Finished.");

      //showAllHands();

      message("Dealer's cards: \nUnknown\n" + dealer.getCard(1).toString());
      for (int i = 0; i < players.size(); i++) {
        playerToTurn = i;
        if(!players.get(i).hasJustJoined()){
          playerTurn(players.get(i));
        }
      }
      playerToTurn = -1;

      dealerTurn();

      for(BlackJackPlayer p : players){
        calcBet(p);
      }

      break; //temp
    
    } while(playersArePlaying());
    //input.close();

    message("Ending BlackJack Game");

    stop();
  }

  /**
   * 
   * @param p
   */
  private void playerTurn(BlackJackPlayer p){

    do{

      //System.out.println("Dealers's cards:");
      //System.out.println("Unknown\n" + dealer.getCard(1));
      //System.out.println();

      showAllHands();
      
      if(p.isPlaying()){
        message("%s's turn with %d and %d chips. What would you like to do?"
        + "\nEnter /hit to hit\nEnter /stand to stand\nEnter /double to double down\nEnter /split to split",
        p.getName(), p.getScore(), p.getChips());

      } else if(p.splitHandIsPlaying()){

        message("%s's split hand with %d and %d chips. What would you like to do?"        
        + "\nEnter /hit to hit\nEnter /stand to stand",
        p.getName(), p.getSplitScore(), p.getChips());

        message(p.getSplitHand());
      }

      playerChoice(p, input());
      

    } while(p.isPlaying() || p.splitHandIsPlaying());
  }

  private void playerChoice(BlackJackPlayer p, String s){
    System.out.println(s);

    switch(s){
        case "w":
        case "hit":
          hit(p);
          break;

        case "s":
        case "stand":
          stand(p);
          break;

        case "a":
        case "split":
          split(p);
          break;

        case "d":
        case "double":
        case "double down":
        case "doubledown":
          doubleDown(p);
          break;

        default:
          System.out.println("BlackJackGame.playerChoice() called without proper choice");
          System.out.println(new Throwable().getStackTrace());
      }
  }

  private void dealerTurn(){
    //TODO: rewrite
    while(dealer.getScore() < 17){
      dealer.addCard(deck.dealTopCard());
    }
  }

  //TODO - rewrite
  private void calcBet(BlackJackPlayer p){
    if(p.getScore() == 21 && p.getHand().size() == 2){
      message(p.getName() + " won their bet of " + p.getBet() + " with blackjack");
      p.payBet(1.5);
    } else{
      if(p.getScore() > 21){
        System.out.println(p.getName() + " has lost their bet of " + p.getBet());
        p.loseBet();
      } else if(dealer.getScore() > 21){
        System.out.println(p.getName() + " has won their bet of " + p.getBet());
        p.payBet();
      } else if(dealer.getScore() > p.getScore()){
        System.out.println(p.getName() + " has lost their bet of " + p.getBet());
        p.loseBet();
      } else if(dealer.getScore() < p.getScore()){
        p.payBet();
        System.out.println(p.getName() + " has won their bet of " + p.getBet());
      } else{
        System.out.println(p.getName() + "'s bet of " + p.getBet() + " has been pushed");
      }
    }


    if(p.getSplitScore() == 21 && p.getSplitHand().size() == 2){
      System.out.println(p.getName() + " won their side bet of " + p.getBet() + " with blackjack");
    } else{
      if(p.splitHandIsPlaying()){
        if(p.getSplitScore() > 21){
          System.out.println(p.getName() + " has lost their side bet of " + p.getBet());
          p.loseBet();
        } else if(dealer.getSplitScore() > 21){
          System.out.println(p.getName() + " has won their side bet of " + p.getBet());
          p.payBet();
        } else if(dealer.getScore() > p.getSplitScore()){
          System.out.println(p.getName() + " has lost their side bet of " + p.getBet());
          p.loseBet();
        } else if(dealer.getScore() < p.getSplitScore()){
          p.payBet();
        } else{
          System.out.println(p.getName() + "'s side bet of " + p.getBet() + " has been pushed");
        }
      }
    }
    
    
    
    
    p.resetBet();
  }

  private boolean playersArePlaying(){
    for(int i = 0; i < players.size(); i++){
      if(players.get(i).getChips() <= 0){
          System.out.println(players.get(i).getName() + " has bust out!");
        players.remove(i);
      }
    }
    if(players.isEmpty()){
      return false;
    }
    return true;
    
  }

  private void hit(BlackJackPlayer p){
    if(p.isPlaying()){
      p.addCard(deck.dealTopCard());
      //System.out.println("Your cards:");
      message(p.getHand());
      
      if(p.getScore() > 21){
        //System.out.println("Bust with " + p.getScore());
      }
    } else if(p.splitHandIsPlaying()){
      p.addCardSplit(deck.dealTopCard());
      //System.out.println("Your cards:");
      message(p.getSplitHand());

      if(p.getSplitScore() > 21){
        //System.out.println("Bust with " + p.getSplitScore());
      }
    }
  }

  private void stand(BlackJackPlayer p){
    if(p.isPlaying()){
      p.stand();
    } else if(p.splitHandIsPlaying()){
      p.splitStand();
    }
  }

  private void split(BlackJackPlayer p){
    if(p.splitHandIsPlaying()){
      System.out.println("You've already split.");
    } else{
      if(p.getHand().size() != 2){
        System.out.println("You can only split on the first turn.");
      } else if(p.getChips() < 2*p.getBet()){
        System.out.println("Not enough points to split.");
      } else if(p.getCard(0).face == p.getCard(1).face){
        p.split();
        p.addCard(deck.dealTopCard());
        p.addCardSplit(deck.dealTopCard());

        System.out.println("Your new cards:");
        message(p.getHand());
        System.out.println("Hand 2:");
        message(p.getSplitHand());
      } else{
        System.out.println("Your two cards have to have the same face.");
      }
    }
  }

  private void doubleDown(BlackJackPlayer p){
    if(p.splitHandIsPlaying()){
      System.out.println("You can't double down after splitting.");
    } else{
      if(p.getHand().size() != 2){
        System.out.println("You can only double down on the first turn.");
      } else if(p.getChips() < 2*p.getBet()){
        System.out.println("Not enough points to double bet");
      } else{
        p.bet(2*p.getBet());
        p.addCard(deck.dealTopCard());

        System.out.println("Your new hand is:");
        message(p.getHand());
        
        if(p.getScore() > 21){
          System.out.println("Bust with " + p.getScore());
        } else{
          p.stand();
        }
      }
    }
  }

  private void showAllHands(){
    for(int i = 0; i < players.size(); i++){
      showHand(players.get(i));
    }
  }

  private void showHand(BlackJackPlayer p) {
    //StringBuilder sb = new StringBuilder();
    message("%s's hand:\n%s", p.getName(), p.getHand());
    if(!p.getSplitHand().isEmpty()){
      message("%s's split hand:\n%s", p.getName(), p.getHand());
    }
  }

  private void setTable(){
    clearPlayersHands();
    clearPlayersBets();
    //clears at the beginning of the game. might move to the end

    clearHasJoinedStatus();
    

    deck = new Deck();
    deck.shuffle();
    //shuffles the deck

    dealer.addCard(deck.dealTopCard());
    dealer.addCard(deck.dealTopCard());
    //deals the dealer their 2 cards

    for(int i = 0; i < players.size(); i++){
      players.get(i).addCard(deck.dealTopCard());
      players.get(i).addCard(deck.dealTopCard());
    }
    //deals the players their 2 cards
  }

  private void clearPlayersHands(){
    for(int i = 0; i < players.size(); i++){
      players.get(i).clearHand();
    }
    dealer.clearHand();
  }

  private void clearPlayersBets(){
    for(int i = 0; i < players.size(); i++){
      players.get(i).resetBet();
    }
  }

  private void betting(){
    String name;
    for(int i = 0; i < players.size(); i++){
      name = players.get(i).getName();
      playerToBet = i;

      message(name + ", make your bet with /bet.");
      System.out.println("pretest");
      final int playerBet = inputAsInt();
      
      message(name + ", you have bet " + playerBet + " chips");
      players.get(i).bet(playerBet);
    }
    playerToBet = -1;
  }

  @Override
  public int getPlayerToBet(){
    return playerToBet;
  }

  @Override
  public int getPlayerToTurn(){
    return playerToTurn;
  }

  private void clearHasJoinedStatus(){
    for(int i = 0; i < players.size(); i++){
      players.get(i).setJoined(false);
    }
  }

  public ArrayList<BlackJackPlayer> getPlayers(){
    return players;

  }

  /**
   * removes itself from the stored games
   */
  public void stop(){
    CommandManager.games.remove(this);
  }

  @Override
  public Player addPlayer(Player player) {
    BlackJackPlayer bjp = new BlackJackPlayer(player.getName(), true);
    players.add(bjp);
    return bjp;
  }

  @Override
  public Player removePlayer(Player player) {
    int i = players.indexOf(new BlackJackPlayer(player.getName()));
    if(i < 0) return null;
    return players.remove(i);
  }
}
