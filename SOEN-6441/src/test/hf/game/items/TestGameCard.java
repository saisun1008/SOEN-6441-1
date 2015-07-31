package test.hf.game.items;

import static org.junit.Assert.*;
import hf.game.common.CardType;
import hf.game.common.ColorEnum;
import hf.game.items.*;

import org.junit.Before;
import org.junit.Test;

/**
 * gamecard Test case in items package
 * 
 * @author yin
 *
 */

public class TestGameCard
{
    private DedicationToken dedicationCard;
    private FavorToken favorCard;
    private LakeTile laketileCard;
    private LanternCard lantern;

    @Before
    public void setup()
    {
        dedicationCard = new DedicationToken(9,
                "images/Dedications/DedicationRed_9.jpg", 9, ColorEnum.RED, 3);
        favorCard = new FavorToken("images/favor_token.jpg", 1);
        laketileCard = new LakeTile(false, "images/tiles/foldedTile.jpg",
                ColorEnum.RED, ColorEnum.BLUE, ColorEnum.GREEN,
                ColorEnum.WHITE, false, 1);
        lantern = new LanternCard(ColorEnum.ORANGE, null, null, 1);
    }
    
    @Test
    public void testdedicationCard()
    {
        assertEquals(CardType.DEDICATION,dedicationCard.getCardType());
    }
    
    @Test
    public void testfavorCard()
    {
        assertEquals(CardType.FAVOR,favorCard.getCardType());
    }
    
    @Test
    public void testlaketileCard()
    {
        assertEquals(CardType.LAKETILE,laketileCard.getCardType());
    }
    
    @Test
    public void testlanternCard()
    {
        assertEquals(CardType.LATERN,lantern.getCardType());
    }

}
