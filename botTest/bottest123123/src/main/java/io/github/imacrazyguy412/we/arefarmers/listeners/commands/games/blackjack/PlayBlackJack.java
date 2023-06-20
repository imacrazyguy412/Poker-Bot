package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.blackjack;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.blackjack.BlackJackGame;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PlayBlackJack extends AbstractCommand {

    public PlayBlackJack() {
        super("playblackjack", "Play a game of blackjack");
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        if(findGameIn(event.getChannel()) != -1){
            event.reply("There is already a game in this channel.").setEphemeral(true).queue();
            return;
        }

        event.reply("Starting BlackJack").queue();
        games.add(new BlackJackGame(event.getChannel(), event.getUser().getAsTag()).start());
    }
    
}
