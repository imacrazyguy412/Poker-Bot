package io.github.imacrazyguy412.we.arefarmers.listeners.commands.games;

import java.util.Random;

import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class DiceRollerCommand extends AbstractCommand {

    public DiceRollerCommand(){
        super("diceroller", "Gamble if 2d6 will roll above or below 7",
            new OptionData(OptionType.INTEGER, "amount", "The amoumnt you want to bet", true),
            new OptionData(OptionType.STRING, "choice", "Enter \"higher\" or \"lower\"", true)
        );
    }

    @Override
    public void invoke(SlashCommandInteractionEvent event){
        //TODO: finish this

        String choice = event.getOption("choice").getAsString();
        int bet = event.getOption("amount").getAsInt();

        //TODO: convert choice to either higher or lower based on what it is.

        Random rand = new Random();

        int roll = (rand.nextInt(6) + 1) + (rand.nextInt(6) + 1);

        String response = "You rolled a " + roll + ", and guessed it would be " + choice + " than 7.";

        //TODO: make the player winning or losing affect their coins or whatever they are betting
        if(roll > 7){
            if(choice.equals("higher")){
                response += "\nYou win your bet of " + bet;
            } else{
                response += "\nYou lose your bet of " + bet;
            }
        } else if(roll < 7){
            if(choice.equals("lower")){
                response += "\nYou win your bet of " + bet;
            } else{
                response += "\nYou lose your bet of " + bet;
            }
        } else{
            response += "\nYou lose your bet of " + bet + ", damn sevens.";
        }

        event.reply(response).queue();
    }

}
