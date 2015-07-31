package test.hf.game.common;

import static org.junit.Assert.*;
import hf.game.common.CardType;

import org.junit.Test;

public class TestCardType
{

    @Test
    public void testLaternCardNum()
    {
        assertTrue(CardType.LATERN.getNumberOfCard()== 56);
    }
    
    @Test
    public void testLaketileNum()
    {
        assertTrue(CardType.LAKETILE.getNumberOfCard()== 36);
    }
    
    @Test
    public void testDedicationNum()
    {
        assertTrue(CardType.DEDICATION.getNumberOfCard()== 30);
    }
    
    @Test
    public void testFavorNum()
    {
        assertTrue(CardType.FAVOR.getNumberOfCard()== 20);
    }

}
