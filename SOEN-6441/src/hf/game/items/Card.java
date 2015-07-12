package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;

/**
 * Abstract card class contains common attributes and functions for cards
 * 
 * @author Sai
 *
 */
public abstract class Card implements Comparable<Card>
{
    protected Player m_owner = null;
    protected String m_image = null;
    protected int m_value = 0;

    protected int x;
    protected int y;
    protected int size;

    protected int m_cardIndex = 0;

    /**
     * Default constructor to set value of the card
     * 
     * @param value
     *            value of the card
     * 
     * @param index
     *            index of the card
     */
    public Card(int value, int index)
    {
        m_value = value;
        m_cardIndex = index;
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
        if (player != null)
        {
            player.takeCard(this);
        }
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
    public String getImage()
    {
        return m_image;
    }

    /**
     * Set card image
     * 
     * @param image
     *            image to be used by the card
     */
    public void setImage(String image)
    {
        m_image = image;
    }

    /**
     * Get card value
     * 
     * @return
     */
    public int getCardValue()
    {
        return m_value;
    }

    /**
     * Get owner of the card
     * 
     * @return
     */
    public Player getOwner()
    {
        return m_owner;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    /**
     * Get index of the card
     * 
     * @return
     */
    public int getIndex()
    {
        return m_cardIndex;
    }

    public void setIndex(int index)
    {
        m_cardIndex = index;
    }
}
