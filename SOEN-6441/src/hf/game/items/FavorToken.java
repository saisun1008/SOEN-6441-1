package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;

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

    public CardType getCardType()
    {
        return CardType.FAVOR;
    }

    @Override
    public int compareTo(Card o)
    {
        return 0;
    }
}
