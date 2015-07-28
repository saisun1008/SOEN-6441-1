package hf.game.controller;

import hf.game.BoardMapper;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.ColorEnum;
import hf.game.items.DedicationToken;
import hf.game.items.LakeTile;
import hf.game.items.LanternCard;
import hf.ui.matrix.MatrixCell;
import hf.util.FileSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameController implements MatrixObserver
{
    /**
     * A dialog for selecting a file (to load)
     * 
     * @param label
     *            A name for the filter
     * @param extension
     *            The extension to filter
     */

    private static GameBoard board;
    private GameBoardBuildedr builder;
    private ArrayList<BoardObserver> observers = new ArrayList<BoardObserver>();

    public GameBoard getBoard()
    {
        return board;
    }

    private static GameController controller = null;

    protected GameController()
    {
        board = new GameBoard();
        builder = new GameBoardBuildedr(board);
    }

    public static GameController getInstance()
    {
        if (controller == null)
        {
            controller = new GameController();
        }
        return controller;
    }

    public void loadGameFile(String label, String extension)
    {

        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")
                + "/data");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(label,
                extension);
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                fileLoaded(chooser.getSelectedFile());
            } catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Could not find file "
                        + chooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    protected void fileLoaded(File file) throws FileNotFoundException
    {
        board = new BoardMapper().load(new FileInputStream(file));

        if (verifiedLakeTileCard(board) == 0)
        {
            System.out.println("LakeTileCard ok");
        }
        if (verifiedLatternCard(board) == 0)
        {
            System.out.println("LatternCard ok");
        }
        if (verifiedDedicationCard(board) == 0)
        {
            System.out.println("DedicationCard ok");
        }
        if (verifiedFavorToken(board) == 0)
        {
            System.out.println("token ok");
        }
        notifyAllObservers();
    }

    /**
     * verified LakeTile Card in the saved xml file when it loaded project build
     * 1 check the card collection correct;
     * 
     * @param board
     * @return 0 success, 1 failed
     */
    protected int verifiedLakeTileCard(GameBoard board)
    {
        int returnvalue = 0;
        int index = 0;
        // first check lakeTilesCollection
        ArrayList<LakeTile> LakeTileCollection = board.getLakeTileCollection();
        for (index = 0; index < LakeTileCollection.size(); index++)
        {
            LakeTile LakeTileCard = LakeTileCollection.get(index);
            if (LakeTileCard.getIndex() != index)
            {
                returnvalue = 1;
            }
        }
        return returnvalue;
    }

    /**
     * verified Lattern card in the saved xml file when it loaded project build
     * 1 check the card collection correct;
     * 
     * @param board
     * @return 0 success, 1 failed
     */
    protected int verifiedLatternCard(GameBoard board)
    {
        int returnvalue = 0;
        int index = 0;
        int ORANGE = 8, GREEN = 8, PURPLE = 8, WHITE = 8, BLUE = 8, RED = 8, BLACK = 8;
        ArrayList<LanternCard> LanternCardCollection = board
                .getLatternCollection();
        for (index = 0; index < LanternCardCollection.size(); index++)
        {
            LanternCard Lantern = LanternCardCollection.get(index);
            if (Lantern.getColor().name() == "ORANGE")
            {
                ORANGE--;
            } else if (Lantern.getColor().name() == "GREEN")
            {
                GREEN--;
            } else if (Lantern.getColor().name() == "PURPLE")
            {
                PURPLE--;
            } else if (Lantern.getColor().name() == "WHITE")
            {
                WHITE--;
            } else if (Lantern.getColor().name() == "BLUE")
            {
                BLUE--;
            } else if (Lantern.getColor().name() == "RED")
            {
                RED--;
            } else if (Lantern.getColor().name() == "BLACK")
            {
                BLACK--;
            }
        }
        if (ORANGE != 0 && GREEN != 0 && PURPLE != 0 && WHITE != 0 && BLUE != 0
                && RED != 0 && BLACK != 0)
        {
            returnvalue = 1;
        }
        return returnvalue;
    }

    /**
     * verified Dedication card in the saved xml file when it loaded project
     * build 1 check the card collection correct;
     * 
     * @param board
     * @return 0 success, 1 failed
     */
    protected int verifiedDedicationCard(GameBoard board)
    {
        int returnvalue = 0;
        ArrayList<DedicationToken> tiles = new ArrayList<DedicationToken>();
        int index = 0, index_dedication = 0;
        int value = 8;
        ColorEnum color = ColorEnum.RED;

        // generate RED dedication tokens
        for (index = 0; index < 9; index++)
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
            } else if (index <= 8)
            {
                value = 4;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // generate BLUE dedication tokens
        color = ColorEnum.BLUE;
        for (index = 0; index < 9; index++)
        {
            int numDots = 0;
            if (index <= 0)
            {
                value = 9;
            } else if (index <= 2)
            {
                value = 8;
                if (index == 2)
                {
                    numDots = 4;
                }
            } else if (index <= 4)
            {
                value = 7;
                if (index == 4)
                {
                    numDots = 3;
                }
            } else if (index <= 6)
            {
                value = 6;
                if (index == 6)
                {
                    numDots = 3;
                }
            } else if (index <= 8)
            {
                value = 5;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // generate GREEN dedication tokens
        color = ColorEnum.GREEN;
        for (index = 0; index < 9; index++)
        {
            int numDots = 0;
            if (index <= 0)
            {
                value = 10;
            } else if (index <= 2)
            {
                value = 9;
                if (index == 2)
                {
                    numDots = 4;
                }
            } else if (index <= 4)
            {
                value = 8;
                if (index == 4)
                {
                    numDots = 3;
                }
            } else if (index <= 6)
            {
                value = 7;
                if (index == 6)
                {
                    numDots = 3;
                }
            } else if (index <= 7)
            {
                value = 6;
            } else if (index <= 8)
            {
                value = 5;
            }
            DedicationToken tile = new DedicationToken(value, null, index,
                    color, numDots);
            tiles.add(tile);
        }

        // Generate 3 grey cards
        for (index = 0; index < 3; index++)
        {
            DedicationToken tile = new DedicationToken(4, null, index,
                    ColorEnum.WHITE, 0);
            tiles.add(tile);
        }
        int indicator = 0;
        ArrayList<DedicationToken> compare_tiles = board
                .getDedicationTokenCollection();
        for (index = 0; index < compare_tiles.size(); index++)
        {
            DedicationToken DedicationCard = compare_tiles.get(index);
            for (index_dedication = 0; index_dedication < tiles.size(); index_dedication++)
            {
                if (DedicationCard.getCardValue() == tiles
                        .get(index_dedication).getCardValue()
                        && DedicationCard.getNumDots() == tiles.get(
                                index_dedication).getNumDots()
                        && DedicationCard.getColor() == tiles.get(
                                index_dedication).getColor())
                {
                    indicator++;
                    break;
                }
            }
        }
        if (indicator != tiles.size())
        {
            returnvalue = 1;
        }

        return returnvalue;
    }

    /**
     * verified FavorToken card in the saved xml file when it loaded project
     * build 1 check the card collection correct;
     * 
     * @param board
     * @return 0 success, 1 failed
     */
    protected int verifiedFavorToken(GameBoard board)
    {
        int returnvalue = 0;
        if (board.getFavorTokenCollection().size() != 20)
        {
            returnvalue = 1;
        }
        return returnvalue;
    }

    public void attach(BoardObserver observer)
    {
        observers.add(observer);
    }

    public void notifyAllObservers()
    {
        for (BoardObserver observer : observers)
        {
            observer.update(board);
        }
    }

    public void saveGame()
    {
        FileSaver.saveFileDialog(board, "Save game board to file", "board");
    }

    public void newGame()
    {
        builder = new GameBoardBuildedr(board);
        builder.buildAll();
    }

    /**
     * Build players for the game board
     * 
     * @param names
     */
    public void buildPlayers(String[] names)
    {
        builder.buildPlayers(names);
    }

    @Override
    public void update(Map<Integer, Integer> entities)
    {
        board.setMatrixLocationIndex(entities);
    }
}
