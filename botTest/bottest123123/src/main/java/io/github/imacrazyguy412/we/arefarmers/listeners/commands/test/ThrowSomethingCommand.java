package io.github.imacrazyguy412.we.arefarmers.listeners.commands.test;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ThrowSomethingCommand extends AbstractCommand {

    public ThrowSomethingCommand(){
        super("throw", "test a throw");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event){
        //event.reply("before throw").queue();
        throw new RuntimeException("Hello, this is a test", new RuntimeException("I didn't want to."));
    }

    @Override
    protected String exceptionMessage(Exception e){
        return "oops";
    }

}
