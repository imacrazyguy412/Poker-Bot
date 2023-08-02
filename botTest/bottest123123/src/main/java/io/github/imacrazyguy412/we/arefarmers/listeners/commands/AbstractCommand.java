// Line comment test
//test 2
/*
 * block comment test
 */

/**test2 */

package io.github.imacrazyguy412.we.arefarmers.listeners.commands;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.imacrazyguy412.we.annotation.IgnoreAsCommand;
import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.games.util.Game;
import io.github.imacrazyguy412.we.games.util.Joinable;
import io.github.imacrazyguy412.we.games.util.Player;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * Represents a single command. It is unlikely that any command class
 * will be instantiated more than once.
 * 
 * @author Matthew "Something Inconspicuous"
 */
@IgnoreAsCommand
public abstract class AbstractCommand implements Command {
    protected final Logger log;

    /** The name of the command. The command is used with {@code /name} */
    protected String name;

    /** A brief description of what the command is supposed to do. */
    protected String description;

    protected List<OptionData> optionDataCollection = new ArrayList<>();

    /**
     * By defualt, constructs an empty command, with {@code "null"} as its name and
     * an empty description. Should be overriden to set defualt name and description.
     * 
     * @see #AbstractCommand(String, String)
     */
    public AbstractCommand() {
        this("null");
    }
    

    /**
     * Create a command with a given name and {@code ""} as its description
     * 
     * @param name The name of the command
     * @see #AbstractCommand(String, String)
     */
    public AbstractCommand(@NotNull String name){
        this(name, "");
    }

    /**
     * Create a command with a given name and description.
     * 
     * @param name The name of the command
     * @param description A brief description of the command
     */
    protected AbstractCommand(@NotNull String name, @NotNull String description){
        this.name = name;
        this.description = description;
        log = LoggerFactory.getLogger(getClass());
    }

    /**
     * Create a command with the given name and description, as well as given option data.
     * 
     * @param name The name of the command
     * @param description A brief description of the command
     * @param data A list of {@link OptionData} to give the command
     */
    protected AbstractCommand(@NotNull String name, @NotNull String description, OptionData... data){
        this(name, description);
        for (OptionData optionData : data) {
            addOption(optionData);
        }
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getPath(){
        Subcommand subcmd = getClass().getDeclaredAnnotation(Subcommand.class);

        if(subcmd == null){
            return getName();
        }

        try {
            Command supcmdInstance = subcmd.value().getConstructor().newInstance();
            return String.format("%s/%s", supcmdInstance.getName(), getName());
        } catch (InstantiationException e) {
            log.error("Error in finding path of " + this, e);
        } catch (IllegalAccessException e) {
            log.error("Error in finding path of " + this, e);
        } catch (IllegalArgumentException e) {
            log.error("Error in finding path of " + this, e);
        } catch (InvocationTargetException e) {
            log.error("Error in finding path of " + this, e);
        } catch (NoSuchMethodException e) {
            log.error("Error in finding path of " + this, e);
        } catch (SecurityException e) {
            log.error("Error in finding path of " + this, e);
        }
        // To signify which command had an exception
        return "/" + getName() + "/";
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public Collection<? extends OptionData> getOptionData(){
        return optionDataCollection.isEmpty() ? null : optionDataCollection;
    }

    protected abstract void onExecution(SlashCommandInteractionEvent event);

    @Override
    public final void execute(@NotNull SlashCommandInteractionEvent event){
        try {
            onExecution(event);
        } catch (Exception e) {
            log.error("Exception in execution of command: " + this, e);

            // Check if event was acknowledged
            if(event.getHook().getInteraction().isAcknowledged()){
                event.getHook()
                    .sendMessage(fullExceptionMessage(e))
                    .setEphemeral(true)
                    .queue();
            } else{
                event.reply(fullExceptionMessage(e))
                    .setEphemeral(true)
                    .queue();
            }
        }
    }

    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description){
        addOption(type, name, description, false);
    }
    
    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description, boolean required){
        addOption(type, name, description, required, false);
    }
    
    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description, boolean required, boolean isAutoComplete){
        addOption(new OptionData(type, name, description, required, isAutoComplete));
    }

    protected void addOption(@NotNull OptionData option){
        optionDataCollection.add(option);
    }
    
    /**
     * Finds the game happening in the given channel among {@link #games}
     * @param channel the channel the game you want to find is in
     * @return the index in {@link #games} that the given channel's game can be found,
     * or -1 if the game does not exist
     * @see ArrayList#indexOf(Object)
     */
    protected static int findGameIn(MessageChannel channel){
        return games.indexOf(new Game(channel) {
            @Override
            protected void play() {} // necassary overrride
        });
    }

    /**
     * Find the player in a given game with a given name
     * @param game the game
     * @param withName the name of the player to search for
     * @return the index of that player in the games list of players
     */
    protected static int findPlayerIn(Joinable game, String withName){
        return game.getPlayers().indexOf(new Player(0, withName) {});
    }

    protected String exceptionMessage(Exception e){
        return String.valueOf(e);
    }
    
    private final String fullExceptionMessage(Exception e){
        return String.format("Something went wrong:\n%s\nPlease try again or contact an admin of this server", exceptionMessage(e));
    }
    
    /**
     * Returns a string representation of this command in the
     * format of <p>
     * {@code "name <requiredoption> [optionaloption]: description"}
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getPath());

        @SuppressWarnings("unchecked")
        List<OptionData> options = (List<OptionData>) getOptionData();

        if(options != null){
            for(OptionData option : options){
                str.append(' ');
                if(option.isRequired()){
                    str.append('<').append(option.getName()).append('>');
                } else {
                    str.append('[').append(option.getName()).append(']');
                }
            }
        }

        String desc = getDescription();
        if(desc != null){
            str.append(": " + desc);
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AbstractCommand)) return false;

        return getName().equals(((Command)obj).getName());
    }
}
