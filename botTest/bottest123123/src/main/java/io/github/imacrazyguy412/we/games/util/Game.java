package io.github.imacrazyguy412.we.games.util;

import io.github.imacrazyguy412.we.arefarmers.DiscordBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

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
 * {@link #setChoice(String)} method. {@link #input()} will return
 * the input value.
 * <p>
 * In order to start a {@link Game}, the {@link #start()}
 * method can be used to start the {@link Thread} and will
 * call the {@link #play()} method, which should be implemented
 * by the extending class.
 * @author That "Inconspicuous" guy
 */
public abstract class Game implements Runnable{
    protected MessageChannel channel;
    private String choice;
    private Integer choiceInt;
    private Thread thread;

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
    
    /**
     * Start the game on its own thread.
     * @return {@code this}
     */
    public final Game start(){
        thread = new Thread(this);
        thread.start();
        return this;
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

    protected final void message(String msg, Object... args){
        DiscordBot.message(String.format(msg, args), channel);
    }

    //!SECTION

    /**
     * Waits until {@link #setChoice(String)} is called from a seperate {@code Thread}, and returns the set value
     * 
     * @return The set choice, A.K.A. the input
     * @see #setChoice(String)
     */
    protected synchronized final String input(){
        while(true){
            try {
                wait();
            } catch (InterruptedException e) {
                return choice;
            }
        }
        
    }

    /**
     * Waits until {@link #setChoice(int)} is called from a seperate {@code Thread}, and returns the given value
     * 
     * @return The given int, A.K.A. the input
     * @see #setChoice(int)
     */
    protected synchronized final int inputAsInt(){ //NOTE - synchronized IS required for these to work
        while(true){
            try {
                wait();
            } catch (InterruptedException e) {
                return choiceInt.intValue();
            }
            
        }
    }

    /**
     * passes a {@code String} to {@link #choice}
     * <p>
     * Gives {@link #choice} a value. The given value will be given with all spaces removed to choice
     * @param s -- the string to pass
     * @see #input()
     */
    public synchronized final void setChoice(String s){
        choice = s.toLowerCase().replaceAll(" ", "");
        thread.interrupt();
    }

    /**
     * passes a {@code int} to {@link #choiceInt}
     * @param i -- the int to pass
     * @see #inputAsInt()
     */
    public synchronized final void setChoice(int i){
        choiceInt = Integer.valueOf(i);
        thread.interrupt();
    }

    @Override
    public final boolean equals(Object obj){
        if(!(obj instanceof Game)) return false;
        
        return ((Game)obj).channel.equals(channel);
    }
}
