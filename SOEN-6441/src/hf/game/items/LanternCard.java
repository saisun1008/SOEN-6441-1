package hf.game.items;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;

import java.awt.image.BufferedImage;

public class LanternCard extends Card
{
    private ColorEnum m_color = ColorEnum.RED;
    private BufferedImage m_image = null;
    private Player m_owner = null;

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

    public Player getOwner()
    {
        return m_owner;
    }

    @Override
    public CardType getCardType()
    {
        return CardType.LATERN;
    }

    /**
     * compareto function
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
