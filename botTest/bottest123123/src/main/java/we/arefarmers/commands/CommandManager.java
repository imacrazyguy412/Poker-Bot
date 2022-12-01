package we.arefarmers.commands;

import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
//import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import we.games.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import we.games.*;



public class CommandManager extends ListenerAdapter {

    private static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>;
    //no idea if this is a good idea, but it would let us play accross channels/servers as long
    //as we can associate each game with a channel, which we already do.

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
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

            case "playblackjack":
                //event.reply("Currently unavailable because we are incompetent").setEphemeral(true).queue();
                
                //OptionMapping numBlackJackPlayers = event.getOption("amount");
                //int numPlayers = numBlackJackPlayers.getAsInt();
                String blackJackStartingPlayerTag = event.getUser().getAsTag();
                //changed to getAsTag() because it works better with the code
                event.reply("Starting BlackJack").queue();
                MessageChannel blackJackChannel = event.getChannel();
                blackJackGames.add(new BlackJackGame(blackJackChannel, blackJackStartingPlayerTag));
                break;

            case "blackjackbet":
                int blackJackGame = -1;
                int blackJackBetAmount = event.getOption("amount").getAsInt();

                //check if there is a game being played. If so:
                for(int i = 0; i < blackJackGames.size()); i++){
                    if(blackJackGames.get(i).getChannel().equals(event.getChannel())){
                        //get the specific instance of BlackJackGame that is associated with the channel
                        blackJackGame = i;
                        //then break convention out of a loop
                        break;
                    }
                }

                //pass the option blackJackBet into the specific instance of BlackJackGame in the event channel
                blackJackGames.get(i).setChoice(blackJackBetAmount + "");

                break;

            case "playpoker":
                event.reply("Currently Testing").queue();
                new PokerGame(event.getUser().getAsTag(), event.getChannel());
                //I just thought: should we keep an arraylist of blackjack and poker games?
                //I have no idea if we would need to keep track of them like that, but idk

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
        commandData.add(Commands.slash("playblackjack", "Play BlackJack").addOptions(numBlackJackPlayers));

        //bet command, hopefully
        OptionData blackJackBet = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true)
        commandData.add(Commands.slash("blackjackbet", "Place a bet (BlackJack)").addOptions(blackJackBet));

        //playpoker command
        commandData.add(Commands.slash("playpoker", "I sure can't wait to do some poker"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
