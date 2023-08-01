package io.github.imacrazyguy412.we.games.util;

public interface Betting {
    /**
     * Have the game place a bet for the player at given index.
     * 
     * @param bet The amount to bet
     * @param forPlayerIndex The index of the player to place the bet for.
     */
    void placeBet(int bet, int forPlayerIndex);
    
    /**
     * Have the game place a bet for the given player
     * 
     * @param bet The amount to bet
     * @param forPlayer The index of the player to place the bet for.
     */
    void placeBet(int bet, Player forPlayer);
    
    /**
     * Indicates whether the game is still in its betting phase or not
     * 
     * @return {@code true} if the game is in its bettting phase, or {@code false otherwise}
     */
    boolean isBetting();
}
