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
import we.arefarmers.commands.CommandManager;
import we.arefarmers.listeners.Listeners;

//should be imports for saving data to a text file
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.nio.charset.StandardCharsets;

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

    public static void message(String str, MessageChannel channel){
      channel.sendMessage(str);
    }
    
    //https://crunchify.com/java-saving-and-loading-data-from-a-file-simple-production-ready-utility-for-file-readwrite-operation/
    //The website I got this stuff from
    /**
    should write str to the file test.txt
    currently for testing
    Im copying most of this, so dont ask me what it does
    */
    public static void writeToFile(String str){
      final String fileLocation = "Poker-Bot/test.txt";
      
      
      File saveFile = new File(fileLocation);//"Poker-Bot/botTest/bottest123123/src/main/java/we/arefarmers/");
      
      // exists(): Tests whether the file or directory denoted by this abstract pathname exists.
      if(!saveFile.exists()){
        try{
          File directery = new File(saveFile.getParent());
          
          
          if (!directory.exists()) {
                    
            // mkdirs(): Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories.
            // Note that if this operation fails it may have succeeded in creating some of the necessary parent directories.
            directory.mkdirs();
          }
          
          // createNewFile(): Atomically creates a new, empty file named by this abstract pathname if and only if a file with this name does not yet exist.
          // The check for the existence of the file and the creation of the file if it does not exist are a single operation
          // that is atomic with respect to all other filesystem activities that might affect the file.
          saveFile.createNewFile();
        } catch(Exception e){
          System.out.println("Error 1 in we.arefarmers.DiscordBot.writeToFile() (sent by try-catch)");
        }
      }
      
      try{
        // Convenience class for writing character files
        FileWriter saveFileWriter;
        saveFileWriter = new FileWriter(saveFile.getAbsoluteFile(), true);
        
        // Writes text to a character-output stream
        BufferedWriter bufferWriter = new BufferedWriter(saveFileWriter);
        bufferWriter.write(str.toString());
        bufferWriter.close();
        
        System.out.println("Data :" + "str" + ": save in file :" + fileLocation + ":");
      } catch (IOException e){
        System.out.println("Error while saving data :" + str + ": to file loction :" + fileLocation + ":\n" + e.toString());
      }
    }
}

