package hf.game.strategy;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import hf.game.GameBoard;
import hf.game.common.LocationEnum;
import hf.game.controller.MatrixCalculator;
import hf.game.items.Player;
import hf.ui.matrix.Matrix;

public class FriendlyStrategy implements PlayerStrategy
{
    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        Matrix matrix = board.getMatrix();
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        Player nextRoundPlayer = board.getNextRoundPlayer();
        LocationEnum sitLocation = nextRoundPlayer.getSitLocation();
        
        MatrixCalculator ca = matrix.getMatrixCalculator();
        ca.setSelectedCard(currentRoundPlayer.getLakeTileList().get(0));

        ca.placeLakeTile(222);
        
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean redeemLanternCard(GameBoard board)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String printReasoning()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
