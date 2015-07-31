package test.hf.game;

import static org.junit.Assert.assertTrue;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.CardType;
import hf.game.common.LocationEnum;
import hf.game.items.Player;

import org.junit.Before;
import org.junit.Test;

/**
 * boardbuilder Test case in game package
 * 
 * @author Sai
 *
 */

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
    public void testBeforeLoadLakeTile()
    {
        assertTrue(board.getLakeTileCollection().size() == 0);
        // function to be tested
    }
    
    @Test
    public void testAfterLoadLakeTile()
    {
        builder.loadLakeTileCard();
        assertTrue("Lake tile load failed", board.getLakeTileCollection()
                .size() == CardType.LAKETILE.getNumberOfCard());
    }

    @Test
    public void testBeforeLoadLanternCard()
    {
        assertTrue(board.getLatternCollection().size() == 0);
    }
    
    @Test
    public void testAfterLoadLanternCard()
    {
        builder.loadLatternCard();
        assertTrue(board.getLatternCollection().size() == CardType.LATERN
                .getNumberOfCard());
    }

    @Test
    public void testBeforeloadDedicationCard()
    {
        assertTrue(board.getDedicationTokenCollection().size() == 0);
    }

    @Test
    public void testAfterloadDedicationCard()
    {
        builder.loadDedicationCard();
        assertTrue("actual number is "
                + board.getDedicationTokenCollection().size(), board
                .getDedicationTokenCollection().size() == CardType.DEDICATION
                .getNumberOfCard());
    }
    
    @Test
    public void testBeforeloadFavorToken()
    {
        assertTrue(board.getFavorTokenCollection().size() == 0);
    }

    @Test
    public void testAfterloadFavorToken()
    {
        builder.loadFavorToken();
        assertTrue(
                "actual number is " + board.getFavorTokenCollection().size(),
                board.getFavorTokenCollection().size() == CardType.FAVOR
                        .getNumberOfCard());
    }

    
    @Test
    public void testBeforeBuildPlayer()
    {
        assertTrue(board.getPlayers().size() == 0);
    }
    
    @Test
    public void testAfterBuildPlayer()
    {
        board.setNumPlayer(3);
        String[] names = new String[]
        { "1", "2", "3" };
        builder.buildAll();
        builder.buildPlayers(names);
        assertTrue(board.getPlayers().size() == 3);
        for (Player p : board.getPlayers())
        {
            assertTrue(p.getName().equals(
                    Integer.toString(board.getPlayers().indexOf(p) + 1)));
        }
    }

    @Test
    public void testBuildDecks()
    {
        board.setNumPlayer(3);
        String[] names = new String[]
        { "1", "2", "3" };
        builder.buildAll();
        builder.buildPlayers(names);
        assertTrue(board.getPlayers().size() == 3);
        for (Player p : board.getPlayers())
        {
            assertTrue(p.getLakeTileList().size() == 3);
            assertTrue(p.getDedicationTokenList().size() == 0);
            assertTrue(p.getLanternList().size() == 0);
            assertTrue(p.getFavorTokenList().size() == 0);
        }
    }

    @Test
    public void testTakeRandomSubset()
    {
        builder.loadLakeTileCard();
        assertTrue(builder.takeRandomSubset(board.getLakeTileCollection(), 10,
                LocationEnum.CENTER).size() == 10);

    }
}
