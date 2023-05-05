package we.games.util;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import we.arefarmers.DiscordBot;

/**
 * An instance of a {@link Game} running on its own {@link Thread}.
 * <p>
 * The {@link Game} class is used as a base for other games that
 * need to run on their own {@link Thread}. The game also has a 
 * dedicated {@link MessageChannel}, which is also what is used
 * to identify them by {@link Object#equals(Object)}.
 * <p>
 * No more than one {@link Game} should be active in a single
 * {@link MessageChannel}.
 * <p>
 * Other {@link Game} classes will be able to message their
 * respective {@link MessageChannel} with the
 * {@link #message(Object)} method for output, and can use the
 * {@link #input()} method to wait for an input given via the
 * {@link #setChoice(String)} method. The {@link #choice} field
 * will hold the resulting input.
 * <p>
 * In order to start a {@link Game}, the {@link #start()}
 * method can be used to start the {@link Thread} and will
 * call the {@link #play()} method, which should be implemented
 * by the extending class.
 * @author That "Inconspicuous" guy
 */
public abstract class Game implements Runnable{
    protected Thread thread;
    protected MessageChannel channel;
    protected String choice;

    public Game(MessageChannel channel){
        this.channel = channel;
    }

    /**
     * gets called by the implemented {@link #run()} method
     */
    public abstract void play();

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

    //SECTION - message overloads

    protected final void message(String msg){
        DiscordBot.message(msg, channel);
    }

    protected final void message(char[] data, int offset, int count){
        DiscordBot.message(String.valueOf(data, offset, count), channel);
    }

    protected final void message(double d){
        DiscordBot.message(String.valueOf(d), channel);
    }

    protected final void message(float f){
        DiscordBot.message(String.valueOf(f), channel);
    }

    protected final void message(long l){
        DiscordBot.message(String.valueOf(l), channel);
    }

    protected final void message(int i){
        DiscordBot.message(String.valueOf(i), channel);
    }

    protected final void message(char c){
        DiscordBot.message(String.valueOf(c), channel);
    }

    protected final void message(char[] data){
        DiscordBot.message(String.valueOf(data), channel);
    }

    protected final void message(Object obj){
        DiscordBot.message(String.valueOf(obj), channel);
    }

    //!SECTION

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
                break;
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
        if(!thread.isInterrupted()){
            thread.interrupt();
        }
    }

    @Override
    public final boolean equals(Object obj){
        Game g = (Game)obj;
        return g.getChannel().equals(channel);
    }
}
