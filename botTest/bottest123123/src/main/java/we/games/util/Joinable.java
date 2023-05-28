package we.games.util;

public interface Joinable {
    /**
     * Allows the given player to join the game
     * 
     * @param player - the player that will join
     * @return the player that was added
     */
    public Player addPlayer(Player player);

    /**
     * Allows the given player to leave the game
     * 
     * @param player - the player that will leave
     * @return the player that was removed
     */
    public Player removePlayer(Player player);
}
