package we.arefarmers;

import javax.security.auth.login.LoginException;

/**
 * Hello world!
 *
 */
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import we.arefarmers.commands.CommandManager;
import we.arefarmers.listeners.Listeners;

public class DiscordBot 
{
  
  public static JDA bot;
  
    public static void main( String[] args ) throws LoginException, InterruptedException
    {

        bot = JDABuilder.createDefault("")
        .setActivity(Activity.playing("with your mom"))
        .addEventListeners(new Listeners(), new CommandManager())
        .build();
    }

    //add playBlackJack method for event listeners

}

