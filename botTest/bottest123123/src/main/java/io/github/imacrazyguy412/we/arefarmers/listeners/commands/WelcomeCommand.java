package io.github.imacrazyguy412.we.arefarmers.listeners.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WelcomeCommand extends AbstractCommand {

    public WelcomeCommand(){
        super("welcome", "Get welcomed by the bot");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event){
        String userTag = event.getUser().getAsTag();
        event.reply("Welcome to the server, **" + userTag + "**!").queue();
    }

}
