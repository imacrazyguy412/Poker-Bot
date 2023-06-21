package io.github.imacrazyguy412.we.arefarmers.listeners.commands.test;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class ButtonTestCommand extends AbstractCommand {

    public ButtonTestCommand(){
        super("buttontest", "test the button");
    }

    @Override
    protected void onExecution(SlashCommandInteractionEvent event){
        System.out.println("button tested");
        MessageCreateData message = new MessageCreateBuilder()
            .addContent("Here is ur button, stupid.")
            .addComponents(
                ActionRow.of(Button.danger("blow-up", "Blow thyself into smithereens"), Button.success("easy", "EZ"))
            )
            .build();

            System.out.println("ur mom");
            event.reply(message).queue();
    }

}
