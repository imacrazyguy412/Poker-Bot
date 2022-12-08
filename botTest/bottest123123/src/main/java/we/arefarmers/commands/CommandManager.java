package we.arefarmers.commands;

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


import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//import we.arefarmers.DiscordBot;
import we.games.*;


public class CommandManager extends ListenerAdapter {

    public static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>();
    public static ArrayList<PokerGame> pokerGames = new ArrayList<PokerGame>();

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


            //blackjack commands
            case "playblackjack":
                //event.reply("Currently unavailable because we are incompetent").setEphemeral(true).queue();
                
                //OptionMapping numBlackJackPlayers = event.getOption("amount");
                //int numPlayers = numBlackJackPlayers.getAsInt();
                String blackJackStartingPlayerTag = event.getUser().getAsTag();
                //changed to getAsTag() because it works better with the code
                event.reply("Starting BlackJack").queue();
                //MessageChannel blackJackChannel = event.getChannel();
                blackJackGames.add(new BlackJackGame(event.getChannel(), blackJackStartingPlayerTag));

                

                //DiscordBot.message("ur mom", event.getChannel());
                break;

            case "blackjackbet":
                int blackJackBetAmount = event.getOption("amount").getAsInt();

                //get the instance of the game being played
                gameInstance = blackJackGames.indexOf(new BlackJackGame(event.getChannel()));

                //check if there is a game being played
                if(gameInstance == -1){
                    event.reply("There's no game in here!").setEphemeral(true).queue();
                } else{
                    int player;

                    //checks if the person is a player in the game
                    player = blackJackGames.get(gameInstance).getPlayers().indexOf(new BlackJackPlayer(event.getUser().getAsTag()));
                    if(player == -1){
                        event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
                    } else{ //TODO: add a check to make sure it is the player's turn to bet
                        //pass the option blackJackBet into the specific instance of BlackJackGame in the event channel
                        blackJackGames.get(gameInstance).setChoice(blackJackBetAmount + "");
                    }

                }
                break;


            //poker commands
            case "playpoker":
                event.reply("Currently Testing").setEphemeral(true).queue();
                new PokerGame(event.getUser().getAsTag(), event.getChannel());
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
                        pokerGames.get(gameInstance).setChoice(pokerBetAmount + "");
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

        //playblackjack command
        //OptionData numBlackJackPlayers = new OptionData(OptionType.INTEGER, "amount", "The amount of players.", true);
        commandData.add(Commands.slash("playblackjack", "Play BlackJack"));

        //blackjackbet command
        OptionData blackJackBet = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true);
        commandData.add(Commands.slash("blackjackbet", "Place a bet (BlackJack)").addOptions(blackJackBet));

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

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
