package io.github.imacrazyguy412.we.games.util;

/**
 * An instance of a {@link Player} that plays in a {@link Game}
 * <p>
 * The {@link Player} class is used as a base for other player
 * classes that play in games.
 * <p>
 * Each player has a name that is used to identify them with
 * {@link Object#equals(Object)}. The name should be unique, so
 * discord tags as {@link String}s may be used.
 * @author That "Inconspicuous" guy
 * @see Game
 */
public abstract class Player {
    /** The amount of chips the player has. Can be left unused */
    protected int chips;
    /** Should be Readonly. The name of the player. Should be used. */
    protected String name;
    
    /**
     * Creates a player with a specified amount of chips and a given name
     * @param chips
     * @param name
     */
    public Player(int chips, String name){
        this.chips = chips; 
        this.name = name;
    }

    /**
     * Returns the name of the player
     * @return the player's name
     */
    public final String getName(){
        return name;
    }

    /**
     * Returns the number of chips a player has
     * 
     * @return the number of chips the player has
     */
    public final int getChips(){
        return chips;
    }

    /**
     * Sets a player's chips to the given value
     * 
     * @param c -- the number of chips to set the player's chips to
     */
    protected final void setChips(int c){
        chips = c;
    }

    @Override
    public final boolean equals(Object obj) {
        if(!(obj instanceof Player)) return false;

        Player p = (Player)obj;
        return name.equals(p.getName());
    }
}
