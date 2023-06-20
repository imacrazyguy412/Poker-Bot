package io.github.imacrazyguy412.we.arefarmers.listeners;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import io.github.imacrazyguy412.we.games.util.Game;
//import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


public class CommandManager extends ListenerAdapter {

    public static final String CMD_OBJECTS_PATH = "botTest\\bottest123123\\src\\main\\java\\io\\github\\imacrazyguy412\\we\\arefarmers\\listeners\\commands";

    protected static Map<String, AbstractCommand> commandMap; 

    //public static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>();
    //public static ArrayList<PokerGame> pokerGames = new ArrayList<PokerGame>();
    public static ArrayList<Game> games = new ArrayList<Game>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();

        System.out.println(commandMap.get(command));

        commandMap.get(command).invoke(event);
        //switch (command){

            //poker commands

            //TODO - test the generic bet command on poker games
            //case "pokerbet":
            //    event.reply("Currently Testing").setEphemeral(true).queue();
            //    int pokerBetAmount = event.getOption("amount").getAsInt();
            //    
            //    gameInstance = pokerGames.indexOf(new PokerGame(event.getChannel()));
            //
            //    if(gameInstance == -1){
            //        event.reply("There's no game in here!").setEphemeral(true).queue();
            //    } else{
            //        int player;
            //
            //        player = pokerGames.get(gameInstance).getPlayers().indexOf(new PokerPlayer(event.getUser().getAsTag()));
            //        if(player == -1){
            //            event.reply("You're not in the game, type /join to join it.").setEphemeral(true).queue();
            //        } else{ //TODO: add a check to make sure it is the player's turn to bet
            //            //pass the option pokerBet into the specific instance of PokerGame in the event channel
            //            pokerGames.get(gameInstance).setChoice(pokerBetAmount);
            //        }
            //    }
            //    break;
                
        //}
        
        
    }
    //guild command
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        //commandData.add(Commands.slash("welcome", "Get welcomed by the bot"));
        

        //test
        //OptionData option1 = new OptionData(OptionType.STRING, "mom", "The name of ur mom");
        //commandData.add(Commands.slash("urmom", "tf bro").addOptions(option1));

        //bet command
        //OptionData betAmountData = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true);
        //commandData.add(Commands.slash("bet", "make a bet").addOptions(betAmountData));

        //playblackjack command
        //OptionData numBlackJackPlayers = new OptionData(OptionType.INTEGER, "amount", "The amount of players.", true);
        //commandData.add(Commands.slash("playblackjack", "Play BlackJack"));

        //blackjackbet command
        //OptionData blackJackBet = new OptionData(OptionType.INTEGER, "amount", "The amount you want to bet", true);
        //commandData.add(Commands.slash("blackjackbet", "Place a bet (BlackJack)").addOptions(blackJackBet));

        //commandData.add(Commands.slash("hit", "Take a card (Blackjack)"));

        //playpoker command
        //commandData.add(Commands.slash("playpoker", "I sure can't wait to do some poker"));

        //pokerbet command
        //OptionData pokerBet = new OptionData(OptionType.INTEGER, "amount", "the amount you want to bet");
        //commandData.add(Commands.slash("pokerbet", "Place a bet (Poker)").addOptions(pokerBet));

        //showhand command
        //commandData.add(Commands.slash("showhand", "Look at your hand in poker (don't worry, only you can see it)"));

        //diceroller command
        //OptionData diceRollerBet = new OptionData(OptionType.INTEGER, "amount", "The amoumnt you want to bet", true);
        //OptionData diceRollerChoice = new OptionData(OptionType.STRING, "choice", "Enter \"higher\" or \"lower\"", true);
        //commandData.add(Commands.slash("diceroller", "Gamble if 2d6 will roll above or below 7").addOptions(diceRollerBet, diceRollerChoice));

        //button test command
        //commandData.add(Commands.slash("buttontest", "test the button"));

        initCommandMap();
        
        // Add the commands that were found to the bot.
        commandMap.forEach((name, command) -> {
            Collection<? extends OptionData> data = command.getOptionData();
            if(data != null){
                commandData.add(Commands.slash(name, command.getDescription()).addOptions(data));
            } else{
                commandData.add(Commands.slash(name, command.getDescription()));
            }
        });
        
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
    
    /**
     * Search through the directory specified by {@link #CMD_OBJECTS_PATH}
     * (currently {@value #CMD_OBJECTS_PATH}) and for each java source code
     * file found, get its class. If it is a command class (it extends 
     * {@link AbstractCommand}), then we add it to our command map.
     * <p>
     * To add new commands, you just create a new file in cmdobjects
     */
    private void initCommandMap(){
        commandMap = new HashMap<String, AbstractCommand>();
        
        File dir = new File(CMD_OBJECTS_PATH);
        File[] listing = dir.listFiles();

        if(listing != null){
            // Search through every file in the "cmdobjects" folder
            mapClassesFrom(listing);
        } else {
            System.out.println("dir: " + dir);
        }
        System.out.println(commandMap);
    }

    private void mapClassesFrom(File[] listing) {
        for(int i = 0; i < listing.length; i++){
            mapClassFrom(listing[i]);
        }
    }

    private void mapClassFrom(File file) {
        if(file.isFile()){
            //Get the class name
            final String className = file.getPath()
                .replace("\\", ".") // Replace backslashes with dots (get package name)
                .replace("botTest.bottest123123.src.main.java.", "") // Remove the part of the path that is not needed
                .replaceFirst("(?s)(.*).java", "$1") // Remove .java file extension
                .replaceFirst("(?s)(.*).jav", "$1"); // Remove .jav file extension

            // Start as AbstractCommand to make the warnings shut up
            Class<? extends AbstractCommand> cmdClass = AbstractCommand.class; 

            try {

                // Get the class in the file
                Class<?> fileClass = Class.forName(className);

                // Make sure it is supposed to be a command
                if(AbstractCommand.class.isAssignableFrom(fileClass)){

                    // These annotations are sometimes hard to work with
                    @SuppressWarnings("unchecked")
                    Class<? extends AbstractCommand> cmdClasstemp = (Class<? extends AbstractCommand>)fileClass;

                    // Store that in a properly constrained class object
                    cmdClass = cmdClasstemp; 
                } else {
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // Ignore Abstract command, as it is abstract and not an actual command
            if(cmdClass == AbstractCommand.class){
                return;
            }

            try {

                // Instantiate the command
                AbstractCommand cmdInstance = cmdClass.getConstructor().newInstance();

                // ...and finally map it
                commandMap.putIfAbsent(cmdInstance.getName(), cmdInstance);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) { // Should never happen
                e.printStackTrace(); 
            }

        } else{
            // If the file is not a source code file and is a directory,
            // we search all of the files inside of it.
            File[] listing = file.listFiles();

            if(listing != null){
                mapClassesFrom(listing);
            }
        }
    }
}
