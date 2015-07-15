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
        assertTrue(board.getLakeTileCollection().size() == 0);
        // function to be tested
        builder.loadLakeTileCard();
        assertTrue("Lake tile load failed", board.getLakeTileCollection()
                .size() == CardType.LAKETILE.getNumberOfCard());
    }

    @Test
    public void testLoadLanternCard()
    {
        assertTrue(board.getLatternCollection().size() == 0);
        builder.loadLatternCard();
        assertTrue(board.getLatternCollection().size() == CardType.LATERN
                .getNumberOfCard());
    }

    @Test
    public void testloadDedicationCard()
    {
        assertTrue(board.getDedicationTokenCollection().size() == 0);
        builder.loadDedicationCard();
        assertTrue("actual number is "
                + board.getDedicationTokenCollection().size(), board
                .getDedicationTokenCollection().size() == CardType.DEDICATION
                .getNumberOfCard());
    }

    @Test
    public void testloadFavorToken()
    {
        assertTrue(board.getFavorTokenCollection().size() == 0);
        builder.loadFavorToken();
        assertTrue(
                "actual number is " + board.getFavorTokenCollection().size(),
                board.getFavorTokenCollection().size() == CardType.FAVOR
                        .getNumberOfCard());
    }
}
