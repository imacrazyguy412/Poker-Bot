package we.games;

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
        Player p = (Player)obj;
        return name.equals(p.getName());
    }
}
