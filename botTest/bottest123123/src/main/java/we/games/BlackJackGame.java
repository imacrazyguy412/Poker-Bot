package we.games;

//import java.util.Scanner;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.DiscordBot;

import java.util.ArrayList;

import we.arefarmers.commands.CommandManager;


public class BlackJackGame implements Runnable{
  public static final int MAXBET = 500, MINBET = 2;
  private Thread thread;
  //private Scanner input = new Scanner(System.in);
  private ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
  //private BlackJackPlayer tempPlayer;
  private BlackJackPlayer dealer = new BlackJackPlayer();
  private Deck deck;
  
  private MessageChannel channel;
  private String choice;

  private int playerToBet = -1, playerToTurn = -1;

  public BlackJackGame(MessageChannel c){
    channel = c;
  }

  public BlackJackGame(MessageChannel c, String startingPlayerName){
    players.add(new BlackJackPlayer(startingPlayerName));
    channel = c; //might work, not sure.
    //playBlackJack();
  }

  public void start(){
    thread = new Thread(this);
    thread.start();
  }

  public void run(){
    playBlackJack();
  }
  
  public void playBlackJack(){
    deck = new Deck();

    do{
      setTable();
      
      betting();

      //showAllHands();

      DiscordBot.message("Dealer's cards: \nUnknown\n" + dealer.getCard(1).toString(), channel);
      for (int i = 0; i < players.size(); i++) {
        BlackJackPlayer p = players.get(i);
        playerToTurn = i;
        if(!p.hasJustJoined()){
          playerTurn(p);
        }
      }
      playerToTurn = -1;

      dealerTurn();


      break; //temp
    
    } while(playersArePlaying());
    //input.close();

    DiscordBot.message("Ending BlackJack Game", channel);

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
        //System.out.println("Your hand:");
        DiscordBot.message(p.getName()
        + "'s turn with "
        + p.getScore()
        + " and "
        + p.getPoints()
        + " points. What would you like to do?"
        + "\nEnter /hit to hit" //change to /hit or whatever the command for hitting is
        + "\nEnter /stand to stand" //also change
        + "\nEnter /double to double down" //also change
        + "\nEnter /split to split", channel); //also change
        //very long string

      } else if(p.splitHandIsPlaying()){
        //System.out.println("Your hand:");

        DiscordBot.message(p.getName()
        + "'s split hand with "
        + p.getSplitScore()
        + " and "
        + p.getPoints()
        + " points. What would you like to do?"
        + "\nEnter /hit to hit" //change to /hit
        + "\nEnter /stand to stand", channel); //also change
        //long string

        printHand(p.getSplitHand());
      }

      choice = "";
      input();
      playerChoice(p, choice);
      

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
      }
  }

  private void dealerTurn(){
    
    //TODO: rewrite
  }

  private void calcBet(BlackJackPlayer p){
    if(p.getScore() == 21 && p.getHand().size() == 2){
      System.out.println(p.getName() + " won their bet of " + p.getBet() + " with blackjack");
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

  private void printHand(ArrayList<Card> hand){
    String message = "";

    for(int i = 0; i < hand.size(); i++){
      if(players.get(0).getName().equalsIgnoreCase("CarrotCakeツ#8734")){
        //easter egg for a friend :)
        message += "J A M E S\n";
        //still an easter egg lmao
        //and no, it's not thier real name, dumbass
      } else{
        //System.out.println(hand.get(i));
        message += hand.get(i).toString() + "\n";
      }
      
    }
    //System.out.println();
    DiscordBot.message(message, channel);
  }

  private boolean playersArePlaying(){
    for(int i = 0; i < players.size(); i++){
      if(players.get(i).getPoints() <= 0){
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
      printHand(p.getHand());
      
      if(p.getScore() > 21){
        //System.out.println("Bust with " + p.getScore());
      }
    } else if(p.splitHandIsPlaying()){
      p.addCardSplit(deck.dealTopCard());
      //System.out.println("Your cards:");
      printHand(p.getSplitHand());

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
      } else if(p.getPoints() < 2*p.getBet()){
        System.out.println("Not enough points to split.");
      } else if(p.getCard(0).getFace() == p.getCard(1).getFace()){
        p.split();
        p.addCard(deck.dealTopCard());
        p.addCardSplit(deck.dealTopCard());

        System.out.println("Your new cards:");
        printHand(p.getHand());
        System.out.println("Hand 2:");
        printHand(p.getSplitHand());
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
      } else if(p.getPoints() < 2*p.getBet()){
        System.out.println("Not enough points to double bet");
      } else{
        p.bet(2*p.getBet());
        p.addCard(deck.dealTopCard());

        System.out.println("Your new hand is:");
        printHand(p.getHand());
        
        if(p.getScore() > 21){
          System.out.println("Bust with " + p.getScore());
        } else{
          p.stand();
        }
      }
    }
  }

  private void showAllHands(){
    for(BlackJackPlayer p : players){
      DiscordBot.message(p.getName() + "'s hand:", channel);
      printHand(p.getHand());
      if(!p.getSplitHand().isEmpty()){
        DiscordBot.message(p.getName() + "'s split hand:", channel);
        printHand(p.getSplitHand());
      }
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

      DiscordBot.message(name + ", make your bet with /bet.", channel);
      System.out.println("pretest");
      choice = "";
      input();
      System.out.println("test");
      DiscordBot.message(name + ", you have bet " + choice + " chips", channel);
    }
    playerToBet = -1;
  }

  public int getPlayerToBet(){
    return playerToBet;
  }

  public int getPlayerToTurn(){
    return playerToTurn;
  }

  private void clearHasJoinedStatus(){
    for(int i = 0; i < players.size(); i++){
      players.get(i).setJoined(false);
    }
  }

  public void join(String n){
    players.add(new BlackJackPlayer(n, true));
  }

  public MessageChannel getChannel(){
    return channel;
  }



  /**
   * passes a {@code String} to {@link #choice}
   * @param s -- {@code String} the string to pass
   */
  public void setChoice(String s){
    choice = s.toLowerCase().replaceAll(" ", "");
    System.out.println("chosen: " + choice);
  }

  /**
   * Waits until {@link #setChoice(String)} is called from a seperate {@code Thread}
   * @see #setChoice(String)
   */
  private void input(){
    while(true){
    try {
      thread.wait();
      System.out.println("choice: " + choice);
    } catch (Exception e) {
      // TODO: handle exception
    }
    if(!choice.equals(""))
    break;
  }


  }

  public ArrayList<BlackJackPlayer> getPlayers(){
    return players;
  }

  @Override
  public boolean equals(Object obj){
    BlackJackGame b = (BlackJackGame)(obj);

    if(b.getChannel().equals(this.getChannel())){
      return true;
    }
    return false;
  }

  public void stop(){
    CommandManager.blackJackGames.remove(this);

    //removes itself from the stored games
  }
}
