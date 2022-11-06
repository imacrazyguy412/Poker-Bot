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

        bot = JDABuilder.createDefault("MTAzODg2MzI4MDIzMzUxMzAyMQ.GLvQMf.G1ZEPpix5LqUT6spJQL4XlRDrb5wlGzgEY64dQ")
        .setActivity(Activity.playing("with your mom"))
        .addEventListeners(new Listeners(), new CommandManager())
        .build();
        //new BlackJackGame(new Deck());
        while(true){
            
        }
    }

    //add playBlackJack method for event listeners

}

