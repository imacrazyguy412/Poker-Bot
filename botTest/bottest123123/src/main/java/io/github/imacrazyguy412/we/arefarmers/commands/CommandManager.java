package io.github.imacrazyguy412.we.arefarmers.commands;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

//import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
//import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.NotNull;

import io.github.imacrazyguy412.we.games.blackjack.BlackJackGame;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackPlayer;
import io.github.imacrazyguy412.we.games.poker.PokerGame;
import io.github.imacrazyguy412.we.games.poker.PokerPlayer;
import io.github.imacrazyguy412.we.games.util.Betting;
import io.github.imacrazyguy412.we.games.util.Game;
import io.github.imacrazyguy412.we.games.util.Joinable;
import io.github.imacrazyguy412.we.games.util.Player;

import java.util.ArrayList;


public class CommandManager extends ListenerAdapter {

    public static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>();
    public static ArrayList<PokerGame> pokerGames = new ArrayList<PokerGame>();
    public static ArrayList<Game> games = new ArrayList<Game>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        int gameInstance;

        String command = event.getName();
        switch (command){
            case "welcome":
                String userTag = event.getUser().getAsTag();
                event.reply("Welcome to the server, **" + userTag + "**!").queue();
                break;

            case "urmom":
                OptionMapping daddy = event.getOption("mom");
                
                if(daddy != null){
                    String  mommy = daddy.getAsString();
                    event.reply("I was in bed with ur mom, " + mommy + ", last night").queue();
                } else{
                    event.reply("no u").queue();
                }
                break;

            case "bet": //ANCHOR - betting command
                bettingCommand(event);
                break;


            //blackjack commands
            case "playblackjack":

                if(findGameIn(event.getChannel()) != -1){
                    event.reply("There is already a game in this channel.").setEphemeral(true).queue();
                    break;
                }

                event.reply("Starting BlackJack").queue();
                games.add(new BlackJackGame(event.getChannel(), event.getUser().getAsTag()).start());

                //DiscordBot.message("ur mom", event.getChannel());
                break;

            case "blackjackbet": //TODO: make one bet command that will detect the game in the channel
                int blackJackBetAmount = event.getOption("amount").getAsInt();
                event.deferReply();
                //get the instance of the game being played
                gameInstance = blackJackGames.indexOf(new BlackJackGame(event.getChannel()));
                System.out.println("gameInstance: " + gameInstance);

                //check if there is a game being played
                if(gameInstance == -1){
                    event.reply("There's no game in here!").setEphemeral(true).queue();
                } else{
                    int player;

                    //checks if the person is a player in the game
                    player = blackJackGames.get(gameInstance).getPlayers().indexOf(new BlackJackPlayer(event.getUser().getAsTag()));

                    System.out.println("Player: " + player);

                    if(player == -1){
                        event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
                    } else{
                        int temp = blackJackGames.get(gameInstance).getPlayerToBet();

                        if(temp == -1){
                            event.reply("It's not time to bet yet.").setEphemeral(true).queue();
                        } else if(temp == player){
                            //pass the option blackJackBet into the specific instance of BlackJackGame in the event channel
                            blackJackGames.get(gameInstance).setChoice(blackJackBetAmount);
                            event.reply(event.getUser().getAsMention() + ", you bet " + blackJackBetAmount + " chips").queue();
                        } else{
                            event.reply("It's not your turn to bet").setEphemeral(true).queue();
                        }
                        
                    }

                }
                break;

            case "hit":
                //event.deferReply();

                gameInstance = blackJackGames.indexOf(new BlackJackGame(event.getChannel()));
                System.out.println("gameInstance: " + gameInstance);

                //check if there is a game being played
                if(gameInstance == -1){
                    event.reply("There's no game in here!").setEphemeral(true).queue();
                } else{
                    int player;

                    //checks if the person is a player in the game
                    player = blackJackGames.get(gameInstance).getPlayers().indexOf(new BlackJackPlayer(event.getUser().getAsTag()));

                    System.out.println("Player: " + player);

                    if(player == -1){
                        event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
                    } else{
                        int temp = blackJackGames.get(gameInstance).getPlayerToTurn();

                        if(temp == -1){
                            event.reply("It's not time to hit yet.").setEphemeral(true).queue();
                        } else if(temp == player){
                            event.reply("Here's your card").queue();
                            blackJackGames.get(gameInstance).setChoice("hit");
                            
                        } else{
                            event.reply("It's not your turn").setEphemeral(true).queue();
                        }
                    }
                }
                break;

            //poker commands
            case "playpoker":
                event.reply("Currently Testing").setEphemeral(true).queue();
                PokerGame tempPokerGame = new PokerGame(event.getUser().getAsTag(), event.getChannel());
                pokerGames.add(tempPokerGame);
                games.add(tempPokerGame);

                //tempPokerGame.start();
                break;

            case "pokerbet":
                event.reply("Currently Testing").setEphemeral(true).queue();
                int pokerBetAmount = event.getOption("amount").getAsInt();
                
                gameInstance = pokerGames.indexOf(new PokerGame(event.getChannel()));

                if(gameInstance == -1){
                    event.reply("There's no game in here!").setEphemeral(true).queue();
                } else{
                    int player;

                    player = pokerGames.get(gameInstance).getPlayers().indexOf(new PokerPlayer(event.getUser().getAsTag()));
                    if(player == -1){
                        event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
                    } else{ //TODO: add a check to make sure it is the player's turn to bet
                        //pass the option pokerBet into the specific instance of PokerGame in the event channel
                        pokerGames.get(gameInstance).setChoice(pokerBetAmount);
                    }
                }
                break;

            case "showhand":
                gameInstance = pokerGames.indexOf(new PokerGame(event.getChannel()));

                if(gameInstance == -1){
                    event.reply("There's no game in here!").setEphemeral(true).queue();
                } else{
                    int player;

                    player = pokerGames.get(gameInstance).getPlayers().indexOf(new PokerPlayer(event.getUser().getAsTag()));
                    if(player == -1){
                        event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
                    } else{
                        String hand = "";

                        //for(Card card : pokerGames.get(gameInstance).getPlayers().get(player).getHand()){
                            //hand += card.toString() + "\n";
                        //}
                        hand += pokerGames.get(gameInstance).getPlayers().get(player).getHand().get(0).toString() + "\n";


                        event.reply(hand).setEphemeral(true).queue();
                    }
                }
                break;

            

            //diceroller command
            case "diceroller":
                //TODO: finish this

                String choice = event.getOption("choice").getAsString();
                int bet = event.getOption("amount").getAsInt();

                //TODO: convert choice to either higher or lower based on what it is.

                Random rand = new Random();

                int roll = (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);

                String response = "You rolled a " + roll + ", and guessed it would be " + choice + " than 7.";

                //TODO: make the player winning or losing affect their coins or whatever they are betting
                if(roll > 7){
                    if(choice.equals("higher")){
                        response += "\nYou win your bet of " + bet;
                    } else{
                        response += "\nYou lose your bet of " + bet;
                    }
                } else if(roll < 7){
                    if(choice.equals("lower")){
                        response += "\nYou win your bet of " + bet;
                    } else{
                        response += "\nYou lose your bet of " + bet;
                    }
                } else{
                    response += "\nYou lose your bet of " + bet + ", damn sevens.";
                }

                event.reply(response).queue();
                break;
            

            case "buttontest":
            System.out.println("button tested");
            MessageCreateData message = new MessageCreateBuilder()
            .addContent("Here is ur button, stupid.")
            .addComponents(
                ActionRow.of(Button.danger("blow-up", "Blow thyself into smithereens"), Button.success("easy", "EZ")))
                .build();

                System.out.println("ur mom");
                event.reply(message).queue();
            break;
                
        }
        
        
    }
    //guild command
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "Get welcomed by the bot"));
        

        //test
        OptionData option1 = new OptionData(OptionType.STRING, "mom", "The name of ur mom");
        commandData.add(Commands.slash("urmom", "tf bro").addOptions(option1));

        //bet command
        OptionData betAmountData = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true);
        commandData.add(Commands.slash("bet", "make a bet").addOptions(betAmountData));

        //playblackjack command
        //OptionData numBlackJackPlayers = new OptionData(OptionType.INTEGER, "amount", "The amount of players.", true);
        commandData.add(Commands.slash("playblackjack", "Play BlackJack"));

        //blackjackbet command
        OptionData blackJackBet = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true);
        commandData.add(Commands.slash("blackjackbet", "Place a bet (BlackJack)").addOptions(blackJackBet));

        commandData.add(Commands.slash("hit", "Take a card (Blackjack)"));

        //playpoker command
        commandData.add(Commands.slash("playpoker", "I sure can't wait to do some poker"));

        //pokerbet command
        OptionData pokerBet = new OptionData(OptionType.INTEGER, "amount", "the amount you want to bet");
        commandData.add(Commands.slash("pokerbet", "Place a bet (Poker)").addOptions(pokerBet));

        //showhand command
        commandData.add(Commands.slash("showhand", "Look at your hand in poker (don't worry, only you can see it)"));

        //diceroller command
        OptionData diceRollerBet = new OptionData(OptionType.INTEGER, "amount", "The amoumnt you want to bet", true);
        OptionData diceRollerChoice = new OptionData(OptionType.STRING, "choice", "Enter \"higher\" or \"lower\"", true);
        commandData.add(Commands.slash("diceroller", "Gamble if 2d6 will roll above or below 7").addOptions(diceRollerBet, diceRollerChoice));

        //button test command
        commandData.add(Commands.slash("buttontest", "test the button"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    /**
     * Finds the game happening in the given channel among {@link #games}
     * @param channel the channel the game you want to find is in
     * @return the index in {@link #games} that the given channel's game can be found,
     * or -1 if the game does not exist
     * @see ArrayList#indexOf(Object)
     */
    private static int findGameIn(MessageChannel channel){
        return games.indexOf(new Game(channel) {
            @Override
            public void play() {} // necassary overrride
        });
    }

    /**
     * Find the player in a given game with a given name
     * @param game the game
     * @param withName the name of the player to search for
     * @return the index of that player in the games list of players
     */
    private static int findPlayerIn(Joinable game, String withName){
        return game.getPlayers().indexOf(new Player(0, withName) {});
    }



    //SECTION - command helper methods

    private static void bettingCommand(SlashCommandInteractionEvent event){
        event.deferReply();
                
        int gameInstance = findGameIn(event.getChannel());

        if(gameInstance < 0){
            event.reply("There is no game in here!").setEphemeral(true).queue();
            return;
        }

        if(!(games.get(gameInstance) instanceof Betting)){
            event.reply("Sorry, you cannot bet in this game.").setEphemeral(true).queue();
            return;
        }

        int playerInstanceForBet = findPlayerIn((Joinable)games.get(gameInstance), event.getUser().getAsTag());

        if(playerInstanceForBet == -1){
            event.reply("Your not in this game!").setEphemeral(true).queue();
            return;
        }

        int playerToBet = ((Betting)games.get(gameInstance)).getPlayerToBet();

        if(playerToBet == -1){
            event.reply("It's not time to bet yet!").setEphemeral(true).queue();
            return;
        }

        if(playerInstanceForBet != playerToBet){
            event.reply("It is not your turn to bet yet!").setEphemeral(true).queue();
            return;
        }

        int bet = event.getOption("amount").getAsInt();
        games.get(gameInstance).setChoice(bet);
        event.reply("You bet ***" + bet + "*** chips").queue();
    }

    //!SECTION
}
