package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.poker;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.poker.PokerGame;
import io.github.imacrazyguy412.we.games.poker.PokerPlayer;
import io.github.imacrazyguy412.we.games.util.Joinable;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ShowHand extends AbstractCommand {

    public ShowHand(){
        super("showhand", "View your hand privately.");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event){
        final int gameInstance = games.indexOf(new PokerGame(event.getChannel()));

        if(gameInstance == -1){
            event.reply("There's no game in here!").setEphemeral(true).queue();
        } else{
            int player;

            player = ((Joinable) games.get(gameInstance)).getPlayers().indexOf(new PokerPlayer(event.getUser().getAsTag()));
            if(player == -1){
                event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
            } else{
                String hand = ((PokerPlayer) ((Joinable) games.get(gameInstance)).getPlayers().get(player)).getHand().get(0).toString() + "\n";

                event.reply(hand).setEphemeral(true).queue();
            }
        }
    }

}
