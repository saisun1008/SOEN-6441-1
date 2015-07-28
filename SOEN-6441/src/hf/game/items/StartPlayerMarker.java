package hf.game.items;
/**
 * Common attributes and functions for starting marker
 * @author Sai
 *
 */
public class StartPlayerMarker
{
    private Player m_owner = null;
    private static StartPlayerMarker m_instance = null;

    protected StartPlayerMarker()
    {
    }

    /**
     * get instance
     * @return instance
     */
    public static StartPlayerMarker getInstance()
    {
        if (m_instance == null)
        {
            m_instance = new StartPlayerMarker();
        }
        return m_instance;
    }

    /**
     * assign card to players
     * @param p
     * @return true if successfully assigned
     */
    public boolean assignToPlayer(Player p)
    {
        m_owner = p;
        return true;
    }
}
