package hf.game.items;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;

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
     * @param icon
     * @param topTile
     * @param botTile
     * @param rightTile
     * @param leftTile
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

    public void setIndex(int i)
    {
        super.setIndex(i);
    }

    @Override
    public String getImage()
    {
        if (!isFaceUp())
            return "data/laketileback.png";

        return super.getImage();
    }

    public boolean isStartingCard()
    {
        return isStartingCard;
    }

    public void setStartingCard(boolean isStartingCard)
    {
        this.isStartingCard = isStartingCard;
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

    public ColorEnum get_topColor()
    {
        return m_topColor;
    }

    public void set_topColor(ColorEnum m_topColor)
    {
        this.m_topColor = m_topColor;
    }

    public ColorEnum get_bottomColor()
    {
        return m_bottomColor;
    }

    public void set_bottomColor(ColorEnum m_bottomColor)
    {
        this.m_bottomColor = m_bottomColor;
    }

    public ColorEnum get_rightColor()
    {
        return m_rightColor;
    }

    public void set_rightColor(ColorEnum m_rightColor)
    {
        this.m_rightColor = m_rightColor;
    }

    public ColorEnum get_leftColor()
    {
        return m_leftColor;
    }

    public void set_leftColor(ColorEnum m_leftColor)
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

    @Override
    public CardType getCardType()
    {
        return CardType.LAKETILE;
    }

    @Override
    public int compareTo(Card o)
    {
        return 0;
    }

    public int getRotateDegrees()
    {
        return rotateDegrees;
    }

    public void setRotateDegrees(int rotateDegrees)
    {
        this.rotateDegrees = rotateDegrees;
    }

    public String toString()
    {
        return "Lake Tile Card " + m_cardIndex + " Directions: [" + m_topColor
                + m_bottomColor + m_rightColor + m_leftColor;
    }
}
