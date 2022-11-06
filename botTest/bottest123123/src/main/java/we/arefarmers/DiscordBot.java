package we.arefarmers;

import javax.security.auth.login.LoginException;

/**
 * Hello world!
 *
 */
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot 
{
  
  public static JDA bot;
  
    public static void main( String[] args ) throws LoginException, InterruptedException
    {

        bot = JDABuilder.createDefault("MTAzODg2MzI4MDIzMzUxMzAyMQ.GsZB3l.uDYX1Zcqcfx_dcSRKS6Jyml__igRoVlsFhagAs")
        .setActivity(Activity.playing("with your mom"))
        .addEventListeners(new ListenToMe())
        .build();
        //new BlackJackGame(new Deck());
        while(true){
            
        }
    }

    //add playBlackJack method for event listeners

}

