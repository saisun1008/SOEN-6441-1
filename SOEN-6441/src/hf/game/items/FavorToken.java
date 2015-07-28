package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;
/**
 * Common attributes and functions for favor tokens
 * @author Sai
 *
 */
public class FavorToken extends Card
{

    /**
     * Default constructor to favor token
     * 
     * @param image
     *            accept null value, image of the token
     * @param index
     *            index of the card
     */
    public FavorToken(String image, int index)
    {
        super(1, index);
        super.setImage(image);
    }

    /**
     * get card type
     * @return card type
     */
    public CardType getCardType()
    {
        return CardType.FAVOR;
    }

    /**
     *override compare function
     *@return 0
     */
    @Override
    public int compareTo(Card o)
    {
        return 0;
    }
}
