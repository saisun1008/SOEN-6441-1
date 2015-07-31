package test.hf.game;

import static org.junit.Assert.*;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.LocationEnum;

import org.junit.Before;
import org.junit.Test;

/**
 * gameboard Test case in game package
 * 
 * @author yin
 *
 */

public class TestGameBoard
{
    private GameBoard board;
    private GameBoardBuildedr builder;

    @Before
    public void setup()
    {
        board = new GameBoard();
        builder = new GameBoardBuildedr(board);
        board.setNumPlayer(4);
        String[] names = new String[]
        { "1", "2", "3","4" };
        builder.buildAll();
        builder.buildPlayers(names);
    }


    @Test
    public void testBoardEntities()
    {
        assertNotNull(board.getEntities());
    }
    
    @Test
    public void testBoardLatternCollection()
    {
        assertTrue(board.getLatternCollection().size()!=0);
    }
    
    @Test
    public void testBoardCurrentRoundPlayer()
    {
        assertNotNull(board.getCurrentRoundPlayer());
    }
    
    @Test
    public void testBoardStartPlayerByDirection()
    {
        assertNotNull(board.getStartPlayerByDirection());
    }
    
    @Test
    public void testBoardPlayerCount()
    {
        assertTrue(board.getPlayerCount()!=0);
    }

    @Test
    public void testBoardLakeTileCollection()
    {
        assertTrue(board.getLakeTileCollection().size()!=0);
    }
    
    @Test
    public void testBoardDedicationTokenCollection()
    {
        assertTrue(board.getDedicationTokenCollection().size()!=0);
    }

    @Test
    public void testBoardFavorTokenCollection()
    {
        assertTrue(board.getFavorTokenCollection().size()!=0);
    }
    
    @Test
    public void testBoardLatternDecks()
    {
        assertNotNull(board.getLatternDecks());
    }
    
    @Test
    public void testBoardLakeTileDeck()
    {
        assertNotNull(board.getLakeTileDeck());
    }
    
    @Test
    public void testBoardDedicationTokenDeck()
    {
        assertNotNull(board.getDedicationTokenDeck());
    }
    
    @Test
    public void testBoardPlayers()
    {
        assertTrue(board.getPlayers().size()!=0);
    }
    
    @Test
    public void testBoardPlayerByLocation()
    {
        assertNotNull(board.getPlayerByLocation(LocationEnum.BOTTOM));
    }
    
    @Test
    public void testBoardLakeTileByIndex()
    {
        assertNotNull(board.getLakeTileByIndex(0));
    }
    
    @Test
    public void testBoardLanterCardByIndex()
    {
        assertNotNull(board.getLanterCardByIndex(0));
    }
    
    @Test
    public void testBoardDedicationTokenByIndex()
    {
        assertNotNull(board.getDedicationTokenByIndex(0));
    }
    
    @Test
    public void testBoardFavorTokenByIndex()
    {
        assertNotNull(board.getFavorTokenByIndex(0));
    }
    
    @Test
    public void testBoardMatrixLocationIndex()
    {
        assertNotNull(board.getMatrixLocationIndex());
    }
}
