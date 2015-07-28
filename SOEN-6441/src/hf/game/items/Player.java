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
    private final HashMap<ColorEnum, ArrayList<Integer>> lanternList = new HashMap<ColorEnum, ArrayList<Integer>>();
    private final ArrayList<Integer> lakeTileList = new ArrayList<Integer>();
    private final HashMap<ColorEnum, ArrayList<Integer>> dedicationTokenList = new HashMap<ColorEnum, ArrayList<Integer>>();
    private final ArrayList<Integer> favorTokenList = new ArrayList<Integer>();
    private int score = 0;

    public HashMap<ColorEnum, ArrayList<Integer>> getLanternList()
    {
        return lanternList;
    }

    public ArrayList<Integer> getLakeTileList()
    {
        return lakeTileList;
    }

    public HashMap<ColorEnum, ArrayList<Integer>> getDedicationTokenList()
    {
        return dedicationTokenList;
    }

    public ArrayList<Integer> getFavorTokenList()
    {
        return favorTokenList;
    }

    // not use delete by yin private StartPlayerMarker m_startMarker = null;
    private String m_name = null;
    private LocationEnum m_sitLocation = null;

    // not use delete by yin private GameBoard m_currentBoard = null;

    /**
     * Default constructor, include name of the player, sit position of the
     * player and game board it's playing on
     * 
     * @param name
     *            name of the player
     * @param sitLocation
     *            sit location of the player
     */
    public Player(String name, LocationEnum sitLocation)
    {
        m_name = name;
        m_sitLocation = sitLocation;
        lakeTileList.clear();
        lakeTileList.clear();
        dedicationTokenList.clear();
        favorTokenList.clear();
        // not use delete by yin m_currentBoard = board;
    }
    /**
     * Take card from the board
     * 
     * @param card
     */
    public void takeCard(Card card)
    {
        switch (card.getCardType())
        {
        case DEDICATION:
            ArrayList<Integer> list;
            if (dedicationTokenList.containsKey(((DedicationToken) card)
                    .getColor()))
            {
                list = dedicationTokenList.get(((DedicationToken) card)
                        .getColor());
                list.add(card.getIndex());

            } else
            {
                list = new ArrayList<Integer>();
                list.add(card.getIndex());

            }
            dedicationTokenList.put(((DedicationToken) card).getColor(), list);
            break;

        case FAVOR:
            favorTokenList.add(card.getIndex());
            break;
        case LATERN:
            ArrayList<Integer> list1;
            if (lanternList.containsKey(((LanternCard) card).getColor()))
            {
                list1 = lanternList.get(((LanternCard) card).getColor());
                list1.add(card.getIndex());

            } else
            {
                list1 = new ArrayList<Integer>();
                list1.add(card.getIndex());

            }
            lanternList.put(((LanternCard) card).getColor(), list1);
            break;

        case LAKETILE:
            lakeTileList.add(card.getIndex());
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

    public void setScore(int score)
    {
        this.score = score;
    }
}
