package hf.ui;

import hf.game.GameBoard;
import hf.game.common.GameProperties;
import hf.game.common.PlayerTypeEnum;
import hf.game.controller.AIControllerThread;
import hf.game.controller.DisasterThread;
import hf.game.controller.GameController;
import hf.game.items.LakeTile;
import hf.game.items.Player;
import hf.game.views.GameView;
import hf.ui.matrix.Matrix;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;

/**
 * display window
 * 
 * @author Sai
 *
 */
public class AppDisplayWindow
{
    private static MenuBar menuBar;
    private static GameView gView;
    private DisasterThread disaster = null;
    private AIControllerThread aiController = null;

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
    private void createMenuBar()
    {
        menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save");
        saveItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                GameController.getInstance().getBoard()
                        .setEntities(gView.getMatrix().getEntities());
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
                gView.selectGameEndingStrategy(GameController.getInstance().getBoard()
                        .getPlayerCount());
                gView.reSelectPlayerStrategy();
                if (gView.EnableDisaster())
                {
                    startDisasterThread();
                } else
                {
                    if (disaster != null)
                    {
                        disaster.stopDisaster();
                    }
                }
                /*
                 * gView.getMatrix().setEntities(
                 * GameController.getInstance().getBoard().getEntities());
                 */
                gView.getMatrix().loadMatrix(
                        GameController.getInstance().getBoard());
                gView.getMatrix().attach(GameController.getInstance());
                LakeTile target = null;
                for (LakeTile index : GameController.getInstance().getBoard()
                        .getLakeTileCollection())
                {
                    if (index.isStartingCard())
                    {
                        target = index;
                    }
                }
                gView.getSelectionView().setBoard(
                        GameController.getInstance().getBoard());
                gView.getLogView().log(target.toString());
                startAIControllerThread();

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
                gView.selectGameEndingStrategy(numPlayers);

                String[] names = gView.enterPlayerNames(numPlayers);
                if (gView.EnableDisaster())
                {
                    startDisasterThread();
                } else
                {
                    if (disaster != null)
                    {
                        disaster.stopDisaster();
                    }
                }
                gView.getLogView().append(
                        "new game is starting with "
                                + numPlayers
                                + "players: "
                                + GameController.getInstance().getBoard()
                                        .getPlayers());
                gView.getMainCanvas().reDrawMatrix();
                gView.getMatrix().attach(GameController.getInstance());
                gView.getMatrix().loadMatrix(
                        GameController.getInstance().getBoard());
                gView.gameStarted();
                gView.getSelectionView().setBoard(
                        GameController.getInstance().getBoard());
                startAIControllerThread();

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

    private void startDisasterThread()
    {
        if (disaster == null)
        {
            disaster = new DisasterThread(gView.getLogView());
            disaster.start();
            Thread thread = new Thread(disaster);
            thread.start();
        } else
        {
            disaster.restart();
        }
    }

    private void startAIControllerThread()
    {
        boolean ai = false;
        for (Player p : GameController.getInstance().getBoard().getPlayers())
        {
            if (p.getPlayerType() == PlayerTypeEnum.AI)
            {
                ai = true;
                break;
            }
        }
        if (ai)
        {
            if (aiController == null)
            {
                aiController = new AIControllerThread(gView.getLogView());
                Thread thread = new Thread(aiController);
                thread.start();
            } else
            {
                aiController.restart();
            }
        }

    }

    public static void main(String[] args)
    {
        try
        {
            new AppDisplayWindow().createMenuBar();

            gView = new GameView(GameController.getInstance().getBoard());
            gView.getMatrix().attach(GameController.getInstance());
            GameController.getInstance().attach(gView);
            gView.getMainCanvas().attach(gView);
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
            gView.getLogView().setPreferredSize(
                    new Dimension(300, GameProperties.GAME_WINDOW_HEIGHT));
            frame.getContentPane().add(gView.getLogView(), BorderLayout.EAST);
            frame.getContentPane().add(gView.getSelectionView(),
                    BorderLayout.WEST);
            gView.getSelectionView().setPreferredSize(new Dimension(200, 500));
            gView.getSelectionView().setVisible(false);
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