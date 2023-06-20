package io.github.imacrazyguy412.we.arefarmers.commands.cmdobjects;

import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
     * By defualt, constructs an empty command, with {@code null} name and description.
     * Should be overriden to set defualt name and description, as well as option data.
     * 
     * @see #AbstractCommand(String, String)
     * 
     */
    public AbstractCommand() {
        this("null");
    }
    
    protected AbstractCommand(@NotNull String name){
        this(name, "null");
    }

    public AbstractCommand(@NotNull String name, @NotNull String description){
        this.name = name;
        this.description = description;
        optionDataCollection = null;
    }

    public AbstractCommand(@NotNull String name, @NotNull String description, OptionData... data){
        this(name, description);
        optionDataCollection = new ArrayList<>(data.length);
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
