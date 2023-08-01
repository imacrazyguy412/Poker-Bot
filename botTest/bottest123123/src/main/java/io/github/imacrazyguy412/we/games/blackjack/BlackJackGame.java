package io.github.imacrazyguy412.we.games.blackjack;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.games.util.Betting;
import io.github.imacrazyguy412.we.games.util.Deck;
import io.github.imacrazyguy412.we.games.util.Game;
import io.github.imacrazyguy412.we.games.util.Joinable;
import io.github.imacrazyguy412.we.games.util.Player;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;


public class BlackJackGame extends Game implements Joinable, Betting {
  private static final Logger log = LoggerFactory.getLogger(BlackJackGame.class);

  public static final int MAX_BET = 500, MIN_BET = 2;
  //private Scanner input = new Scanner(System.in);
  private ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
  //private BlackJackPlayer tempPlayer;
  private BlackJackPlayer dealer = new BlackJackPlayer();
  private Deck deck;
  

  private int playerToTurn = -1;
  private boolean isBetting;

  public BlackJackGame(MessageChannel c){
    super(c);
  }

  public BlackJackGame(MessageChannel c, String startingPlayerName){
    super(c);
    players.add(new BlackJackPlayer(startingPlayerName));
  }
  
  protected void play(){
    log.info("Starting blackjack");
    do{
      log.info("New Game starting");
      setTable();
      
      isBetting = true;
      betting(); //ANCHOR - betting
      isBetting = false;
      log.info("Betting Finished.");

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
    log.info("ending blackjack game");

    stop();
  }

  /**
   * 
   * @param p
   */
  private void playerTurn(BlackJackPlayer p){

    do{

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
    log.debug("Player {} chose {}", p, s);

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
          IllegalArgumentException e = new IllegalArgumentException("BlackJackGame.playerChoice() called without proper choice.");
          log.error("", e);
          throw e;
          
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
        
        p.loseBet();
      } else if(dealer.getScore() > 21){
       
        p.payBet();
      } else if(dealer.getScore() > p.getScore()){
        
        p.loseBet();
      } else if(dealer.getScore() < p.getScore()){
        p.payBet();
        
      } else{
        
      }
    }


    if(p.getSplitScore() == 21 && p.getSplitHand().size() == 2){
     
    } else{
      if(p.splitHandIsPlaying()){
        if(p.getSplitScore() > 21){
          
          p.loseBet();
        } else if(dealer.getSplitScore() > 21){
         
          p.payBet();
        } else if(dealer.getScore() > p.getSplitScore()){
         
          p.loseBet();
        } else if(dealer.getScore() < p.getSplitScore()){
          p.payBet();
        } else{
         
        }
      }
    }
    
    
    
    
    p.resetBet();
  }

  private boolean playersArePlaying(){
    for(int i = 0; i < players.size(); i++){
      if(players.get(i).getChips() <= 0){
          
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
      message(p.getHand());
      
      if(p.getScore() > 21){
        
      }
    } else if(p.splitHandIsPlaying()){
      p.addCardSplit(deck.dealTopCard());
      
      message(p.getSplitHand());

      if(p.getSplitScore() > 21){
        
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
      
    } else{
      if(p.getHand().size() != 2){
        
      } else if(p.getChips() < 2*p.getBet()){
        
      } else if(p.getCard(0).rank == p.getCard(1).rank){
        p.split();
        p.addCard(deck.dealTopCard());
        p.addCardSplit(deck.dealTopCard());

        
        message(p.getHand());
        
        message(p.getSplitHand());
      } else{
        
      }
    }
  }

  private void doubleDown(BlackJackPlayer p){
    if(p.splitHandIsPlaying()){
     
    } else{
      if(p.getHand().size() != 2){
       
      } else if(p.getChips() < 2*p.getBet()){
       
      } else{
        p.bet(2*p.getBet());
        p.addCard(deck.dealTopCard());

       
        message(p.getHand());
        
        if(p.getScore() > 21){
         
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

  /**
   * Execute the betting phase of a game of blackjack
   */
  private void betting(){
    //String name;
    //for(int i = 0; i < players.size(); i++){
    //  name = players.get(i).getName();
    //  playerToBet = i;
//
    //  message(name + ", make your bet with /blackjack bet.");
    //  final int playerBet = inputAsInt();
    //  
    //  message(name + ", you have bet " + playerBet + " chips");
    //  players.get(i).bet(playerBet);
    //}
    message("Everyone make your bets with `/blackjack bet`");
    boolean someoneHasNotBet = true;
    boolean hasPrompted = false;
    synchronized (this) {
      
      while (someoneHasNotBet){

        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          ArrayList<BlackJackPlayer> playersWhoNeedToBet = new ArrayList<BlackJackPlayer>(players.size());

          // Check if there is someone who has not bet
          for(BlackJackPlayer p : players){

            if(p.getBet() == 0){
              playersWhoNeedToBet.add(p);
              System.out.format("%s has not bet\n", p.getName());
            }

          }

          someoneHasNotBet = !playersWhoNeedToBet.isEmpty();

          if(!hasPrompted && playersWhoNeedToBet.size() <= (0.2 * players.size()) && playersWhoNeedToBet.size() > 1){
            StringBuilder str = new StringBuilder();
            for(BlackJackPlayer player : playersWhoNeedToBet){
              str.append(player.getName()).append(", You need to bet"); //TODO - make mention
            }
          }
        }

      }
    }
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

  @Override
  public synchronized void placeBet(int bet, int forPlayerIndex) {
    if(forPlayerIndex < 0) return;

    System.out.format("Placing bet of %d for player %d\n", bet, forPlayerIndex);
    players.get(forPlayerIndex).bet(bet);
    notifyAll();
  }

  @Override
  public void placeBet(int bet, Player forPlayer) {
    placeBet(bet, players.indexOf(forPlayer));
  }

  @Override
  public boolean isBetting() {
    return isBetting;
  }
}
