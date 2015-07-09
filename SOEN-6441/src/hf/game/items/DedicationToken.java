package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;

public class DedicationToken extends Card
{

    /**
     * Default constructor of the dedication token
     * 
     * @param value
     *            value of the dedication token
     * @param image
     *            accept null value, image of the dedication token
     */
    public DedicationToken(int value, BufferedImage image)
    {
        super(value);
        super.setImage(image);
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
}
