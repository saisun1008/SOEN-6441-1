package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.items.Player;

public interface GameEndingStrategy
{
    /**
     * Validate if current game board has satisfied game ending conditions
     * 
     * @param board
     *            game board that is currently played on
     * @return true if game ending condition has been met
     */
    public boolean validateGameEndingCondition(GameBoard board);

    /**
     * Print out winner info
     * 
     * @return String contains winner information
     */
    public String printoutWinner();
}
