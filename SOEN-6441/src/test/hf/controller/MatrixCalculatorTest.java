package test.hf.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.controller.MatrixCalculator;
import hf.game.items.LakeTile;

/**
 * Test of MatrixCalculator
 * 
 * 
 *
 */

public class MatrixCalculatorTest
{
    private MatrixCalculator mc = new MatrixCalculator();
    private GameBoard board;
    private GameBoardBuildedr builder;
    
    @Before
    public void before()
    {
        board = new GameBoard();
        builder = new GameBoardBuildedr(board);        
        mc.init(board);
    }

    @Test
    public void testPlaceLakeTile()
    {
        // No selected card
        mc.setSelectedCard(null);
        mc.placeLakeTile(1);
        assertNull(mc.getEntities().get(3).getLake());
    }

    @Test
    public void testPlaceNewLake()
    {
        mc.placeNewLake(1, 221);
        mc.placeNewLake(2, 200);
        assertNotNull(mc.getEntities().get(221 - 21).getLake());
        assertNull(mc.getEntities().get(221 + 21).getLake());
        assertNull(mc.getEntities().get(221 - 1).getLake());
        assertNull(mc.getEntities().get(221 + 1).getLake());
    }
    
    @Test
    public void testPlaceStartLake()
    {
        LakeTile lt = new LakeTile();
        lt.setIndex(21);
        assertFalse(lt.isFaceUp());
        mc.placeStartLake(lt,221);
        assertTrue(lt.isFaceUp());
    }
}
