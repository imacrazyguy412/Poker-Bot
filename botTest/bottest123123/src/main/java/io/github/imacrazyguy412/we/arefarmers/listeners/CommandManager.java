package io.github.imacrazyguy412.we.arefarmers.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.imacrazyguy412.we.annotation.IgnoreAsCommand;
import io.github.imacrazyguy412.we.annotation.Subcommand;
import io.github.imacrazyguy412.we.annotation.Supercommand;
import io.github.imacrazyguy412.we.arefarmers.Poop;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.Command;
import io.github.imacrazyguy412.we.games.util.Game;
import net.dv8tion.jda.api.entities.Guild;
//import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;


public class CommandManager extends ListenerAdapter {

    /**
     *
     */
    private static final String CMD_OBJECTS_LIST_PATH_STRING = "botTest\\bottest123123\\src\\main\\java\\io\\github\\imacrazyguy412\\we\\arefarmers\\listeners\\CommandObjects.txt";

    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);

    public static final String CMD_OBJECTS_PATH = "botTest\\bottest123123\\src\\main\\java\\io\\github\\imacrazyguy412\\we\\arefarmers\\listeners\\commands";

    protected static Map<String, Command> commandMap;

    protected static Map<String, Consumer<SlashCommandInteractionEvent>> commandConsumerMap;

    //public static ArrayList<BlackJackGame> blackJackGames = new ArrayList<BlackJackGame>();
    //public static ArrayList<PokerGame> pokerGames = new ArrayList<PokerGame>();
    public static List<Game> games = new ArrayList<Game>();
    
    @Override
    public void onReady(ReadyEvent event){
        // Get rid of the command map and let the garbage collector deal with it, as we don't 
        // need all the information anymore
        commandMap = null;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        ///String command = event.getName();

        //log.info("Recieved Command \"{}\" from server {}", getCommandByPath(event.getCommandPath()), guildToString(event.getGuild()));

        //commandMap.get(command).execute(event);

        String commandPath = event.getCommandPath();

        commandConsumerMap.get(commandPath).accept(event);
        
        
    }

    private static String guildToString(Guild guild){
        return String.format("\"%s\" #%d", guild.getName(), guild.getIdLong());
    }
    
    //guild command
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        log.debug("Getting ready for server: {}", guildToString(event.getGuild()));

        List<CommandData> commandData = new ArrayList<>();

        if(commandMap == null){
            initCommandMap();
        }
        
        // Add the commands that were found to the bot.
        Map<Command, CommandData> cmdToCmdDataMap = new HashMap<Command, CommandData>();
        Map<Command, List<SubcommandData>> subcmdDataToDoMap = new HashMap<Command, List<SubcommandData>>();
        commandMap.forEach((name, command) -> {
            
            log.debug("Converting command: {}", command);
            
            if(command.getPath().startsWith("debug")){
                if(event.getGuild().getIdLong() != Poop.TESTING_SERVER){
                    return;
                }
                log.warn("Debug command \"{}\" given to server: {}", command, guildToString(event.getGuild()));
            }

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
                            log.debug("Found options: {}", subcmdOptions);
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
                            log.debug("Found options: {}", subcmdOptions);
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
                        log.debug("Found options: {}", subcmdOptions);
                        if(subcmdOptions == null){
                            supcmdData.addSubcommands(new SubcommandData(slash.getName(), slash.getDescription()));
                        } else {
                            log.debug("Added options");
                            supcmdData.addSubcommands(
                                new SubcommandData(slash.getName(), slash.getDescription())
                                    .addOptions(subcmdOptions)
                            );
                        }
                    }
                } catch (InstantiationException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
                } catch (IllegalAccessException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
                } catch (IllegalArgumentException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
                } catch (InvocationTargetException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
                } catch (NoSuchMethodException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
                } catch (SecurityException e) {
                    log.error(String.format("Error in command \"%s\" creation.", command), e);
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
     * Search through all files specified by {@value #CMD_OBJECTS_LIST_PATH_STRING},
     * and for each java source code file found, get its class. If it is a command
     * class (it implements {@link Command}), then we add it to our command map.
     * <p>
     * To add new commands, specify them in the txt file.
     */
    private static void initCommandMap(){
        commandMap = new HashMap<String, Command>();
        commandConsumerMap = new HashMap<>();

        //NOTE - This all works fine on windows 10, but I have no way to know if
        // unix and mac systems respond the same way to paths with backslashes

        final File commandsListFile = new File(CMD_OBJECTS_LIST_PATH_STRING);
        
        List<String> paths = new ArrayList<String>();
        try (Scanner fileScanner = new Scanner(commandsListFile)) {

            while(fileScanner.hasNextLine()){
                paths.add(
                    fileScanner.nextLine()
                        // Remove comments (anything after a '//' or '#') and ALL spaces and tabs
                        .replaceAll("((\\/\\/|#).*)| +|\t+", "")
                        // Replace any single slashes with a backlash
                        .replace("/", "\\")
                );
            }
            
        } catch (FileNotFoundException e) {
            log.error("Could not read Command Objects file.", e);
            return;
        }

        log.debug("paths: {}", paths);

        Set<String> pathBlacklist = new HashSet<String>();

        for(String path : paths){
            if(path.startsWith("!")){
                // Substring to remove exclemation mark
                pathBlacklist.add(path.substring(1).replaceAll("\\\\$", ""));
            }
        }
        

        for(String path : paths){
            if(path.isEmpty()){
                continue;
            }

            if(path.endsWith("\\")){
                // If the file is a directory, we check all files in it's listings
                File dir = new File(path);
                File[] listing = dir.listFiles();
        
                if(listing != null){
                    // Search through every file in the "commands" folder
                    mapClassesFrom(listing, pathBlacklist);
                }
            } else {
                File file = new File(path);

                mapClassFrom(file, pathBlacklist);
            }
        }
    }

    private static void mapClassesFrom(File[] listing, Collection<? extends String> pathBlacklist) {
        for(int i = 0; i < listing.length; i++){
            mapClassFrom(listing[i], pathBlacklist);
        }
    }

    private static void mapClassFrom(File file, Collection<? extends String> pathBlacklist) {
        if(pathBlacklist.contains(file.getPath())){
            return;
        }

        if(file.isFile()){

            log.debug("Parsing commands from file: \"{}\"", file.getPath());

            //Get the class name
            //final String className = file.getPath()
            //    .replace('\\', '.') // Replace backslashes with dots (get package name)
            //    .replace("botTest.bottest123123.src.main.java.", "") // Remove the part of the path that is not needed
            //    .replaceFirst("\\.[Jj][Aa][Vv][Aa]?$", ""); // Remove .jav(a) file extension


            // New method to get the class. This one actually looks for the package and class name
            // in the file's contents rather than just assuming that specific conventions are followed.
            String className = "null.null";

            try (Scanner fileScanner = new Scanner(file)) {
                String packageName = null;
                String simpleClassName = null;

                while(fileScanner.hasNext()){
                    String token = fileScanner.next();

                    // Skip commented lines
                    if(token.startsWith("//")){
                        //inCommentLine = true;
                        fileScanner.nextLine();
                        continue;
                    }

                    // Skip over comment blocks
                    if(token.contains("/*") && !token.contains("*/")){
                        //inCommentBlock = true;

                        // Skip to the next commnet closer 
                        Pattern oldDelimiter = fileScanner.delimiter();
                        
                        String skipped = fileScanner.useDelimiter("\\*/").next();

                        log.debug("Skipped token: \"{}\"", skipped);

                        fileScanner.useDelimiter(oldDelimiter);
                        continue;
                    }

                    //if(token.endsWith("*/")){
                    //    inCommentBlock = false;
                    //    continue;
                    //}

                    //if(token.endsWith("\n")){
                    //    inCommentLine = false;
                    //    fileScanner.skip("\n");
                    //    continue;
                    //}

                    //if(inCommentBlock || inCommentLine){
                    //    log.trace("skipping commented token {}", token);
                    //    continue;
                    //}
                    
                    // Find package name
                    if(token.equals("package")){
                        Pattern oldDelimiter = fileScanner.delimiter();

                        packageName = fileScanner.useDelimiter("\\s*;\\s*").next().trim();

                        fileScanner.useDelimiter(oldDelimiter);

                        log.trace("found package name {}", packageName);
                        continue;
                    }

                    //final Pattern classNamesRegex = Pattern.compile("class|@?interface|enum");

                    //if(classNamesRegex.matcher(token).matches()){
                    if(token.equals("class")){
                        simpleClassName = fileScanner.next();
                        log.trace("found simple class name {}", simpleClassName);
                        continue;
                    }

                    if(packageName != null && simpleClassName != null){
                        break;
                    }
                }

                className = String.format("%s.%s", packageName, simpleClassName);
                log.trace("found full class name {}", className);
            } catch (FileNotFoundException e) {
                log.error("File not found.", e);
            }

            // Start as Command to make the warnings shut up
            Class<? extends Command> cmdClass = Command.class; 

            try {

                // Get the class in the file
                Class<?> fileClass = Class.forName(className);

                // Make sure it is supposed to be a command
                if(Command.class.isAssignableFrom(fileClass)){

                    // These annotations are sometimes hard to work with
                    @SuppressWarnings("unchecked")
                    Class<? extends Command> cmdClassTemp = (Class<? extends Command>)fileClass;

                    // Store that in a properly constrained class object
                    cmdClass = cmdClassTemp; 
                } else {
                    return;
                }
            } catch (ClassNotFoundException e) {
                log.error("Command not found", e);;
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
                commandConsumerMap.putIfAbsent(cmdInstance.getPath(), cmdInstance::execute);
            } catch (InstantiationException e) {
                log.error("Could not instatiate command object", e);
            } catch (InvocationTargetException e) {
                log.error("Could not instatiate command object", e);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) { // Should never happen
                log.error("Could not instatiate command object", e); 
            }

        } else{
            // If the file is not a source code file and is a directory,
            // we search all of the files inside of it.
            File[] listing = file.listFiles();

            if(listing != null){
                log.debug("Parsing files in listing: \"{}\"", file.getPath());
                mapClassesFrom(listing, pathBlacklist);
            }
        }
    }

    @Deprecated
    public static Command getCommandByPath(String name){
        return commandMap.get(name);
    }
}
