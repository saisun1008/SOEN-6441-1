package hf.game;

import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.controller.GameController;
import hf.game.items.DedicationToken;
import hf.game.items.FavorToken;
import hf.game.items.LakeTile;
import hf.game.items.LanternCard;
import hf.game.items.Player;
import hf.ui.matrix.MatrixCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private Map<Integer, Integer> matrixLocation_index = new HashMap<>();

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
     * matrix entities
     */
    Map<Integer, MatrixCell> m_entities;

    /**
     * Variable to indicate game has ended or not
     */
    public boolean gameEnded = false;
    public boolean shouldUpdateMatrix = false;

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
        m_entities = new HashMap<Integer, MatrixCell>();
        matrixLocation_index.put(221, getStartLakeTileIndex());
    }

    /**
     * Get entities
     * 
     * @return Map<Integer, MatrixCell>
     */
    public Map<Integer, MatrixCell> getEntities()
    {
        return m_entities;
    }

    /**
     * Set entities
     * 
     * @param entities
     */
    public void setEntities(Map<Integer, MatrixCell> entities)
    {
        this.m_entities = entities;
    }

    /**
     * Get lantern Collection
     * 
     * @return ArrayList<LanternCard>
     */
    public ArrayList<LanternCard> getLatternCollection()
    {
        return m_LatternCollection;
    }

    /**
     * Get lake tile index from the beginning
     * 
     * @return late tile index
     */
    private int getStartLakeTileIndex()
    {
        for (LakeTile t : m_LakeTileCollection)
        {
            if (t.isFaceUp())
            {
                return t.getIndex();
            }
        }
        return 0;
    }

    /**
     * Get player who should be playing for current game round
     * 
     * @return player
     */
    public Player getCurrentRoundPlayer()
    {
        return m_players.get(numPlayer - roundExecutor - 1);
    }

    /**
     * Make new round, increase the round token
     * 
     * @return new round token
     */
    public String makeNewRound()
    {
        if (!gameEnded)
        {
            roundExecutor++;
            if (roundExecutor >= numPlayer)
            {
                roundExecutor = 0;
            }
        }
        return verifyGameEndState();
    }

    /**
     * Verify game state by checking how many lake tiles are still in players'
     * hands and deck. If none left, then game has ended
     * 
     * @return true if game has ended, otherwise false
     */
    private String verifyGameEndState()
    {
        int lakeTileCnt = 0;
        int maxScore = 0;
        int winner = 0;
        String ret = "";
        for (Player player : m_players)
        {
            lakeTileCnt = lakeTileCnt + player.getLakeTileList().size();
            int score = 0;
            for (ColorEnum index : player.getDedicationTokenList().keySet())
            {
                for (int i : player.getDedicationTokenList().get(index))
                {
                    score += getDedicationTokenByIndex(i).getCardValue();
                }

            }
            if (maxScore < score)
            {
                winner = m_players.indexOf(player);
                maxScore = score;
            }
            player.setScore(score);
            ret = ret.concat(m_players.get(m_players.indexOf(player)).getName()
                    + "score:" + score + "\n ");
        }

        lakeTileCnt += m_LakeTileDeck.size();

        if (lakeTileCnt == 0)
        {
            // System.out.println(m_players.get(winner).getName() +
            // " has won!!");
            gameEnded = true;
        }

        ret = ret.concat(m_players.get(winner).getName() + " has won!!");
        return ret;
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
        roundExecutor = new Random().nextInt(numPlayer);
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

    /**
     * Set lantern collection
     * 
     * @param m_LatternCollection
     */
    public void setLatternCollection(ArrayList<LanternCard> m_LatternCollection)
    {
        this.m_LatternCollection = m_LatternCollection;
    }

    /**
     * Get lake tile collection
     * 
     * @return ArrayList<LakeTile>
     */
    public ArrayList<LakeTile> getLakeTileCollection()
    {
        return m_LakeTileCollection;
    }

    /**
     * Set Lake Tile Collection
     * 
     * @param m_LakeTileCollection
     */
    public void setLakeTileCollection(ArrayList<LakeTile> m_LakeTileCollection)
    {
        this.m_LakeTileCollection = m_LakeTileCollection;
    }

    /**
     * Set Lake Tile by index
     * 
     * @param index
     * @param lakeTile
     */
    public void setLakeTileByIndex(int index, LakeTile lakeTile)
    {
        this.m_LakeTileCollection.set(index, lakeTile);
    }

    /**
     * Get dedication token collection
     * 
     * @return ArrayList<DedicationToken>
     */
    public ArrayList<DedicationToken> getDedicationTokenCollection()
    {
        return m_DedicationTokenCollection;
    }

    /**
     * Set Dedication Token Collection
     * 
     * @param m_DedicationTokenCollection
     */
    public void setDedicationTokenCollection(
            ArrayList<DedicationToken> m_DedicationTokenCollection)
    {
        this.m_DedicationTokenCollection = m_DedicationTokenCollection;
    }

    /**
     * Get Favor Token Collection
     * 
     * @return ArrayList<FavorToken>
     */
    public ArrayList<FavorToken> getFavorTokenCollection()
    {
        return m_FavorTokenCollection;
    }

    /**
     * Set Favor Token Collection
     * 
     * @param m_FavorTokenCollection
     */
    public void setFavorTokenCollection(
            ArrayList<FavorToken> m_FavorTokenCollection)
    {
        this.m_FavorTokenCollection = m_FavorTokenCollection;
    }

    /**
     * Get lantern decks
     * 
     * @return HashMap<ColorEnum, ArrayList<Integer>>
     */
    public HashMap<ColorEnum, ArrayList<Integer>> getLatternDecks()
    {
        return m_LatternDecks;
    }

    /**
     * Set lantern deck
     * 
     * @param m_LatternDecks
     */
    public void setLatternDeck(
            HashMap<ColorEnum, ArrayList<Integer>> m_LatternDecks)
    {
        this.m_LatternDecks = m_LatternDecks;
    }

    /**
     * Get lake tile deck
     * 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getLakeTileDeck()
    {
        return m_LakeTileDeck;
    }

    /**
     * Set lake tile deck
     * 
     * @param m_LakeTileDeck
     */
    public void setLakeTileDeck(ArrayList<Integer> m_LakeTileDeck)
    {
        this.m_LakeTileDeck = m_LakeTileDeck;
    }

    /**
     * Get dedocation token deck
     * 
     * @return HashMap<ColorEnum, ArrayList<Integer>>
     */
    public HashMap<ColorEnum, ArrayList<Integer>> getDedicationTokenDeck()
    {
        return m_DedicationTokenDecks;
    }

    /**
     * Set dedication token deck
     * 
     * @param m_DedicationTokenDeck
     */
    public void setDedicationTokenDeck(
            HashMap<ColorEnum, ArrayList<Integer>> m_DedicationTokenDeck)
    {
        this.m_DedicationTokenDecks = m_DedicationTokenDeck;
    }

    /**
     * Get players
     * 
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers()
    {
        return m_players;
    }

    /**
     * Set players
     * 
     * @param m_players
     */
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

    /**
     * Get lake tile object by its index
     * 
     * @param index
     * @return Lake tile object
     */
    public LakeTile getLakeTileByIndex(int index)
    {
        return m_LakeTileCollection.get(index);
    }

    /**
     * Get LanternCard object by its index
     * 
     * @param index
     * @return LanternCard object
     */
    public LanternCard getLanterCardByIndex(int index)
    {
        return m_LatternCollection.get(index);
    }

    /**
     * Get DedicationToken object by its index
     * 
     * @param index
     * @return DedicationToken object
     */
    public DedicationToken getDedicationTokenByIndex(int index)
    {
        return m_DedicationTokenCollection.get(index);
    }

    /**
     * Get FavorToken object by its index
     * 
     * @param index
     * @return FavorToken object
     */
    public FavorToken getFavorTokenByIndex(int index)
    {
        return m_FavorTokenCollection.get(index);
    }

    /**
     * Get Matrix Location Index
     * 
     * @return Map<Integer, Integer>
     */
    public Map<Integer, Integer> getMatrixLocationIndex()
    {
        return matrixLocation_index;
    }

    /**
     * Set Matrix Location Index
     * 
     * @param indexMatrixLocation
     */
    public void setMatrixLocationIndex(Map<Integer, Integer> indexMatrixLocation)
    {
        this.matrixLocation_index = indexMatrixLocation;
    }

    public void manuallySetMap(Map<Integer, Integer> indexMatrixLocation)
    {
        this.matrixLocation_index = indexMatrixLocation;
        GameController.getInstance().getGameView().getMatrix()
                .getMatrixCalculator().initMatrixCell();
    }

    /**
     * Exchange four of a kind lantern cards
     * 
     * @param exchangeList
     *            list contains cards to be exchanged
     * @return true if successful
     */
    public boolean exchangeFourOfKind(HashMap<ColorEnum, Integer> exchangeList)
    {
        boolean result = false;
        // valid if the exchange can be done or not
        for (ColorEnum color : exchangeList.keySet())
        {
            // if has enough cards
            if (exchangeList.get(color) >= 4)
            {
                for (int i = 0; i < 4; i++)
                {
                    // place the cards into deck
                    m_LatternDecks.get(color).add(
                            getCurrentRoundPlayer().getLanternList().get(color)
                                    .get(0));
                    // first remove lantern cards from player hand
                    getCurrentRoundPlayer().getLanternList().get(color)
                            .remove(0);
                }
                // give player one red dedication token
                ArrayList<Integer> origin = getCurrentRoundPlayer()
                        .getDedicationTokenList().get(ColorEnum.RED);
                if (origin == null)
                {
                    origin = new ArrayList<Integer>();
                }
                origin.add(m_DedicationTokenDecks.get(ColorEnum.RED).get(0));
                m_DedicationTokenDecks.get(ColorEnum.RED).remove(0);

                getCurrentRoundPlayer().getDedicationTokenList().put(
                        ColorEnum.RED, origin);
                result = true;
            }
        }
        return result;
    }

    /**
     * Exchange three pair lantern cards
     * 
     * @param exchangeList
     *            list contains cards to be exchanged
     * @return true if successful
     */
    public boolean exchangeThreePair(HashMap<ColorEnum, Integer> exchangeList)
    {
        boolean result = false;
        int valid = 0;
        // valid if the exchange can be done or not
        for (ColorEnum color : exchangeList.keySet())
        {
            // if has enough cards
            if (exchangeList.get(color) >= 2)
            {
                valid++;
            }
        }
        if (valid < 3)
        {
            return false;
        }
        for (ColorEnum color : exchangeList.keySet())
        {
            // if has enough cards
            if (exchangeList.get(color) >= 2)
            {
                for (int i = 0; i < 2; i++)
                {
                    // place the cards into deck
                    m_LatternDecks.get(color).add(
                            getCurrentRoundPlayer().getLanternList().get(color)
                                    .get(0));
                    // first remove lantern cards from player hand
                    getCurrentRoundPlayer().getLanternList().get(color)
                            .remove(0);
                }

            }
        }
        // give player one blue dedication token
        ArrayList<Integer> origin = getCurrentRoundPlayer()
                .getDedicationTokenList().get(ColorEnum.BLUE);
        if (origin == null)
        {
            origin = new ArrayList<Integer>();
        }
        origin.add(m_DedicationTokenDecks.get(ColorEnum.BLUE).get(0));
        m_DedicationTokenDecks.get(ColorEnum.BLUE).remove(0);

        getCurrentRoundPlayer().getDedicationTokenList().put(ColorEnum.BLUE,
                origin);
        result = true;
        return result;
    }

    /**
     * Exchange seven unique lantern cards
     * 
     * @param exchangeList
     *            list contains cards to be exchanged
     * @return true if successful
     */
    public boolean exchangeSevenUnique(HashMap<ColorEnum, Integer> exchangeList)
    {
        boolean result = false;
        int valid = 0;
        // valid if the exchange can be done or not
        for (ColorEnum color : exchangeList.keySet())
        {
            // if has enough cards
            if (exchangeList.get(color) >= 1)
            {
                valid++;
            }
        }
        if (valid < 7)
        {
            return false;
        }
        for (ColorEnum color : exchangeList.keySet())
        {
            // if has enough cards
            if (exchangeList.get(color) >= 1)
            {
                for (int i = 0; i < 1; i++)
                {
                    // place the cards into deck
                    m_LatternDecks.get(color).add(
                            getCurrentRoundPlayer().getLanternList().get(color)
                                    .get(0));
                    // first remove lantern cards from player hand
                    getCurrentRoundPlayer().getLanternList().get(color)
                            .remove(0);
                }
            }
        }

        // give player one GREEN dedication token
        ArrayList<Integer> origin = getCurrentRoundPlayer()
                .getDedicationTokenList().get(ColorEnum.GREEN);
        if (origin == null)
        {
            origin = new ArrayList<Integer>();
        }
        origin.add(m_DedicationTokenDecks.get(ColorEnum.GREEN).get(0));
        m_DedicationTokenDecks.get(ColorEnum.GREEN).remove(0);

        getCurrentRoundPlayer().getDedicationTokenList().put(ColorEnum.GREEN,
                origin);
        result = true;
        return result;
    }

    /**
     * Current player use favor token to switch a lantern card
     * 
     * @param cardToGiveback
     *            card to put back to deck
     * @param cardToTake
     *            card to take
     * @return true if successful
     */
    public boolean useFavorToken(ColorEnum cardToGiveback, ColorEnum cardToTake)
    {
        // remove two favor tokens from player
        getCurrentRoundPlayer().getFavorTokenList().remove(0);
        getCurrentRoundPlayer().getFavorTokenList().remove(0);

        // put @cardToGiveback to deck
        m_LatternDecks.get(cardToGiveback).add(
                getCurrentRoundPlayer().getLanternList().get(cardToGiveback)
                        .get(0));

        // take cardToTake from deck
        getCurrentRoundPlayer().getLanternList().get(cardToTake)
                .add(m_LatternDecks.get(cardToTake).get(0));
        m_LatternDecks.get(cardToTake).remove(0);
        return true;
    }
}
