package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;

public class DedicationToken extends Card
{

    private ColorEnum m_color;
    private int m_numDots = 0;
    
    /**
     * Default constructor of the dedication token
     * 
     * @param value
     *            value of the dedication token
     * @param image
     *            accept null value, image of the dedication token
     * @param index
     *            index of the card
     * @param color
     *            color of the card
     * @param numDots
     *            number of dots
     */
    public DedicationToken(int value, String image, int index, ColorEnum color,
            int numDots)
    {
        super(value, index);
        super.setImage(image);
        m_color = color;
        m_numDots = numDots;
    }

    public CardType getCardType()
    {
        return CardType.DEDICATION;
    }

    @Override
    public int compareTo(Card o)
    {
        return 0;
    }

    public ColorEnum getColor()
    {
        return m_color;
    }

    /**
     * Get number of dots on the card
     * 
     * @return number of dots
     */
    public int getNumDots()
    {
        return m_numDots;
    }
}
