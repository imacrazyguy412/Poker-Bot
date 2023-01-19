package we.arefarmers;

import javax.security.auth.login.LoginException;

/**
 * Hello world!
 *
 */
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import we.arefarmers.commands.CommandManager;
import we.arefarmers.listeners.Listeners;

public class DiscordBot 
{
  
  public static JDA bot;
  
    public static void main( String[] args ) throws LoginException, InterruptedException
    {

        bot = JDABuilder.createDefault(Poop.id)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setActivity(Activity.playing("with your mom"))
        .addEventListeners(new Listeners(), new CommandManager())
        .build();
    }

    public static void message(String str, MessageChannel channel){
      channel.sendMessage(str).queue();
    }
    public static void message(String str, MessageChannel channel, String player){
      
    }
}

