package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games.blackjack;

import io.github.imacrazyguy412.we.annotation.Supercommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


@Supercommand
public class BlackJackCommand extends AbstractCommand {

    public BlackJackCommand(){
        super("blackjack", "Do something in blackjack");
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        CommandManager.getCommandByName(event.getSubcommandName()).execute(event);
    }

}
