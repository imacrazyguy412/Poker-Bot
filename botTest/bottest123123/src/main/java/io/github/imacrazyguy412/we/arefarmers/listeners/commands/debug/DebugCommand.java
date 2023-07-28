package io.github.imacrazyguy412.we.arefarmers.listeners.commands.debug;

import io.github.imacrazyguy412.we.annotation.Supercommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Supercommand
public class DebugCommand extends AbstractCommand {

    public DebugCommand(){
        super("debug", "debug");
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        CommandManager.getCommandByPath(event.getCommandPath()).execute(event);
    }

}
