package hf.game.items;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Player
{
    private final HashMap<ColorEnum, ArrayList<Integer>> m_lanternList = new HashMap<ColorEnum, ArrayList<Integer>>();
    private final ArrayList<Integer> m_lakeTileList = new ArrayList<Integer>();
    private final HashMap<ColorEnum, ArrayList<Integer>> m_dedicationTokenList = new HashMap<ColorEnum, ArrayList<Integer>>();
    private final ArrayList<Integer> m_favorTokenList = new ArrayList<Integer>();
    private StartPlayerMarker m_startMarker = null;
    private String m_name = null;
    private LocationEnum m_sitLocation = null;
    private GameBoard m_currentBoard = null;

    /**
     * Default constructor, include name of the player, sit position of the
     * player and game board it's playing on
     * 
     * @param name
     *            name of the player
     * @param sitLocation
     *            sit location of the player
     * @param board
     *            game board object it's located at
     */
    public Player(String name, LocationEnum sitLocation, GameBoard board)
    {
        m_name = name;
        m_sitLocation = sitLocation;
        m_lakeTileList.clear();
        m_lakeTileList.clear();
        m_dedicationTokenList.clear();
        m_favorTokenList.clear();
        m_currentBoard = board;
    }

    public void takeCard(Card card)
    {
        switch (card.getCardType())
        {
        case DEDICATION:
            ArrayList<Integer> list;
            if (m_dedicationTokenList.containsKey(((DedicationToken) card)
                    .getColor()))
            {
                list = m_dedicationTokenList.get(((DedicationToken) card)
                        .getColor());
                list.add(card.getIndex());

            } else
            {
                list = new ArrayList<Integer>();
                list.add(card.getIndex());

            }
            m_dedicationTokenList
                    .put(((DedicationToken) card).getColor(), list);
            break;

        case FAVOR:
            m_favorTokenList.add(card.getIndex());
            break;
        case LATERN:
            ArrayList<Integer> list1;
            if (m_lanternList.containsKey(((LanternCard) card).getColor()))
            {
                list1 = m_lanternList.get(((LanternCard) card).getColor());
                list1.add(card.getIndex());

            } else
            {
                list1 = new ArrayList<Integer>();
                list1.add(card.getIndex());

            }
            m_lanternList.put(((LanternCard) card).getColor(), list1);
            break;

        case LAKETILE:
            m_lakeTileList.add(card.getIndex());
            // we may need to sort
            // Collections.sort(m_lakeTileList);
            break;
        }
    }

    public String getName()
    {
        return m_name;
    }

    public String toString()
    {
        return m_name + " at position " + m_sitLocation;
    }

    public LocationEnum getSitLocation()
    {
        return m_sitLocation;
    }
}
