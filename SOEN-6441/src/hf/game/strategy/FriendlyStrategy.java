package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.items.Player;
import hf.ui.matrix.Matrix;

public class FriendlyStrategy implements PlayerStrategy
{

    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        Matrix matrix = board.getMatrix();
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        matrix.getMatrixCalculator().setSelectedCard(currentRoundPlayer.getLakeTileList().get(0));
        
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
