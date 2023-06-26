package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.poker;

import io.github.imacrazyguy412.we.annotation.Supercommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Supercommand
public class PokerCommand extends AbstractCommand {

    public PokerCommand(){
        super("poker", "Do something in poker.");
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        CommandManager.getCommandByPath(event.getCommandPath()).execute(event);
    }

}
