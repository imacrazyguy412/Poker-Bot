package we.arefarmers.listeners;

import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import we.arefarmers.DiscordBot;

public class Listeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {
        if(!e.getAuthor().isBot()){
          String message = e.getMessage().getContentRaw();
          DiscordBot.message(message + ":clown:", e.getChannel());  
        }

    }

}
