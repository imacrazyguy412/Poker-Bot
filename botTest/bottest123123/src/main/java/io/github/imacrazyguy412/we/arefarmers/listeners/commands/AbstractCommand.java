package io.github.imacrazyguy412.we.arefarmers.listeners.commands;

import static io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager.games;

import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.NotNull;

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
public abstract class AbstractCommand {
    /** The name of the command. The command is used with {@code /name} */
    protected String name;

    /** A brief description of what the command is supposed to do. */
    protected String description;

    protected Collection<OptionData> optionDataCollection;

    /**
     * By defualt, constructs an empty command, with {@code "null"} as its name and
     * description. Should be overriden to set defualt name and description, as well
     * as option data.
     * 
     * @see #AbstractCommand(String, String)
     */
    public AbstractCommand() {
        this("null");
    }
    
    public AbstractCommand(@NotNull String name){
        this(name, "null");
    }

    public AbstractCommand(@NotNull String name, @NotNull String description){
        this.name = name;
        this.description = description;
        optionDataCollection = null;
    }

    public AbstractCommand(@NotNull String name, @NotNull String description, OptionData... data){
        this(name, description);
        optionDataCollection = new ArrayList<OptionData>(data.length);
        for (OptionData optionData : data) {
            optionDataCollection.add(optionData);
        }
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Collection<? extends OptionData> getOptionData(){
        return optionDataCollection;
    }

    public abstract void invoke(SlashCommandInteractionEvent event);

    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description){
        addOption(type, name, description, false);
    }
    
    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description, boolean required){
        addOption(type, name, description, required, false);
    }
    
    protected void addOption(@NotNull OptionType type, @NotNull String name, @NotNull String description, boolean required, boolean isAutoComplete){
        optionDataCollection.add(new OptionData(type, name, description, required, isAutoComplete));
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
            public void play() {} // necassary overrride
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
    
    @Override
    public String toString() {
        String desc = getDescription();
        if(desc == null){
            return getName();
        }
        return String.format("%s, %s", getName(), getDescription());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AbstractCommand)) return false;

        return getName().equals(((AbstractCommand)obj).getName());
    }
}
