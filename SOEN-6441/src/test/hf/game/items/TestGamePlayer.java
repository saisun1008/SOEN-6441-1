package test.hf.game.items;

import static org.junit.Assert.*;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.CardType;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.items.DedicationToken;
import hf.game.items.FavorToken;
import hf.game.items.LakeTile;
import hf.game.items.LanternCard;
import hf.game.items.Player;

import org.junit.Before;
import org.junit.Test;

public class TestGamePlayer
{
    private Player gamePlayer;
    private GameBoard board;
    private GameBoardBuildedr builder;
    
    @Before
    public void setup()
    {
        board = new GameBoard();
        builder = new GameBoardBuildedr(board);
        gamePlayer = new Player("smith", LocationEnum.BOTTOM);
    }

    @Test
    public void testPlayerName()
    {
        assertEquals("smith",gamePlayer.getName());
    }
    
    @Test
    public void testPlayerLocation()
    {
        assertEquals(LocationEnum.BOTTOM,gamePlayer.getSitLocation());
    }
    
    @Test
    public void testPlayerlaternlist()
    {
        board.setNumPlayer(4);
        String[] names = new String[]
        { "1", "2", "3","4" };
        builder.buildAll();
        builder.buildPlayers(names);
        gamePlayer = board.getPlayerByLocation(LocationEnum.BOTTOM);
        assertTrue(gamePlayer.getLanternList().size()==0);
    }
    
    @Test
    public void testPlayerLakeTileList()
    {
        board.setNumPlayer(4);
        String[] names = new String[]
        { "1", "2", "3","4" };
        builder.buildAll();
        builder.buildPlayers(names);
        gamePlayer = board.getPlayerByLocation(LocationEnum.BOTTOM);
        assertTrue(gamePlayer.getLakeTileList().size()!=0);
    }

    @Test
    public void testPlayerDedicationTokenList()
    {
        board.setNumPlayer(4);
        String[] names = new String[]
        { "1", "2", "3","4" };
        builder.buildAll();
        builder.buildPlayers(names);
        gamePlayer = board.getPlayerByLocation(LocationEnum.BOTTOM);
        assertTrue(gamePlayer.getDedicationTokenList().size()==0);
    }
    
    @Test
    public void testPlayerFavorTokenList()
    {
        board.setNumPlayer(4);
        String[] names = new String[]
        { "1", "2", "3","4" };
        builder.buildAll();
        builder.buildPlayers(names);
        gamePlayer = board.getPlayerByLocation(LocationEnum.BOTTOM);
        assertTrue(gamePlayer.getFavorTokenList().size()==0);
    }
}
