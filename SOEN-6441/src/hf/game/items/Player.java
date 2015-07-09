package hf.game.items;

import hf.game.common.Direction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player
{
    private final ArrayList<LanternCard> m_lanternList = new ArrayList<LanternCard>();
    private final ArrayList<LakeTile> m_lakeTileList = new ArrayList<LakeTile>();
    private final ArrayList<DedicationToken> m_dedicationTokenList = new ArrayList<DedicationToken>();
    private final ArrayList<FavorToken> m_favorTokenList = new ArrayList<FavorToken>();
    private StartPlayerMarker m_startMarker = null;
    private String m_name = null;
    private Direction m_sitLocation = null;

    public Player(String name, Direction sitLocation)
    {
        m_name = name;
        m_sitLocation = sitLocation;
        m_lakeTileList.clear();
        m_lakeTileList.clear();
        m_dedicationTokenList.clear();
        m_favorTokenList.clear();
    }

    public void takeCard(Card card)
    {
        switch (card.getCardType())
        {
        case DEDICATION:
            m_dedicationTokenList.add((DedicationToken) card);
            validateCardList(m_dedicationTokenList);
            break;

        case FAVOR:
            m_favorTokenList.add((FavorToken) card);
            validateCardList(m_favorTokenList);
            break;
        case LATERN:
            m_lanternList.add((LanternCard) card);
            validateCardList(m_lanternList);
            Collections.sort(m_lanternList);
            break;

        case LAKETILE:
            m_lakeTileList.add((LakeTile) card);
            validateCardList(m_lakeTileList);
            // we may need to sort
            // Collections.sort(m_lakeTileList);
            break;
        }
    }

    /**
     * Check if the card list has any card not belonging to the current player
     * 
     * @param list
     *            list of card to be validated
     */
    private void validateCardList(ArrayList<? extends Card> list)
    {
        for (Card card : list)
        {
            if (card.getOwner() == null)
            {
                list.remove(card);
            }
        }
    }
}
