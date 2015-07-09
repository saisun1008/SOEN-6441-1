package hf.game.items;

import hf.game.common.CardType;
import hf.game.common.Color;

import java.awt.image.BufferedImage;

public class LanternCard extends Card
{
    private Color m_color = Color.RED;
    private BufferedImage m_image = null;
    private Player m_owner = null;

    public LanternCard(Color color, BufferedImage image, Player p)
    {
        super(1);
        super.assignToPlayer(p);
        super.setImage(image);
        m_color = color;
    }

    public Color getColor()
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
