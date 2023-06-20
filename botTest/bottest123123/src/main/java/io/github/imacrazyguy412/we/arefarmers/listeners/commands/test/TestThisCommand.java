package io.github.imacrazyguy412.we.arefarmers.listeners.commands.test;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class TestThisCommand extends AbstractCommand {

    public TestThisCommand() {
        super("testme", "testity test");
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event) {
        System.out.println("Tested");
        event.reply("Hello").queue();
    }
}
