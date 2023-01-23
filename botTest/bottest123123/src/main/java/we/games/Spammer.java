package we.games;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.DiscordBot;

public class Spammer implements Runnable{
    Thread thread;
    String message;
    MessageChannel channel;
    boolean isSpamming;

    public Spammer(String message, MessageChannel channel){
        isSpamming = true;

        this.message = message;
        this.channel = channel;
    }

    public Spammer(MessageChannel channel){
        this.channel = channel;
    }

    public void run(){
        while(isSpamming){
            DiscordBot.message(message, channel);
        } 
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        isSpamming = false;
    }

    public MessageChannel getChannel(){
        return channel;
    }

    @Override
    public boolean equals(Object obj) {
        Spammer sp = (Spammer)(obj);

        return sp.getChannel().equals(this.getChannel());
    }
}