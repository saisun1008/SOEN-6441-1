package hf.game.items;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;

/**
 * LakeTile class is used to build lake tile cards in the game play, it's
 * designed based on game rules;
 * 
 * @author sai sun
 *
 */
public class LakeTile extends Card
{
    private boolean m_isFaceUp = false;
    private ColorEnum m_topColor = null;
    private ColorEnum m_bottomColor = null;
    private ColorEnum m_rightColor = null;
    private ColorEnum m_leftColor = null;
    private boolean m_hasSpecialIcon = false;
    private boolean isStartingCard = false;
    private int rotateDegrees = 0;

    public LakeTile()
    {
        super(0, 0);
    }

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
     * @param index
     */
    public LakeTile(boolean faceup, String image, ColorEnum top,
            ColorEnum bottom, ColorEnum right, ColorEnum left,
            boolean specialIcon, int index)
    {
        super(1, index);
        super.setImage(image);
        m_isFaceUp = faceup;
        m_topColor = top;
        m_bottomColor = bottom;
        m_rightColor = right;
        m_leftColor = left;
        m_hasSpecialIcon = specialIcon;
    }

    /**
     * set index
     * 
     * @param index
     */
    public void setIndex(int i)
    {
        super.setIndex(i);
    }

    /**
     * get images
     * 
     * @return image path
     */
    @Override
    public String getImage()
    {
        if (!isFaceUp())
            return m_image;

        return super.getImage();
    }

    /**
     * check if the current card is the starting card
     * 
     * @return true if it is the starting card
     */
    public boolean isStartingCard()
    {
        return isStartingCard;
    }

    /**
     * set starting card
     * 
     * @param isStartingCard
     */
    public void setStartingCard(boolean isStartingCard)
    {
        this.isStartingCard = isStartingCard;
    }

    /**
     * check if the card if face up
     * 
     * @return true if it is face up
     */
    public boolean isFaceUp()
    {
        return m_isFaceUp;
    }

    /**
     * check if the card is routed
     */
    public void flipFaceUp()
    {
        this.m_isFaceUp = true;
    }

    /**
     * check if the card is routed
     */
    public void flipFaceDown()
    {
        this.m_isFaceUp = false;
    }

    /**
     * get top color
     * 
     * @return color enum
     */
    public ColorEnum get_topColor()
    {
        return m_topColor;
    }

    /**
     * set top color
     * 
     * @param m_topColor
     */
    public void set_topColor(ColorEnum m_topColor)
    {
        this.m_topColor = m_topColor;
    }

    /**
     * get bottom color
     * 
     * @return color enum
     */
    public ColorEnum get_bottomColor()
    {
        return m_bottomColor;
    }

    /**
     * set bottom color
     * 
     * @param m_bottomColor
     */
    public void set_bottomColor(ColorEnum m_bottomColor)
    {
        this.m_bottomColor = m_bottomColor;
    }

    /**
     * get right color
     * 
     * @return color enum
     */
    public ColorEnum get_rightColor()
    {
        return m_rightColor;
    }

    /**
     * set right color
     * 
     * @param m_rightColor
     */
    public void set_rightColor(ColorEnum m_rightColor)
    {
        this.m_rightColor = m_rightColor;
    }

    /**
     * get left color
     * 
     * @return color enum
     */
    public ColorEnum get_leftColor()
    {
        return m_leftColor;
    }

    /**
     * set left color
     * 
     * @param m_leftColor
     */
    public void set_leftColor(ColorEnum m_leftColor)
    {
        this.m_leftColor = m_leftColor;
    }

    /**
     * check if the card has a special icon
     * 
     * @return true if it has
     */
    public boolean hasSpecialIcon()
    {
        return m_hasSpecialIcon;
    }

    /**
     * set the card to special icon
     * 
     * @param m_hasSpecialIcon
     */
    public void set_hasSpecialIcon(boolean m_hasSpecialIcon)
    {
        this.m_hasSpecialIcon = m_hasSpecialIcon;
    }

    /**
     * totate tile
     */
    public void rotateTile()
    {
        ColorEnum colorEnum = m_topColor;
        m_topColor = m_rightColor;
        m_rightColor = m_bottomColor;
        m_bottomColor = m_leftColor;
        m_leftColor = colorEnum;
        rotateDegrees = rotateDegrees + 90;
        if (rotateDegrees == 360)
            rotateDegrees = 0;
    }

    /**
     * get card type
     * 
     * @return card type
     */
    @Override
    public CardType getCardType()
    {
        return CardType.LAKETILE;
    }

    /**
     * compare function
     * 
     * @return 0
     */
    @Override
    public int compareTo(Card o)
    {
        return 0;
    }

    /**
     * get rotate degrees
     * 
     * @return rotate degrees
     */
    public int getRotateDegrees()
    {
        return rotateDegrees;
    }

    /**
     * get rotate degrees
     * 
     * @param rotateDegrees
     */
    public void setRotateDegrees(int rotateDegrees)
    {
        this.rotateDegrees = rotateDegrees;
    }

    /**
     * print the result
     * 
     * @return result string
     */
    public String toString()
    {
        return "Lake Tile Card " + m_cardIndex + " Directions: [" + m_topColor
                + m_bottomColor + m_rightColor + m_leftColor;
    }

    public boolean hasColor(ColorEnum color)
    {
        if (m_topColor == color)
        {
            return true;
        } else if (m_bottomColor == color)
        {
            return true;
        } else if (m_leftColor == color)
        {
            return true;
        } else if (m_rightColor == color)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public void rotateCardToDesiredDegree(LocationEnum location, ColorEnum color)
    {
        switch (location)
        {
        case BOTTOM:
            while (m_bottomColor != color)
            {
                rotateTile();
            }
            break;

        case TOP:
            while (m_topColor != color)
            {
                rotateTile();
            }
            break;

        case LEFT:
            while (m_leftColor != color)
            {
                rotateTile();
            }
            break;

        case RIGHT:
            while (m_rightColor != color)
            {
                rotateTile();
            }
            break;

        default:
            break;
        }
    }
}
