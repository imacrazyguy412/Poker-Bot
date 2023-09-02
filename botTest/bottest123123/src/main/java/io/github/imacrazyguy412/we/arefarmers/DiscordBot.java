package io.github.imacrazyguy412.we.arefarmers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.Listeners;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {

    private static final String REPLACE_LOG_LEVEL = "___REPLACE_LOG_LEVEL___";

    public static JDA bot;

    public static final Logger log = LoggerFactory.getLogger(DiscordBot.class);

    public static String currentLogLevel;

    public static final Path LOGBACK_PATH = Path.of("botTest\\bottest123123\\src\\main\\resources\\logback.xml");

    public static void main(String[] args) throws LoginException, InterruptedException {
        log.info( "\n" +
            "==============================================================================\n" +
            "Starting Bot...\n" +
            "==============================================================================\n"
        );

        String logLevel;
        if(args.length < 1){
            logLevel = "INFO";
        } else {
            switch(args[0].toLowerCase()){
                case "-d":
                case "-x":
                case "debug":
                    logLevel = "DEBUG";
                    break;

                case "-t":
                case "trace":
                    logLevel = "TRACE";
                    break;

                case "-w":
                case "warn":
                    logLevel = "WARN";
                    break;
                    
                // No shorthand for error, as warnings should probably be displayed
                case "error":
                    logLevel = "ERROR";
                    break;

                // Defualt to info
                default:
                    logLevel = "INFO";
            }
        }
        
        setInitialLogLevel(logLevel);
        currentLogLevel = logLevel;

        bot = JDABuilder.createDefault(Poop.id)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("with your mom"))
                .addEventListeners(new Listeners(), new CommandManager())
                .build();

        //Runtime.getRuntime().addShutdownHook(new Thread(() -> log.info("Shutting down.\n"), "Shutdown"));
    }

    public static void setNewLogLevel(String logLevel){
        try {
            List<String> lines = Files.readAllLines(LOGBACK_PATH);
            List<String> newLines = new ArrayList<String>(lines.size());
            
            for (int i = 0; i < lines.size(); i++) {
                newLines.add(lines.get(i).replace(currentLogLevel, logLevel));
            }
            Files.writeString(LOGBACK_PATH, String.join("\n", newLines));
            currentLogLevel = logLevel;
        
            log.info("Set log level to {}", logLevel);
        } catch (IOException e) {
            log.error("Could not read/write logback file.", e);
        };
    }

    private static void setInitialLogLevel(String logLevel) {
        try {
            List<String> lines = Files.readAllLines(LOGBACK_PATH);
            List<String> newLines = new ArrayList<String>(lines.size());
            
            for (int i = 0; i < lines.size(); i++) {
                newLines.add(lines.get(i).replace(REPLACE_LOG_LEVEL, logLevel));
            }
            Files.writeString(LOGBACK_PATH, String.join("\n", newLines));
        
            log.info("Set log level to {}", logLevel);
        } catch (IOException e) {
            log.error("Could not read/write logback file.", e);
        };
        
        // Set the log level back to our replacement indicator on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                List<String> lines = Files.readAllLines(LOGBACK_PATH);
                List<String> newLines = new ArrayList<>(lines.size());

                for (int i = 0; i < lines.size(); i++) {
                    newLines.add(lines.get(i).replace(logLevel, REPLACE_LOG_LEVEL));
                }
                Files.writeString(LOGBACK_PATH, String.join("\n", newLines));
        
                log.info("Replaced log level with replacement indicator (\"{}\")", REPLACE_LOG_LEVEL);
            } catch (IOException e) {
                log.error("Could not read/write logback file.", e);
            }
        }, "logback Shutdown Hook"));
    }

    public static void message(String str, MessageChannel channel) {
        channel.sendMessage(str).queue();
    }
}
