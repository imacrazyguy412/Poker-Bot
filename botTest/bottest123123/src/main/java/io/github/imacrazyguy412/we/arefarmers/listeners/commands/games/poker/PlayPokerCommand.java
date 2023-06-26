package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.poker;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.poker.PokerGame;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Subcommand(PokerCommand.class)
public class PlayPokerCommand extends AbstractCommand {

    public PlayPokerCommand(){
        super("play", "Start a game of poker");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event){
        if(findGameIn(event.getChannel()) != -1){
            event.reply("There is already a game in this channel.").setEphemeral(true).queue();
            return;
        }
        event.reply("Starting poker...").queue();
        games.add(new PokerGame(event.getUser().getAsTag(), event.getChannel()).start());
    }

}
