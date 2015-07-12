package hf.game.controller;

import hf.game.BoardMapper;
import hf.game.GameBoard;
import hf.game.GameBoardBuildedr;
import hf.game.common.Direction;
import hf.game.items.Player;
import hf.ui.LogView;
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

    private GameBoard board;
    private GameBoardBuildedr builder;

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
    }

    public void saveGame()
    {
        FileSaver.saveFileDialog(board, "Save game board to file", "board");
    }

    public void newGame()
    {
        board = new GameBoard();
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
