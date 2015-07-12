package hf.game.items;

public class StartPlayerMarker
{
    private Player m_owner = null;
    private static StartPlayerMarker m_instance = null;

    protected StartPlayerMarker()
    {
    }

    public static StartPlayerMarker getInstance()
    {
        if (m_instance == null)
        {
            m_instance = new StartPlayerMarker();
        }
        return m_instance;
    }

    public boolean assignToPlayer(Player p)
    {
        m_owner = p;
        return true;
    }
}