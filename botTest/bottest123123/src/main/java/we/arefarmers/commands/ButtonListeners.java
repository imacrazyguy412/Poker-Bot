package commands;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

public class ButtonListeners extends ListenerAdapter{
    
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e){
        if(e.getMessage().getContentRaw().contains("button")){
            Message message = new MessageBuilder()
            .append("Here is ur button, stupid.")
            .setActionRows(
                ActionRow.of(Button.danger("blow-up", "Blow thyself into smithereens"), Button.success("nut", "Nut.")))
                .build();
            
                e.getChannel().sendMessage(message).queue();
        }
        
    }

    public void onButtonInteraction(@NotNull ButtonInteractionEvent e){
        String id = e.getButton().getId();

        if(id.equals("blow-up")){

            e.getChannel().sendMessage(e.getUser().getAsTag() + " Blew themself up! :coolpepe::emoji_502:").queue();
        } else if(id.equals("nut")){
            e.getChannel().sendMessage("uuhhhh").queue();
        } else{
            e.getChannel().sendMessage("how").queue();
        }
    }
}