package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;

public abstract class Card implements Comparable<Card>
{
    protected Player m_owner = null;
    protected BufferedImage m_image = null;
    protected int m_value = 0;

    /**
     * Default constructor to set value of the card
     * 
     * @param value
     *            value of the card
     */
    public Card(int value)
    {
        m_value = value;
    }

    /**
     * Assign a card to a player
     * 
     * @param player
     *            player to be assigned with the card
     */
    public void assignToPlayer(Player player)
    {
        m_owner = player;
        player.takeCard(this);
    }

    /**
     * Return the card to deck
     */
    public void returnToDeck()
    {
        m_owner = null;
    }

    /**
     * Get card type
     * 
     * @return card type @see CardType
     */
    public abstract CardType getCardType();

    /**
     * Get image for current card
     * 
     * @return image of the card
     */
    public BufferedImage getImage()
    {
        return m_image;
    }

    /**
     * Set card image
     * 
     * @param image
     *            image to be used by the card
     */
    public void setImage(BufferedImage image)
    {
        m_image = image;
    }

    public int getCardValue()
    {
        return m_value;
    }

    public Player getOwner()
    {
        return m_owner;
    }
}
