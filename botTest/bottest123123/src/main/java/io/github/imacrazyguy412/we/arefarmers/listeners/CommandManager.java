package io.github.imacrazyguy412.we.arefarmers.listeners;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import io.github.imacrazyguy412.we.annotation.IgnoreAsCommand;
import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.annotation.Supercommand;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.Command;
import io.github.imacrazyguy412.we.games.util.Game;
//import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;


public class CommandManager extends ListenerAdapter {

    public static final String CMD_OBJECTS_PATH = "botTest\\bottest123123\\src\\main\\java\\io\\github\\imacrazyguy412\\we\\arefarmers\\listeners\\commands";

    protected static Map<String, Command> commandMap; 

    //public static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>();
    //public static ArrayList<PokerGame> pokerGames = new ArrayList<PokerGame>();
    public static ArrayList<Game> games = new ArrayList<Game>();
    

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();

        System.out.format("[CommandManager] Recieved Command: %s\n", getCommandByPath(event.getCommandPath()));

        commandMap.get(command).execute(event);
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

        initCommandMap();
        
        // Add the commands that were found to the bot.
        Map<Command, CommandData> cmdToCmdDataMap = new HashMap<Command, CommandData>();
        Map<Command, List<SubcommandData>> subcmdDataToDoMap = new HashMap<Command, List<SubcommandData>>();
        commandMap.forEach((name, command) -> {
            System.out.println("[CommandManager] Converting command: " + command);

            SlashCommandData slash = Commands.slash(command.getName(), command.getDescription());
            
            List<SubcommandData> subcmdDataToAdd = subcmdDataToDoMap.get(command);
            if(subcmdDataToAdd != null){
                slash.addSubcommands(subcmdDataToAdd);
            }
            
            final Supercommand supcmd = command.getClass().getDeclaredAnnotation(Supercommand.class);
            
            if(supcmd != null){
                cmdToCmdDataMap.put(command, slash);
            }

            final Subcommand subcmd = command.getClass().getDeclaredAnnotation(Subcommand.class);
            if(subcmd != null){
                try {
                    // Get this subcommand's super command
                    Command supercmd = commandMap.get(subcmd.value().getConstructor().newInstance().getName());
                    // And its data
                    SlashCommandData supcmdData = (SlashCommandData) cmdToCmdDataMap.get(supercmd);
                    
                    // If its data is retrievable, we know we've already handled the super command
                    if(supcmdData == null){
                        // So we need to add it to out todo list
                        
                        List<SubcommandData> subcmdList = subcmdDataToDoMap.get(supercmd);
                        
                        // Check if the list already exists
                        if(subcmdList == null){
                            List<SubcommandData> subcmdToAdd = new ArrayList<SubcommandData>();

                            Collection<? extends OptionData> subcmdOptions = command.getOptionData();
                            //System.out.println("[CommandManager] Found options: " + subcmdOptions);
                            if(subcmdOptions == null){
                                subcmdToAdd.add(new SubcommandData(command.getName(), command.getDescription()));
                            } else {
                                subcmdToAdd.add(
                                    new SubcommandData(command.getName(), command.getDescription())
                                        .addOptions(subcmdOptions)
                                );
                            }

                            subcmdDataToDoMap.put(supercmd, subcmdToAdd);
                        } else{
                            Collection<? extends OptionData> subcmdOptions = command.getOptionData();
                            //System.out.println("[CommandManager] Found options: " + subcmdOptions);
                            if(subcmdOptions == null){
                                subcmdList.add(new SubcommandData(command.getName(), command.getDescription()));
                            } else {
                                subcmdList.add(
                                    new SubcommandData(command.getName(), command.getDescription())
                                        .addOptions(subcmdOptions)
                                );
                            }
                        }
                    } else{
                        // Otherwise, we can simply append our subcommand data to the command data
                        // Along with its options, if present
                        Collection<? extends OptionData> subcmdOptions = command.getOptionData();
                        //System.out.println("[CommandManager] Found options: " + subcmdOptions);
                        if(subcmdOptions == null){
                            supcmdData.addSubcommands(new SubcommandData(slash.getName(), slash.getDescription()));
                        } else {
                            //System.out.println("[CommandManager] Added options");
                            supcmdData.addSubcommands(
                                new SubcommandData(slash.getName(), slash.getDescription())
                                    .addOptions(subcmdOptions)
                            );
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                Collection<? extends OptionData> data = command.getOptionData();
                if(data != null){
                    commandData.add(slash.addOptions(data));
                } else{
                    commandData.add(slash);
                }
            }
            
            
        });
        
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
    
    /**
     * Search through the directory specified by {@link #CMD_OBJECTS_PATH}
     * (currently {@value #CMD_OBJECTS_PATH}) and for each java source code
     * file found, get its class. If it is a command class (it implements 
     * {@link Command}), then we add it to our command map.
     * <p>
     * To add new commands, you just create a new file in "commands"
     */
    private static void initCommandMap(){
        commandMap = new HashMap<String, Command>();
        
        File dir = new File(CMD_OBJECTS_PATH);
        File[] listing = dir.listFiles();

        if(listing != null){
            // Search through every file in the "commands" folder
            mapClassesFrom(listing);
        } else {
            //System.out.println("[CommandManager] dir: " + dir);
        }
        //System.out.println("[CommandManager] " + commandMap);
    }

    private static void mapClassesFrom(File[] listing) {
        for(int i = 0; i < listing.length; i++){
            mapClassFrom(listing[i]);
        }
    }

    private static void mapClassFrom(File file) {
        if(file.isFile()){
            //Get the class name
            final String className = file.getPath()
                .replace('\\', '.') // Replace backslashes with dots (get package name)
                .replace("botTest.bottest123123.src.main.java.", "") // Remove the part of the path that is not needed
                .replaceFirst("(?s)(.*).java", "$1") // Remove .java file extension
                .replaceFirst("(?s)(.*).jav", "$1"); // Remove .jav file extension

            // Start as Command to make the warnings shut up
            Class<? extends Command> cmdClass = Command.class; 

            try {

                // Get the class in the file
                Class<?> fileClass = Class.forName(className);

                // Make sure it is supposed to be a command
                if(Command.class.isAssignableFrom(fileClass)){

                    // These annotations are sometimes hard to work with
                    @SuppressWarnings("unchecked")
                    Class<? extends Command> cmdClasstemp = (Class<? extends Command>)fileClass;

                    // Store that in a properly constrained class object
                    cmdClass = cmdClasstemp; 
                } else {
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // Ignore annotated command, as it is not an actual command
            if(cmdClass.getDeclaredAnnotation(IgnoreAsCommand.class) != null){
                return;
            }

            try {

                // Instantiate the command
                Command cmdInstance = cmdClass.getConstructor().newInstance();

                // ...and finally map it
                commandMap.putIfAbsent(cmdInstance.getPath(), cmdInstance);
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

    public static Command getCommandByPath(String name){
        return commandMap.get(name);
    }
}
