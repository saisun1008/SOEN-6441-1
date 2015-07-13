package hf.game;

import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.items.DedicationToken;
import hf.game.items.FavorToken;
import hf.game.items.LakeTile;
import hf.game.items.LanternCard;
import hf.game.items.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Game board should contain everything the game needs, players, decks of
 * cards...
 * 
 * @author Sai
 *
 */
public class GameBoard
{

    /**
     * Collections of all cards needed for the game, this is the only copy in
     * the application, other object will only take card's index
     * 
     */
    private ArrayList<LanternCard> m_LatternCollection;
    private ArrayList<LakeTile> m_LakeTileCollection;
    private ArrayList<DedicationToken> m_DedicationTokenCollection;
    private ArrayList<FavorToken> m_FavorTokenCollection;

    /**
     * Decks of cards used in current game board, only contains indexes of the
     * cards
     */
    private HashMap<ColorEnum, ArrayList<Integer>> m_LatternDecks;
    private ArrayList<Integer> m_LakeTileDeck;
    private HashMap<ColorEnum, ArrayList<Integer>> m_DedicationTokenDecks;
    /**
     * Player list
     */

    private ArrayList<Player> m_players;

    /**
     * Number of players
     */
    private int numPlayer = 0;

    /**
     * Integer value to indicate which player should be taking this run, it's
     * related to the LocationEnum ENUM Order is TOP->LEFT->BOT->RIGHT, its
     * range is [0-3]
     * 
     * @see LocationEnum
     */
    private int roundExecutor = 0;

    /**
     * Initialize Card collections
     */
    public GameBoard()
    {
        m_LatternCollection = new ArrayList<LanternCard>();
        m_LakeTileCollection = new ArrayList<LakeTile>();
        m_DedicationTokenCollection = new ArrayList<DedicationToken>();
        m_FavorTokenCollection = new ArrayList<FavorToken>();

        m_LatternDecks = new HashMap<ColorEnum, ArrayList<Integer>>();
        m_LakeTileDeck = new ArrayList<Integer>();
        m_DedicationTokenDecks = new HashMap<ColorEnum, ArrayList<Integer>>();

        m_players = new ArrayList<Player>();
        roundExecutor = 0;
    }

    public ArrayList<LanternCard> getLatternCollection()
    {
        return m_LatternCollection;
    }

    /**
     * Get player who should be playing for current game round
     * 
     * @return player
     */
    public Player getCurrentRoundPlayer()
    {
        return m_players.get(roundExecutor);
    }

    /**
     * Make new round, increase the round token
     */
    public void makeNewRound()
    {
        roundExecutor++;
        if (roundExecutor >= numPlayer)
        {
            roundExecutor = 0;
        }
    }

    /**
     * Return the start player represented by his/her direction
     * 
     * @return direction of the start player for the game
     */
    public LocationEnum getStartPlayerByDirection()
    {
        return getCurrentRoundPlayer().getSitLocation();
    }

    /**
     * Set how many players are in the game
     * 
     * @param num
     */
    public void setNumPlayer(int num)
    {
        numPlayer = num;
    }

    /**
     * initialize the roundExecutor, random among the number of players
     */
    public void getRandomStartPlayer()
    {
        roundExecutor = new Random().nextInt(numPlayer - 1);
    }

    /**
     * Get number count of current players
     * 
     * @return player count
     */
    public int getPlayerCount()
    {
        return numPlayer;
    }

    public void setLatternCollection(ArrayList<LanternCard> m_LatternCollection)
    {
        this.m_LatternCollection = m_LatternCollection;
    }

    public ArrayList<LakeTile> getLakeTileCollection()
    {
        return m_LakeTileCollection;
    }

    public void setLakeTileCollection(ArrayList<LakeTile> m_LakeTileCollection)
    {
        this.m_LakeTileCollection = m_LakeTileCollection;
    }

    public ArrayList<DedicationToken> getDedicationTokenCollection()
    {
        return m_DedicationTokenCollection;
    }

    public void setDedicationTokenCollection(
            ArrayList<DedicationToken> m_DedicationTokenCollection)
    {
        this.m_DedicationTokenCollection = m_DedicationTokenCollection;
    }

    public ArrayList<FavorToken> getFavorTokenCollection()
    {
        return m_FavorTokenCollection;
    }

    public void setFavorTokenCollection(
            ArrayList<FavorToken> m_FavorTokenCollection)
    {
        this.m_FavorTokenCollection = m_FavorTokenCollection;
    }

    public HashMap<ColorEnum, ArrayList<Integer>> getLatternDecks()
    {
        return m_LatternDecks;
    }

    public void setLatternDeck(
            HashMap<ColorEnum, ArrayList<Integer>> m_LatternDecks)
    {
        this.m_LatternDecks = m_LatternDecks;
    }

    public ArrayList<Integer> getLakeTileDeck()
    {
        return m_LakeTileDeck;
    }

    public void setLakeTileDeck(ArrayList<Integer> m_LakeTileDeck)
    {
        this.m_LakeTileDeck = m_LakeTileDeck;
    }

    public HashMap<ColorEnum, ArrayList<Integer>> getDedicationTokenDeck()
    {
        return m_DedicationTokenDecks;
    }

    public void setDedicationTokenDeck(
            HashMap<ColorEnum, ArrayList<Integer>> m_DedicationTokenDeck)
    {
        this.m_DedicationTokenDecks = m_DedicationTokenDeck;
    }

    public ArrayList<Player> getPlayers()
    {
        return m_players;
    }

    public void setPlayers(ArrayList<Player> m_players)
    {
        this.m_players = m_players;
    }

    /**
     * Get player by providing a location
     * 
     * @param loc
     *            location to find the player
     * @return Player object
     */
    public Player getPlayerByLocation(LocationEnum loc)
    {
        for (Player p : m_players)
        {
            if (p.getSitLocation() == loc)
            {
                return p;
            }
        }

        return null;
    }
}
