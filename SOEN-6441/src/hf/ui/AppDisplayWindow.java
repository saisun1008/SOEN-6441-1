package hf.ui;

import hf.game.controller.GameController;
import hf.game.views.GameView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class AppDisplayWindow
{
    private static MenuBar menuBar;
    private static GameView gView;

    public AppDisplayWindow()
    {
    }

    public static MenuBar getGameMenuBar()
    {
        return menuBar;
    }

    public void setMenuBar(MenuBar menuBar)
    {
        this.menuBar = menuBar;
    }

    /**
     * Create a new menu bar
     */
    private static void createMenuBar()
    {
        menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save");
        saveItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                GameController.getInstance().saveGame();
                gView.getLogView().log("Game is saved to data directory");

            }
        });

        MenuItem loadItem = new MenuItem("Load");
        loadItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                GameController.getInstance().loadGameFile("", "board");
                gView.getLogView().append(
                        "Loaded game is starting with "
                                + GameController.getInstance().getBoard()
                                        .getPlayerCount()
                                + "players: \n"
                                + GameController.getInstance().getBoard()
                                        .getPlayers()
                                + "\n Player "
                                + GameController.getInstance().getBoard()
                                        .getCurrentRoundPlayer().toString());
                gView.gameStarted();
            }
        });

        MenuItem newItem = new MenuItem("New Game");
        newItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {

                GameController.getInstance().newGame();
                int numPlayers = Integer.parseInt(gView.selectPlayerCount());
                GameController.getInstance().getBoard()
                        .setNumPlayer(numPlayers);

                String[] names = gView.enterPlayerNames(numPlayers);
                GameController.getInstance().buildPlayers(names);
                gView.getLogView().append(
                        "new game is starting with "
                                + numPlayers
                                + "players: "
                                + GameController.getInstance().getBoard()
                                        .getPlayers());
                gView.gameStarted();

            }
        });
        menuBar.add(menu);
        menu.add(newItem);
        menu.add(loadItem);
        menu.add(saveItem);
    }

    public void newGameHandler(ActionEvent arg0)
    {

        GameController.getInstance().newGame();
        gView.getLogView().append("new game is starting");
    }

    public static void main(String[] args)
    {
        try
        {
            createMenuBar();

            gView = new GameView(GameController.getInstance().getBoard());
            GameController.getInstance().attach(gView);
            JFrame frame = new JFrame("Havest Festival");
            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    Display.destroy();
                    System.exit(0);
                }
            });
            Menu menu = new Menu();
            menu.add("save");
            frame.getContentPane().add(gView.getGameCanvas(),
                    BorderLayout.CENTER);
            gView.getLogView().setPreferredSize(new Dimension(300, 500));
            frame.getContentPane().add(gView.getLogView(), BorderLayout.EAST);
            frame.setMenuBar(getGameMenuBar());
            frame.pack();
            frame.setVisible(true);
            gView.getGameCanvas().requestFocus();
            gView.getGameCanvas().start();

        } catch (SlickException ex)
        {
            Logger.getLogger(AppDisplayWindow.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}