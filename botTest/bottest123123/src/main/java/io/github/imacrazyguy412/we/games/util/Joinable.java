package io.github.imacrazyguy412.we.games.util;

import java.util.ArrayList;

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

    public ArrayList<? extends Player> getPlayers();
}
