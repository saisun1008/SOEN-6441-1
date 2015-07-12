package hf.game.views;

import hf.game.GameBoard;
import hf.ui.GameCanvas;
import hf.ui.LogView;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

public class GameView extends JPanel
{
    private LogView logView;
    private GameBoard gameBoard;
    private CanvasGameContainer appgc;

    public GameView(GameBoard board) throws SlickException
    {
        logView = new LogView();
        gameBoard = board;
        buildCanvas();
    }

    public LogView getLogView()
    {
        return logView;
    }

    private void buildCanvas() throws SlickException
    {
        GameCanvas mainView = new GameCanvas("Harvest Festival");
        appgc = new CanvasGameContainer(mainView);
        appgc.setBounds(0, 0, 600, 500);
    }

    public CanvasGameContainer getGameCanvas()
    {
        return appgc;
    }

    /**
     * Generate a pop up to allow user to select how many players are in the
     * game
     * 
     * @return number of players in string format
     */
    public String selectPlayerCount()
    {
        Object[] possibilities =
        { "2", "3", "4" };
        String s = (String) JOptionPane.showInputDialog(this, "",
                "Number of Players", JOptionPane.PLAIN_MESSAGE, null,
                possibilities, "4");

        // If a string was returned, say so.
        if ((s != null) && (s.length() > 0))
        {
            return s;
        }
        return "4";
    }

    /**
     * Pop up allow user to enter player names
     * 
     * @param num
     *            number of players
     * @return String array contains user names
     */
    public String[] enterPlayerNames(int num)
    {
        String[] ret = new String[num];
        for (int index = 0; index < num; index++)
        {
            boolean goodname = false;
            while (!goodname)
            {
                String s = (String) JOptionPane.showInputDialog(this, "",
                        "Please enter name for player: " + (index + 1),
                        JOptionPane.PLAIN_MESSAGE, null, null, "4");
                if ((s == null) || (s.length() <= 0))
                {
                    s = "joe" + (index + 1);
                }
                if (index == 0 || !ret[index - 1].equals(s))
                {
                    goodname = true;
                } else
                {
                    logView.log("Name already exists, please use a different one!");
                }
                ret[index] = s;
            }
        }
        return ret;
    }
}
