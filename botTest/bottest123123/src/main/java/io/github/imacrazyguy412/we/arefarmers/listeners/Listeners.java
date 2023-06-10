package io.github.imacrazyguy412.we.arefarmers.listeners;

import org.jetbrains.annotations.NotNull;

import io.github.imacrazyguy412.we.arefarmers.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if(!e.getAuthor().isBot()){
          String message = e.getMessage().getContentRaw();
          DiscordBot.message(message + " :clown:", e.getChannel());  
        }

    }

}
