package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.blackjack;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackGame;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

@Subcommand(BlackJackCommand.class)
public class BlackJackBetCommand extends AbstractCommand {

    public BlackJackBetCommand(){
        super("bet", "Place a bet");
        addOption(
            new OptionData(OptionType.INTEGER, "amount", "The amount you would like to bet.", true)
                .setRequiredRange(BlackJackGame.MIN_BET, BlackJackGame.MAX_BET)
        );
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        int gameInstance = games.indexOf(new BlackJackGame(event.getMessageChannel()));

        if(gameInstance < 0){
            event.reply("There is no game in here!").setEphemeral(true).queue();
            return;
        }

        if(!(games.get(gameInstance) instanceof BlackJackGame)){
            event.reply("There is no blackjack game in here!").setEphemeral(true).queue();
            return;
        }

        BlackJackGame game = (BlackJackGame)(games.get(gameInstance));

        int playerInstanceForBet = findPlayerIn(game, event.getUser().getAsTag());

        if(playerInstanceForBet == -1){
            event.reply("Your not in this game!").setEphemeral(true).queue();
            return;
        }

        //if(){
        //    event.reply("It's not time to bet yet!").setEphemeral(true).queue();
        //    return;
        //}

        int bet = event.getOption("amount").getAsInt();
        game.placeBet(bet, playerInstanceForBet);
        event.reply("You bet ***" + bet + "*** chips").queue();
    }

}
