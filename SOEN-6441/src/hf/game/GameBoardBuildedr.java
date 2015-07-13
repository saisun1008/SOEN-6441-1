package hf.game;

import hf.game.common.CardType;
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
 * GameBoardBuilder should contain the functions to initialize or change game
 * board contents
 * 
 * @author Sai
 *
 */
public class GameBoardBuildedr
{
    private final GameBoard m_board;

    public GameBoardBuildedr(GameBoard board)
    {
        m_board = board;
    }

    /**
     * Build all the cards
     */
    public void buildAll()
    {
        loadLakeTileCard();
        loadLatternCard();
        loadDedicationCard();
        loadFavorToken();
    }

    /**
     * Load lake tile cards, now it's randomly creating cards
     */
    private void loadLakeTileCard()
    {
        m_board.getLakeTileCollection().clear();
        ArrayList<LakeTile> tiles = new ArrayList<LakeTile>();
        int index = 0;
        for (; index < CardType.LAKETILE.getNumberOfCard() - 1; index++)
        {
            LakeTile tile = new LakeTile(false, "images/tiles/foldedTile.jpg",
                    ColorEnum.RED, ColorEnum.BLUE, ColorEnum.GREEN,
                    ColorEnum.WHITE, false, index);
            tiles.add(tile);
        }
        // add the special starting card
        LakeTile tile = new LakeTile(false, "images/tiles/foldedTile.jpg",
                ColorEnum.RED, ColorEnum.BLUE, ColorEnum.GREEN,
                ColorEnum.WHITE, false, index);
        tile.setStartingCard(true);
        tiles.add(tile);

        m_board.setLakeTileCollection(tiles);
    }

    /**
     * Load Lattern cards
     */
    private void loadLatternCard()
    {
        m_board.getLatternCollection().clear();
        ArrayList<LanternCard> tiles = new ArrayList<LanternCard>();

        for (int index = 0; index < CardType.LATERN.getNumberOfCard(); index++)
        {
            ColorEnum color = ColorEnum.values()[index % 7];
            LanternCard tile = new LanternCard(color, null, null, index);
            tiles.add(tile);
        }

        m_board.setLatternCollection(tiles);
    }

