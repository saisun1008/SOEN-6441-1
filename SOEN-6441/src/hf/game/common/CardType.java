package hf.game.common;

/**
 * Card type enum class, contains number of specific cards
 * 
 * @author Sai
 *
 */
public enum CardType
{
    LATERN(56), LAKETILE(36), DEDICATION(30), FAVOR(20);

    private int m_numberOfCard;

    private CardType(int numberOfCard)
    {
        m_numberOfCard = numberOfCard;
    }

    public int getNumberOfCard()
    {
        return m_numberOfCard;
    }
}
