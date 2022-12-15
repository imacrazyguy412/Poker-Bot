package we.arefarmers;

import javax.security.auth.login.LoginException;

/**
 * Hello world!
 *
 */
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.commands.ButtonListeners;
import we.arefarmers.commands.CommandManager;
import we.arefarmers.listeners.Listeners;

public class DiscordBot 
{
  
  public static JDA bot;
  
    public static void main( String[] args ) throws LoginException, InterruptedException
    {

        bot = JDABuilder.createDefault("MTAzODg2MzI4MDIzMzUxMzAyMQ.GOK4AW.1Ay3x5ePJhhwJMUzFgeDejiIi8z_EEG-xYJsvQ")
        .setActivity(Activity.playing("with your mom"))
        .addEventListeners(new Listeners(), new CommandManager(), new ButtonListeners())
        .build();
    }

    public static void message(String str, MessageChannel channel){
      channel.sendMessage(str).queue();
    }
    public static void message(String str, MessageChannel channel, String player){
      
    }
}

