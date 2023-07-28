package io.github.imacrazyguy412.we.arefarmers.listeners.commands.debug;

import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.arefarmers.DiscordBot;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

@Subcommand(DebugCommand.class)
public class SetLogLevelCommand extends AbstractCommand {

    public SetLogLevelCommand(){
        super("setloglevel", "Set the log level (debug)");
        OptionData level = new OptionData(OptionType.STRING, "level", "The log level", true)
            .addChoice("info", "INFO")
            .addChoice("debug", "DEBUG")
            .addChoice("trace", "TRACE")
            .addChoice("warn", "WARN")
            .addChoice("error", "ERROR");
        addOption(level);
    }

    @Override
    public void onExecution(SlashCommandInteractionEvent event){
        DiscordBot.setNewLogLevel(event.getOption("level").getAsString());
        event.reply("Set loglevel").setEphemeral(true).queue();
    }

}
