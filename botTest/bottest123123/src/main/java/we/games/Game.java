package we.games;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.DiscordBot;

public abstract class Game implements Runnable{
    protected Thread thread;
    protected MessageChannel channel;
    protected String choice;

    Game(MessageChannel channel){
        this.channel = channel;
    }

    /**
     * gets called by the implemented {@link #run()} method
     */
    abstract void play();

    public final void run(){
        play();
    }
    
    public final void start(){
        thread = new Thread(this);
        thread.start();
    }

    public final MessageChannel getChannel(){
        return channel;
    }

    protected final void message(String msg){
        DiscordBot.message(msg, channel);
    }

    /**
     * Waits until {@link #setChoice(String)} is called from a seperate {@code Thread}
     * @see #setChoice(String)
     */
    protected final void input(){
        while(true){
            try {
                thread.wait();
                //System.out.println("choice: " + choice);
            } catch (Exception e) {
                // TODO: handle exception
            }
            if(!choice.equals(""))
                break;
        }
    }

    /**
     * passes a {@code String} to {@link #choice}
     * <p>
     * Gives {@link #choice} a value. The given value will be given with all spaces removed to choice
     * @param s -- {@code String} the string to pass
     */
    public final void setChoice(String s){
        choice = s.toLowerCase().replaceAll(" ", "");
    }

    @Override
    public final boolean equals(Object obj){
        Game g = (Game)obj;
        return g.getChannel().equals(channel);
    }
}
