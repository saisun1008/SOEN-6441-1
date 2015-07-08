package hf.game.items;

import hf.game.common.Color;
import hf.game.common.Direction;

import java.awt.image.BufferedImage;

/**
 * LakeTile class is used to build lake tile cards in the game play, it's
 * designed based on game rules;
 * 
 * @author sai sun
 *
 */
public class LakeTile
{
    private boolean m_isFaceUp = false;
    private BufferedImage m_image = null;
    private Color m_topColor = null;
    private Color m_bottomColor = null;
    private Color m_rightColor = null;
    private Color m_leftColor = null;
    private boolean m_hasSpecialIcon = false;
    private BufferedImage m_specialIconImage = null;
    private LakeTile m_topAdjacentTile = null;
    private LakeTile m_bottomAdjacentTile = null;
    private LakeTile m_rightAdjacentTile = null;
    private LakeTile m_leftAdjacentTile = null;
    private Player m_owner = null;

    /**
     * Constructor
     * 
     * @param faceup
     * @param image
     * @param top
     * @param bottom
     * @param right
     * @param left
     * @param specialIcon
     * @param icon
     * @param topTile
     * @param botTile
     * @param rightTile
     * @param leftTile
     */
    public LakeTile(boolean faceup, BufferedImage image, Color top,
            Color bottom, Color right, Color left, boolean specialIcon,
            BufferedImage icon, LakeTile topTile, LakeTile botTile,
            LakeTile rightTile, LakeTile leftTile)
    {
        m_isFaceUp = faceup;
        m_image = image;
        m_topColor = top;
        m_bottomColor = bottom;
        m_rightColor = right;
        m_leftColor = left;
        m_hasSpecialIcon = specialIcon;
        m_specialIconImage = icon;
        m_topAdjacentTile = topTile;
        m_bottomAdjacentTile = botTile;
        m_rightAdjacentTile = rightTile;
        m_leftAdjacentTile = leftTile;
    }

    public boolean isFaceUp()
    {
        return m_isFaceUp;
    }

    public void flipFaceUp()
    {
        this.m_isFaceUp = true;
    }

    public void flipFaceDown()
    {
        this.m_isFaceUp = false;
    }

    public BufferedImage get_image()
    {
        return m_image;
    }

    public void set_image(BufferedImage m_image)
    {
        this.m_image = m_image;
    }

    public Color get_topColor()
    {
        return m_topColor;
    }

    public void set_topColor(Color m_topColor)
    {
        this.m_topColor = m_topColor;
    }

    public Color get_bottomColor()
    {
        return m_bottomColor;
    }

    public void set_bottomColor(Color m_bottomColor)
    {
        this.m_bottomColor = m_bottomColor;
    }

    public Color get_rightColor()
    {
        return m_rightColor;
    }

    public void set_rightColor(Color m_rightColor)
    {
        this.m_rightColor = m_rightColor;
    }

    public Color get_leftColor()
    {
        return m_leftColor;
    }

    public void set_leftColor(Color m_leftColor)
    {
        this.m_leftColor = m_leftColor;
    }

    public boolean hasSpecialIcon()
    {
        return m_hasSpecialIcon;
    }

    public void set_hasSpecialIcon(boolean m_hasSpecialIcon)
    {
        this.m_hasSpecialIcon = m_hasSpecialIcon;
    }

    public BufferedImage get_specialIconImage()
    {
        return m_specialIconImage;
    }

    public void set_specialIconImage(BufferedImage m_specialIconImage)
    {
        this.m_specialIconImage = m_specialIconImage;
    }

    public LakeTile get_topAdjacentTile()
    {
        return m_topAdjacentTile;
    }

    public void set_topAdjacentTile(LakeTile m_topAdjacentTile)
    {
        this.m_topAdjacentTile = m_topAdjacentTile;
    }

    public LakeTile get_bottomAdjacentTile()
    {
        return m_bottomAdjacentTile;
    }

    public void set_bottomAdjacentTile(LakeTile m_bottomAdjacentTile)
    {
        this.m_bottomAdjacentTile = m_bottomAdjacentTile;
    }

    public LakeTile get_rightAdjacentTile()
    {
        return m_rightAdjacentTile;
    }

    public void set_rightAdjacentTile(LakeTile m_rightAdjacentTile)
    {
        this.m_rightAdjacentTile = m_rightAdjacentTile;
    }

    public LakeTile get_leftAdjacentTile()
    {
        return m_leftAdjacentTile;
    }

    public void set_leftAdjacentTile(LakeTile m_leftAdjacentTile)
    {
        this.m_leftAdjacentTile = m_leftAdjacentTile;
    }

    public boolean assignToPlayer(Player p)
    {
        m_owner = null;
        return true;
    }

    public void returnToDeck()
    {
        m_owner = null;
    }

    /**
     * Place current lake tile card next to a given lake tile card
     * 
     * @param tile
     *            Lake tile card to be placed next to
     * @param direction
     *            Direction of the given card to be adjacent to current card
     * @return
     */
    public boolean placeNextToTile(LakeTile tile, Direction direction)
    {
        switch (direction)
        {
        case TOP:
        {
            if (tile.get_topAdjacentTile() == null)
            {
                tile.set_topAdjacentTile(this);
                this.set_bottomAdjacentTile(tile);
                return true;
            }
        }

        case BOTTOM:
        {
            if (tile.get_bottomAdjacentTile() == null)
            {
                tile.set_bottomAdjacentTile(this);
                this.set_topAdjacentTile(tile);
                return true;
            }
        }
        case RIGHT:
        {
            if (tile.get_rightAdjacentTile() == null)
            {
                tile.set_rightAdjacentTile(this);
                this.set_leftAdjacentTile(tile);
                return true;
            }
        }

        case LEFT:
        {
            if (tile.get_leftAdjacentTile() == null)
            {
                tile.set_leftAdjacentTile(this);
                this.set_rightAdjacentTile(tile);
                return true;
            }
        }
        }

        return false;
    }
}
