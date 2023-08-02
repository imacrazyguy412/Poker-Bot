package io.github.imacrazyguy412.we.arefarmers.listeners.commands;

import java.util.Collection;

import org.jetbrains.annotations.NotNull;

//import io.github.imacrazyguy412.we.annotation.IgnoreAsCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * An interface to allow an object to properly represent a slash
 * command. Each command has a name ({@link #getName()}), description
 * ({@link #getDescription()}), and potentially can have options
 * ({@link #getOptionData()}). If the command is not suppposed to
 * have options, {@link #getOptionData()} should return {@code null},
 * which it will by defualt
 * 
 * @author Matthew "Something Inconspicuous"
 * @see AbstractCommand
 */
//@IgnoreAsCommand
public interface Command {


    /**
     * Get the name of the command that is used in Discord
     * (used as /name)
     * 
     * @return The name of the command
     */
    String getName();

    /**
     * Get the full path of the command
     * 
     * @return The path of the command
     * @see CommandInteractionPayload#getCommandPath()
     */
    String getPath();

    /**
     * Get the description of the command. This is shown to
     * the user when they use the command as a way of explaining
     * what it does
     * 
     * @return The description of the command
     */
    String getDescription();

    /**
     * Get the options that the command uses, such as an amount or
     * another user.
     * 
     * @return A {@link Collection} of the option data
     */
    default Collection<? extends OptionData> getOptionData(){
        return null;
    }

    /**
     * Execute the command.
     * <p>
     * The {@link AbstractCommand} overrides this command to add
     * exception handling and response to the command's actions.
     * 
     * @param event The interaction event that prompted the command's
     * execution.
     */
    void execute(@NotNull SlashCommandInteractionEvent event);

}