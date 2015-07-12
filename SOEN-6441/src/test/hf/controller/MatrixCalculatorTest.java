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
    public void testPlaceStartLake()
    {
        LakeTile lt = new LakeTile();
        lt.setIndex(1);
        assertFalse(lt.isFaceUp());
        mc.placeStartLake(lt,221);
        assertTrue(lt.isFaceUp());
    }
}
