package test.hf.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hf.controller.MatrixCalculator;
import hf.game.items.LakeTile;

public class MatrixCalculatorTest
{
    MatrixCalculator mc = new MatrixCalculator();
    @Before
    public void before()
    {
        mc.init();
    }

    @Test
    public void testPlaceLakeTile()
    {
        // No selected card
        mc.setSelectedCard(null);
        mc.placeLakeTile(3);
        assertNull(mc.getEntities().get(3).getLake());
    }

    @Test
    public void testPlaceNewLake()
    {
        mc.placeNewLake(new LakeTile(), 221);
        mc.placeNewLake(new LakeTile(), 200);
        assertNotNull(mc.getEntities().get(221 - 21).getLake());
        assertNull(mc.getEntities().get(221 + 21).getLake());
        assertNull(mc.getEntities().get(221 - 1).getLake());
        assertNull(mc.getEntities().get(221 + 1).getLake());
    }
    
    @Test
    public void testPlaceStartLake()
    {
        LakeTile lt = new LakeTile();
        lt.setIndex(1);
        assertFalse(lt.isFaceUp());
        mc.placeStartLake(lt,221);
        assertTrue(lt.isFaceUp());
    }
}
