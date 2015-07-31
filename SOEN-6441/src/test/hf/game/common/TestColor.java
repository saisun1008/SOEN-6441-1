package test.hf.game.common;

import static org.junit.Assert.*;
import hf.game.common.ColorEnum;

import org.junit.Test;

/**
 * color Test case in common package
 * 
 * @author yin
 *
 */

public class TestColor
{

    @Test
    public void testColorBlack()
    {
        assertNotNull(ColorEnum.BLACK.getImagePath());
    }
    
    @Test
    public void testColorBlue()
    {
        assertNotNull(ColorEnum.BLUE.getImagePath());
    }
    
    @Test
    public void testColorGreen()
    {
        assertNotNull(ColorEnum.GREEN.getImagePath());
    }
    
    @Test
    public void testColorOrange()
    {
        assertNotNull(ColorEnum.ORANGE.getImagePath());
    }
    
    @Test
    public void testColorPurple()
    {
        assertNotNull(ColorEnum.PURPLE.getImagePath());
    }
    
    @Test
    public void testColorRed()
    {
        assertNotNull(ColorEnum.RED.getImagePath());
    }
    
    @Test
    public void testColorWhite()
    {
        assertNotNull(ColorEnum.WHITE.getImagePath());
    }
}
