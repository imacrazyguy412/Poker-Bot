package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.blackjack;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackGame;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackPlayer;
import io.github.imacrazyguy412.we.games.util.Joinable;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Subcommand(BlackJackCommand.class)
public class HitCommand extends AbstractCommand {

    public HitCommand() {
        super("hit", "Take a card in blackjack");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event) {
        int gameInstance = games.indexOf(new BlackJackGame(event.getChannel()));
        //System.out.println("gameInstance: " + gameInstance);
        
        //check if there is a game being played
        if(gameInstance == -1){
            event.reply("There's no game in here!").setEphemeral(true).queue();
            return;
        }

        if(!(games.get(gameInstance) instanceof BlackJackGame)){
            event.reply("You can't hit in this game.").setEphemeral(true).queue();
            return;
        }

        
        int player;

        //checks if the person is a player in the game
        player = ((Joinable) games.get(gameInstance)).getPlayers().indexOf(new BlackJackPlayer(event.getUser().getAsTag()));

        System.out.println("Player: " + player);

        if(player == -1){
            event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
            return;
        }

        int turn = ((Joinable) games.get(gameInstance)).getPlayerToTurn();

        if(turn == -1){
            event.reply("It's not time to hit yet.").setEphemeral(true).queue();
        } else if(turn == player){
            event.reply("Here's your card.").queue();
            games.get(gameInstance).setChoice("hit");
            
        } else{
            event.reply("It's not your turn.").setEphemeral(true).queue();
        }
    }
    
}
