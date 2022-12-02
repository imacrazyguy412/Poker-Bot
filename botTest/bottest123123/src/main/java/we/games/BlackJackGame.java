package we.games;

//import java.util.Scanner;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.DiscordBot;

import java.util.ArrayList;


public class BlackJackGame{
  //private Scanner input = new Scanner(System.in);
  private ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();
  //private BlackJackPlayer tempPlayer;
  private BlackJackPlayer dealer = new BlackJackPlayer();
  private Deck deck;
  private static final int MAXBET = 500, MINBET = 2;
  private MessageChannel channel;
  private String choice;

  public BlackJackGame(MessageChannel channel, String startingPlayerName){
    players.add(new BlackJackPlayer(startingPlayerName));
    playBlackJack();
    this.channel = channel; //might work, not sure.
  }
  
  private void playBlackJack() {
    deck = new Deck();
    //String choice;

    //for(int i = 0; i < numPlayers; i++){
      //System.out.print("Player " + (i+1) + ", what is your name? ");
      //DiscordBot.message("Player" + (i + 1) + ", type /join to join");
      //might change
      //tempPlayer = new BlackJackPlayer(input.nextLine());
      //players.add(tempPlayer);
    //}

    do{

      setTable();

      showAllHands();

      betting();

      for(int i = 0; i < players.size(); i++){
        if(!players.get(i).hasJustJoined()){
          playerTurn(players.get(i));
        }
      }

      dealerTurn();
      /*
      Old Code, rewriting
      deck = new Deck();
      deck.shuffle();
      
      dealer.addCard(deck.dealTopCard());
      dealer.addCard(deck.dealTopCard());
      //deals the dealer's two cards
  
      for(int i = 0; i < players.size(); i++){
        System.out.print(players.get(i).getName() + ", you have " + players.get(i).getPoints() + " points, make your bet: ");
  
        do{
          choice = input.nextLine();
          try{
            if(Integer.parseInt(choice) < MINBET || Integer.parseInt(choice) > players.get(i).getPoints() || Integer.parseInt(choice) > MAXBET){
              System.out.print("Minumum bet is 2, maximum bet is 500. Don't bet more than you have!\nPlease reenter response: ");
            } else{
              players.get(i).bet(Integer.parseInt(choice));
            }
          } catch(Exception e){
            System.out.print("Invalid Response.\nPlease reenter response: ");
          }
        } while(players.get(i).getBet() == 0);
      }
  
      System.out.println("Dealers's cards:");
      System.out.println("Unknown\n" + dealer.getCard(1));
      System.out.println();

      for(int i = 0; i < players.size(); i++){
        //try{
          //Thread.sleep(1400);
        //}catch(Exception e){
          //System.out.print("Error");
        //}
        
        players.get(i).addCard(deck.dealTopCard());
        players.get(i).addCard(deck.dealTopCard());
        
        System.out.println(players.get(i).getName() + "'s hand:");
        printHand(players.get(i).getHand());

        
      }
        //try{
          //Thread.sleep(1700);
        //} catch(Exception e){
          //System.out.println("Error");
        //}
  
      if(dealer.getCard(1).getFace() == 1){
        System.out.println("The dealer is showing an ace.");
        for(int i = 0; i < players.size(); i++){
          do{
            System.out.print(players.get(i).getName() + ", would you like to make an insurence bet? (Y/N) ");
            choice = input.nextLine();
            if(choice.equalsIgnoreCase("Y")){
              players.get(i).bet((int)(players.get(i).getBet()/2));
              if(dealer.getScore() == 21){
                players.get(i).payBet();
              } else{
                players.get(i).loseBet();
              }
              players.get(i).bet(players.get(i).getBet()*2);
              //rounding might be annoying here, but hopefully ill remember to fix it 
            }
          }while(!(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")));
        }
        if(dealer.getScore() != 21){
          System.out.println("Dealer does not have black jack.\n");
          //try{
            //Thread.sleep(1700);
          //} catch(Exception e){
            //System.out.println("I don't even know how this one happened");
          //}
        }
      }
  
      for(int i = 0; i < players.size(); i++){
        
       
        
        if(dealer.getScore() == 21){
          System.out.println("Dealer has blackjack!");
          if(players.get(i).getScore() == 21){
            
            System.out.println(players.get(i).getName() + "'s bet of " + players.get(i).getBet() + " has been pushed.");
            players.get(i).resetBet();
          } else{
            System.out.println(players.get(i).getName() + " has lost their bet of " + players.get(i).getBet());
            players.get(i).loseBet();
          }
          
        } else{        
          if(players.get(i).getScore() == 21){
            System.out.println("Blackjack! " + players.get(i).getName() + "'s bet is payed 3:2");
            players.get(i).blackJack();
            //try{
              //Thread.sleep(4000);
            //} catch(Exception e){
              //System.out.println("The door's righ- wait this is a console of text.");
            //}
          } else{
            playerTurn(players.get(i));
          }
        }
      }
      dealerTurn();
      
      for(int i = 0; i < players.size(); i++){
        calcBet(players.get(i));
        players.get(i).clearHand();
      }
      dealer.clearHand();
      //clears every player's hand, including the dealer's

      //try{
        //Thread.sleep(7777);
        //There is absolutly nothing significant with this number
      //} catch(Exception e){
        //System.out.println("Error");
      //}
      */
    
    } while(playersArePlaying());
    //input.close();

    DiscordBot.message("Ending BlackJack Game", channel); //no idea if this is right
    //ok cool it is right
  }

