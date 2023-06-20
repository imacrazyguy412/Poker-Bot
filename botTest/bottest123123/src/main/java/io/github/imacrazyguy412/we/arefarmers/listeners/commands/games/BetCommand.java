package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.util.Betting;
import io.github.imacrazyguy412.we.games.util.Joinable;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class BetCommand extends AbstractCommand {
    public BetCommand() {
        super("bet", "Place a bet", new OptionData(OptionType.INTEGER, "amount", "The amount you would like to bet", true));
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event) {
                
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
    
}
