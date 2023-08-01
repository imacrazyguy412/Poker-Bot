package io.github.imacrazyguy412.we.games.util;

public interface SequentialBetting extends Betting {
    /**
     * Get the player's turn to bet. Should return -1 to indicate when it is no one's turn to bet. 
     * Used for sequential betting.
     * 
     * @return the player whose turn it is to bet.
     */
    int getPlayerToBet();
}
