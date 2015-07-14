package hf.util;

import hf.game.GameBoard;
import hf.game.items.LakeTile;
import hf.game.items.Player;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A UI utility class which prompts the user for saving game board
 * 
 * @see GameBoard
 * 
 */
public final class FileSaver
{

    /**
     * Private constructor - don't permit external instantiation
     */
    private FileSaver()
    {
    }

    /**
     * A generic method which creates a dialog asking the user to save the
     * current game
     * 
     * @param object
     *            The object to be made persistent
     * @param description
     *            A description of the object
     * @param extension
     *            the file extension related to this object
     * @return True if the operation was successful, false otherwise
     */
    public static <T> String saveFileDialog(T object, String description,
            String extension)
    {
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")
                + "/data");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                description, extension);
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                new XmlMapper<T>().save(object,
                        new FileOutputStream(chooser.getSelectedFile()));
                return chooser.getSelectedFile().getAbsolutePath();
            } catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Could not save file "
                        + chooser.getSelectedFile().getAbsolutePath());
            }
        }
        return null;
    }

    /**
     * The "Player" save to file dialog
     * 
     * @param player
     *            A reference to a Player object
     * @return True if the operation was successful, false otherwise
     */
    public static String savePlayer(Player player)
    {
        return saveFileDialog(player, "Player Files", "player");
    }

    public static String saveLakeTileCollection(ArrayList<LakeTile> list)
    {
        return saveFileDialog(list, "Player Files", "laketile");
    }
}
