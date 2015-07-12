package hf.game.controller;

import hf.game.GameBoard;

/**
 * Observer interface
 * 
 * @author Sai
 *
 */
public interface BoardObserver
{
    public void update(GameBoard board);
}
