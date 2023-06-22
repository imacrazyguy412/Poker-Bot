package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.blackjack;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackGame;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackPlayer;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class StandCommand extends AbstractCommand {

    public StandCommand(){
        super("stand", "Stand in blackjack");
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        int gameInstance = games.indexOf(new BlackJackGame(event.getMessageChannel()));

        if(!(games.get(gameInstance) instanceof BlackJackGame)){
            event.reply("You cannot stand in this game.").setEphemeral(true).queue();
            return;
        }

        BlackJackGame game = (BlackJackGame)(games.get(gameInstance));
        
        int player = game.getPlayers().indexOf(new BlackJackPlayer(event.getUser().getAsTag()));

        if(player == -1){
            event.reply("You aren't in this game. Use /join to join it.").setEphemeral(true).queue();
            return;
        }

        if(player != game.getPlayerToTurn()){
            event.reply("It's not your turn yet.").setEphemeral(true).queue();
            return;
        }

        event.reply(event.getUser().getAsTag() + " has stood.").queue();
        game.setChoice("stand");
        
    }

}
