package test.hf.game;

import static org.junit.Assert.assertTrue;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.CardType;

import org.junit.Before;
import org.junit.Test;

public class TestBoardBuilder
{

    private GameBoard board;
    private GameBoardBuildedr builder;

    @Before
    public void setup()
    {
        board = new GameBoard();
        builder = new GameBoardBuildedr(board);
    }

    @Test
    public void testLoadLakeTile()
    {
        builder.loadLakeTileCard();
        assertTrue("Lake tile load failed", board.getLakeTileCollection()
                .size() == CardType.LAKETILE.getNumberOfCard());
    }

}
