package hf.game.controller;

import hf.game.BoardMapper;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.LocationEnum;
import hf.game.items.Player;
import hf.game.views.LogView;
import hf.util.FileSaver;

import java.awt.Container;
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
    
    protected int verification(GameBoard board)
    {
        int playernum = board.getPlayerCount();
        
        return 1;
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
