package hf.game.items;

import java.awt.image.BufferedImage;

import hf.game.common.CardType;

public abstract class Card implements Comparable<Card>
{
    protected Player m_owner = null;
    protected String m_image = null;
    protected int m_value = 0;
    protected String id;
    protected int x;
    protected int y;
    protected int size;
    
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

    public int getCardValue()
    {
        return m_value;
    }

    public Player getOwner()
    {
        return m_owner;
    }
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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
    
    public Card()
    {
        
    }
}
