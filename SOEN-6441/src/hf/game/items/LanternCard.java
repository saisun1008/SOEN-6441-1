package hf.game.items;

import hf.game.common.Color;

import java.awt.image.BufferedImage;

public class LanternCard implements Comparable<LanternCard>
{
    private Color m_color = Color.RED;
    private BufferedImage m_image = null;
    private Player m_owner = null;

    public LanternCard(Color color, BufferedImage image, Player p)
    {
        m_color = color;
        m_image = image;
        m_owner = p;
    }

    /**
     * override compareto function
     */
    @Override
    public int compareTo(LanternCard o)
    {
        if (m_color == o.getColor() && m_owner.equals(o.getOwner()))
        {
            return 0;
        } else
        {
            return 1;
        }
    }

    public boolean assignToPlayer(Player p)
    {
        m_owner = p;
        return true;
    }

    public void returnToDeck()
    {
        m_owner = null;
    }

    public Color getColor()
    {
        return m_color;
    }

    public Player getOwner()
    {
        return m_owner;
    }

    public BufferedImage getImage()
    {
        return m_image;
    }
}