    /**
     * Load dedication tokens
     */
    private void loadDedicationCard()
    {
        m_board.getDedicationTokenCollection().clear();
        ArrayList<DedicationToken> tiles = new ArrayList<DedicationToken>();

        int index = 0;
        int value = 8;
        ColorEnum color = ColorEnum.RED;

        // generate RED dedication tokens
        for (; index < 10; index++)
        {
            int numDots = 0;
            if (index <= 0)
            {
                value = 8;
            } else if (index <= 2)
            {
                value = 7;
                if (index == 2)
                {
                    numDots = 4;
                }
            } else if (index <= 4)
            {
                value = 6;
                if (index == 4)
                {
                    numDots = 3;
                }
            } else if (index <= 7)
            {
                value = 5;
                if (index == 7)
                {
                    numDots = 3;
                }
            } else if (index <= 9)
            {
                value = 4;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // generate BLUE dedication tokens
        color = ColorEnum.BLUE;
        for (; index < 20; index++)
        {
            int numDots = 0;
            if (index - 10 <= 0)
            {
                value = 9;
            } else if (index - 10 <= 2)
            {
                value = 8;
                if (index - 10 == 2)
                {
                    numDots = 4;
                }
            } else if (index - 10 <= 4)
            {
                value = 7;
                if (index - 10 == 4)
                {
                    numDots = 3;
                }
            } else if (index - 10 <= 6)
            {
                value = 6;
                if (index - 10 == 6)
                {
                    numDots = 3;
                }
            } else if (index - 10 <= 9)
            {
                value = 5;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // generate GREEN dedication tokens
        color = ColorEnum.GREEN;
        for (; index < 30; index++)
        {
            int numDots = 0;
            if (index - 20 <= 0)
            {
                value = 10;
            } else if (index - 20 <= 2)
            {
                value = 9;
                if (index - 20 == 2)
                {
                    numDots = 4;
                }
            } else if (index - 20 <= 4)
            {
                value = 8;
                if (index - 20 == 4)
                {
                    numDots = 3;
                }
            } else if (index - 20 <= 6)
            {
                value = 7;
                if (index - 20 == 6)
                {
                    numDots = 3;
                }
            } else if (index - 20 <= 8)
            {
                value = 6;
            } else
            {
                value = 5;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // Generate 3 grey cards
        for (; index < 33; index++)
        {
            DedicationToken tile = new DedicationToken(4, null, index,
                    ColorEnum.WHITE, 0);
            tiles.add(tile);
        }
        m_board.setDedicationTokenCollection(tiles);
    }

    /**
     * Load favor tokens
     */
    private void loadFavorToken()
    {
        m_board.getFavorTokenCollection().clear();
        ArrayList<FavorToken> tiles = new ArrayList<FavorToken>();
        for (int index = 0; index < CardType.FAVOR.getNumberOfCard(); index++)
        {
            FavorToken tile = new FavorToken("images/favor_token.jpg", index);
            tiles.add(tile);
        }

        m_board.setFavorTokenCollection(tiles);
    }

    /**
     * Build players from given names, and initialize every data structure
     * related to players: decks, initial hand cards, and randomly selected
     * start player
     * 
     * @param names
     *            names of the players
     */
    public void buildPlayers(String[] names)
    {
        ArrayList<Player> list = new ArrayList<Player>();
        for (int i = 0; i < m_board.getPlayerCount(); i++)
        {
            Player p = new Player(names[i], LocationEnum.values()[i], m_board);
            list.add(p);
        }
        m_board.setPlayers(list);
        buildDecks();
        assignInitialHandCards();
        m_board.getRandomStartPlayer();

    }

    /**
     * Build card decks for the game
     */
    public void buildDecks()
    {
        switch (m_board.getPlayerCount())
        {

        // case for 2 players
        case 2:
            // first build lakeTiles
            m_board.setLakeTileDeck(takeRandomSubset(
                    m_board.getLakeTileCollection(), 16, LocationEnum.CENTER));
            // build lantern card decks
            HashMap<ColorEnum, ArrayList<Integer>> decks = new HashMap<ColorEnum, ArrayList<Integer>>();
            for (ColorEnum color : ColorEnum.values())
            {
                // index of the taken lantern cards
                ArrayList<Integer> indexList = new ArrayList<Integer>();
                int count = 0;
                for (LanternCard card : m_board.getLatternCollection())
                {
                    if (count == 5)
                    {
                        break;
                    }
                    if (card.getColor() == color)
                    {
                        indexList.add(card.getIndex());
                        count++;
                    }
                }
                decks.put(color, indexList);
            }

            m_board.setLatternDeck(decks);

            // build dedication token deck
            HashMap<ColorEnum, ArrayList<Integer>> dedicationdecks = new HashMap<ColorEnum, ArrayList<Integer>>();
            dedicationdecks.put(ColorEnum.RED, new ArrayList<Integer>());
            dedicationdecks.put(ColorEnum.BLUE, new ArrayList<Integer>());
            dedicationdecks.put(ColorEnum.GREEN, new ArrayList<Integer>());
            dedicationdecks.put(ColorEnum.WHITE, new ArrayList<Integer>());
            for (DedicationToken token : m_board.getDedicationTokenCollection())
            {
                if (token.getNumDots() != 3 && token.getNumDots() != 4)
                {
                    ArrayList<Integer> list = dedicationdecks.get(token
                            .getColor());
                    list.add(token.getIndex());
                    dedicationdecks.put(token.getColor(), list);
                }
            }

            m_board.setDedicationTokenDeck(dedicationdecks);
            break;

        // case for 3 players
        case 3:
            m_board.setLakeTileDeck(takeRandomSubset(
                    m_board.getLakeTileCollection(), 18, LocationEnum.CENTER));
            // build lantern card decks
            HashMap<ColorEnum, ArrayList<Integer>> decks1 = new HashMap<ColorEnum, ArrayList<Integer>>();
            for (ColorEnum color : ColorEnum.values())
            {
                // index of the taken lantern cards
                ArrayList<Integer> indexList = new ArrayList<Integer>();
                int count = 0;
                for (LanternCard card : m_board.getLatternCollection())
                {
                    if (count == 7)
                    {
                        break;
                    }
                    if (card.getColor() == color)
                    {
                        indexList.add(card.getIndex());
                        count++;
                    }
                }
                decks1.put(color, indexList);
            }

            m_board.setLatternDeck(decks1);

            // build dedication token deck
            HashMap<ColorEnum, ArrayList<Integer>> dedicationdecks1 = new HashMap<ColorEnum, ArrayList<Integer>>();
            dedicationdecks1.put(ColorEnum.RED, new ArrayList<Integer>());
            dedicationdecks1.put(ColorEnum.BLUE, new ArrayList<Integer>());
            dedicationdecks1.put(ColorEnum.GREEN, new ArrayList<Integer>());
            dedicationdecks1.put(ColorEnum.WHITE, new ArrayList<Integer>());
            for (DedicationToken token : m_board.getDedicationTokenCollection())
            {
                if (token.getNumDots() != 4)
                {
                    ArrayList<Integer> list = dedicationdecks1.get(token
                            .getColor());
                    list.add(token.getIndex());
                    dedicationdecks1.put(token.getColor(), list);
                }
            }

            m_board.setDedicationTokenDeck(dedicationdecks1);
            break;

        // case for 4 players
        case 4:
            m_board.setLakeTileDeck(takeRandomSubset(
                    m_board.getLakeTileCollection(), 20, LocationEnum.CENTER));
            // build lantern card decks
            HashMap<ColorEnum, ArrayList<Integer>> decks11 = new HashMap<ColorEnum, ArrayList<Integer>>();
            for (ColorEnum color : ColorEnum.values())
            {
                // index of the taken lantern cards
                ArrayList<Integer> indexList = new ArrayList<Integer>();
                int count = 0;
                for (LanternCard card : m_board.getLatternCollection())
                {
                    if (count == 8)
                    {
                        break;
                    }
                    if (card.getColor() == color)
                    {
                        indexList.add(card.getIndex());
                        count++;
                    }
                }
                decks11.put(color, indexList);
            }

            m_board.setLatternDeck(decks11);

            // build dedication token deck
            HashMap<ColorEnum, ArrayList<Integer>> dedicationdecks11 = new HashMap<ColorEnum, ArrayList<Integer>>();
            dedicationdecks11.put(ColorEnum.RED, new ArrayList<Integer>());
            dedicationdecks11.put(ColorEnum.BLUE, new ArrayList<Integer>());
            dedicationdecks11.put(ColorEnum.GREEN, new ArrayList<Integer>());
            dedicationdecks11.put(ColorEnum.WHITE, new ArrayList<Integer>());
            for (DedicationToken token : m_board.getDedicationTokenCollection())
            {
                ArrayList<Integer> list = dedicationdecks11.get(token
                        .getColor());
                list.add(token.getIndex());
                dedicationdecks11.put(token.getColor(), list);
            }

            m_board.setDedicationTokenDeck(dedicationdecks11);
            break;
        }
    }

    /**
     * Take a random subset from a list
     * 
     * @param items
     *            list of the items
     * @param m
     *            subset size
     * @return randomly selected subset
     */
    private ArrayList<Integer> takeRandomSubset(ArrayList<LakeTile> items,
            int m, LocationEnum assignTarget)
    {
        ArrayList<Integer> res = new ArrayList<Integer>(m);
        Random rnd = new Random();
        int count = 0;
        while (count < m)
        {
            int randomIndex = rnd.nextInt(items.size());
            while (items.get(randomIndex).isStartingCard()
                    || items.get(randomIndex).getOwner() != null)
            {
                randomIndex = rnd.nextInt(items.size());
            }
            res.add(randomIndex);
            if (assignTarget != LocationEnum.CENTER)
            {
                items.get(randomIndex).assignToPlayer(
                        m_board.getPlayerByLocation(assignTarget));
            } else
            {
                items.get(randomIndex).setOwner(assignTarget);
            }
            count++;
        }
        return res;
    }

    /**
     * This function will will assign intial player hand cards to each player
     */
    public void assignInitialHandCards()
    {
        for (Player p : m_board.getPlayers())
        {
            takeRandomSubset(m_board.getLakeTileCollection(), 3,
                    p.getSitLocation());
        }
    }
}