  private void playerTurn(BlackJackPlayer p){

    do{

      //System.out.println("Dealers's cards:");
      //System.out.println("Unknown\n" + dealer.getCard(1));
      //System.out.println();
      DiscordBot.message("Dealer's cards: \nUnknown\n" + dealer.getCard(1).toString(), channel);

      showAllHands();
      
      if(p.isPlaying()){
        //System.out.println("Your hand:");
        DiscordBot.message("p.getName()"
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

        printHand(p.getHand());
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
      
      //choice = input.nextLine();
      //add someway for the user to choose
      //this is gonna involve the command listeners
      
      //choice = choice.toLowerCase().replaceAll(" ", "");
      //sets the users choice to lower case so that their choice is registered regardless of capitalization
      //also clears spaces

      playerChoice(p, choice);
      
    } while(p.isPlaying() || p.splitHandIsPlaying());
  }

  private void playerChoice(BlackJackPlayer p, String s){
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
          System.out.println("You really fd this didnt ya");
      }
      

      //if the player stands, the program will wait a bit?
      //idk I wrote this code when I was dumb and Im still dumb
      //if(!(choice.equals("stand") || choice.equals("s"))){
        //try{
          //Thread.sleep(3000);
        //} catch(Exception e){
          //System.out.println("whatever you did, don't");
        //}
      //}
      //except also im removing this until we do the multi threading thing
  }

  //rewrite this
  private void dealerTurn(){
    
    //try{
      while(dealer.getScore() < 17){
        //System.out.println("Dealers hand");
        DiscordBot.message("Dealer's hand:", channel);
        printHand(dealer.getHand());
        //System.out.println(dealer.getScore());
        dealer.addCard(deck.dealTopCard());
        //Thread.sleep(1700);
      }
    //} catch(Exception e){
      //System.out.println("Geor- I mean Sean");
    //}
    /*
    try{
      Thread.sleep(1700);
    } catch(Exception e){
      System.out.println("Error");
    }
    */
    //DEFINITLY re-add a wait between the dealer drawing cards. It is increadible anticlimatic otherwise
    System.out.println("Dealers hand:");
    printHand(dealer.getHand());
    if(dealer.getScore() > 21){
      System.out.println("Dealer busts with " + dealer.getScore());
      System.out.println();
    }
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
    for(int i = 0; i < hand.size(); i++){
      if(players.get(0).getName().equalsIgnoreCase("CarrotCakeツ#8734")){
        //System.out.println("James of James");
        //easter egg for a friend :)
        DiscordBot.message("J A M E S", channel);
        //still an easter egg lmao
      } else{
        //System.out.println(hand.get(i));
        DiscordBot.message(hand.get(i).toString(), channel);
      }
      
    }
    //System.out.println();
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
    for(int i = 0; i < players.size(); i++){
      System.out.println(players.get(i).getName() + "'s hand:");
      printHand(players.get(i).getHand());
      if(!players.get(i).getSplitHand().isEmpty()){
        System.out.println(players.get(i).getName() + "'s split hand:");
        printHand(players.get(i).getSplitHand());
      }
    }
    //Clears the console and prints all players current hands. Just here for cleanliness
    //doesnt clear the console anymore, just for ugliness (and also because its a discord bot)
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

      DiscordBot.message(name + ", make your bet.", channel);
      //don't know how we will get the bot to listen to massages in here.
      //we could have a /bet command in CommandManaager maybe?
      //idk
    }
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

  public void setChoice(String s){
    choice = s.toLowerCase().replaceAll(" ", "");
  }

  @Override
  public boolean equals(Object obj){
    BlackJackGame b = (BlackJackGame)(obj);

    if(b.getChannel().equals(this.getChannel())){
      return true;
    }
    return false;
  }
}