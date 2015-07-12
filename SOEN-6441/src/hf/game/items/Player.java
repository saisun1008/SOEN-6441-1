package hf.game.items;

import hf.game.GameBoard;
import hf.game.common.Direction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Player
{
    private final ArrayList<Integer> m_lanternList = new ArrayList<Integer>();
    private final ArrayList<Integer> m_lakeTileList = new ArrayList<Integer>();
    private final ArrayList<Integer> m_dedicationTokenList = new ArrayList<Integer>();
    private final ArrayList<Integer> m_favorTokenList = new ArrayList<Integer>();
    private StartPlayerMarker m_startMarker = null;
    private String m_name = null;
    private Direction m_sitLocation = null;
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
    public Player(String name, Direction sitLocation, GameBoard board)
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
            m_dedicationTokenList.add(card.getIndex());
            break;

        case FAVOR:
            m_favorTokenList.add(card.getIndex());
            break;
        case LATERN:
            m_lanternList.add(card.getIndex());
            Collections.sort(m_lanternList);
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
}
