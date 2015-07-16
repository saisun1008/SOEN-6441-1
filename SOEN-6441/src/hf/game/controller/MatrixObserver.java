package hf.game.controller;

import hf.ui.matrix.MatrixCell;

import java.util.Map;

/**
 * Observer for Matrix updates
 *
 */
public interface MatrixObserver
{
    public void update(Map<Integer, MatrixCell> entities);
}
