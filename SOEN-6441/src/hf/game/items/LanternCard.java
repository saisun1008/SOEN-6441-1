package hf.game.items;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;

import java.awt.image.BufferedImage;

public class LanternCard extends Card
{
    private ColorEnum m_color = ColorEnum.RED;
    private BufferedImage m_image = null;
    private Player m_owner = null;
    /**
     * Assign lantern card and set images
     * 
     * @param color
     * @param image
     * @param p
     * @param index
     */
    public LanternCard(ColorEnum color, String image, Player p, int index)
    {
        super(1, index);
        super.assignToPlayer(p);
        super.setImage(image);
        m_color = color;
    }

    public ColorEnum getColor()
    {
        return m_color;
    }

    @Override
    public CardType getCardType()
    {
        return CardType.LATERN;
    }

    /**
     * Compareto function
     * 
     * @param o
     * @return 0 if the color matches
     */    
    @Override
    public int compareTo(Card o)
    {
        if (m_color == ((LanternCard) o).getColor()
                && m_owner.equals(o.getOwner()))
        {
            return 0;
        } else
        {
            return 1;
        }
    }
}
