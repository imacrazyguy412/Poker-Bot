package io.github.imacrazyguy412.we.arefarmers.listeners.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class UrmomCommand extends AbstractCommand {

    public UrmomCommand() {
        super("urmom", "tf, bro",
            new OptionData(OptionType.STRING, "mom", "thine mother", false, false)
        );
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        OptionMapping daddy = event.getOption("mom");
                
        if(daddy != null){
            String  mommy = daddy.getAsString();
            event.reply("I was in bed with ur mom, " + mommy + ", last night").queue();
        } else{
            event.reply("no u").queue();
        }
    }
}
