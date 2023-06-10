package we.games.util;

public interface Betting {
    /**
     * Get the player's turn to bet. By defualt, will return {@code -1} which
     * indecates that it is nobody's turn to bet. 
     * @return the player whose turn it is to bet.
     */
    default int getPlayerToBet(){
        return -1;
    }
}
