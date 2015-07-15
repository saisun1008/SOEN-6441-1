package hf.game.controller;

import hf.game.BoardMapper;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.items.LakeTile;
import hf.game.items.LanternCard;
import hf.util.FileSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GameController
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
        for (index = 0; index <= LakeTileCollection.size(); index++)
        {
            LakeTile LakeTileCard = LakeTileCollection.get(index);
            if (LakeTileCard.getIndex() == index)
            {
                LakeTileCollection.remove(index);
            }
        }
        if (!LakeTileCollection.isEmpty())
        {
            returnvalue = 1;
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
        int ORANGE = 8,GREEN = 8,PURPLE = 8,WHITE = 8,BLUE = 8,RED = 8,BLACK = 8;
        ArrayList<LanternCard> LanternCardCollection = board.getLatternCollection();
        for (index = 0; index <= LanternCardCollection.size(); index++)
        {
            LanternCard Lantern = LanternCardCollection.get(index);
            if (Lantern.getColor().name() == "ORANGE")
            {
                ORANGE--;
            }
            else if (Lantern.getColor().name() == "GREEN")
            {
                GREEN--;
            }
            else if (Lantern.getColor().name() == "PURPLE")
            {
                PURPLE--;
            }
            else if (Lantern.getColor().name() == "WHITE")
            {
                WHITE--;
            }else if (Lantern.getColor().name() == "BLUE")
            {
                BLUE--;
            }else if (Lantern.getColor().name() == "RED")
            {
                RED--;
            }else if (Lantern.getColor().name() == "BLACK")
            {
                BLACK--;
            }
        }
        if(ORANGE!=0&&GREEN!=0&&PURPLE!=0&&WHITE!=0&&BLUE!=0&&RED!=0&&BLACK!=0)
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
}
