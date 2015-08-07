package hf.game.views;

import java.util.Map;

import hf.game.GameBoard;
import hf.game.common.GameProperties;
import hf.game.common.PlayerTypeEnum;
import hf.game.common.StrategyEnum;
import hf.game.controller.BoardObserver;
import hf.game.controller.GameController;
import hf.game.controller.MatrixObserver;
import hf.game.controller.ViewEventObserver;
import hf.game.strategy.FriendlyStrategy;
import hf.game.strategy.GreedyStrategy;
import hf.game.strategy.RandomStrategy;
import hf.game.strategy.UnfriendlyStrategy;
import hf.ui.GameCanvas;
import hf.ui.matrix.Matrix;
import hf.ui.matrix.MatrixCell;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

public class GameView extends JPanel implements BoardObserver,
        ViewEventObserver
{
    private LogView logView;
    private GameBoard gameBoard;
    private CanvasGameContainer appgc;
    private GameCanvas mainView;
    private SelectionView selectionView;

    /**
     * initialization
     * 
     * @param board
     * @throws SlickException
     */
    public GameView(GameBoard board) throws SlickException
    {
        logView = new LogView();

        gameBoard = board;

        selectionView = new SelectionView(this, board);

        buildCanvas();
    }

    public LogView getLogView()
    {
        return logView;
    }

    public SelectionView getSelectionView()
    {
        return selectionView;
    }

    public GameCanvas getMainCanvas()
    {
        return mainView;
    }

    /**
     * build canvas
     * 
     * @throws SlickException
     */
    private void buildCanvas() throws SlickException
    {
        mainView = new GameCanvas("Harvest Festival");
        appgc = new CanvasGameContainer(mainView);
        mainView.setGameBoard(gameBoard);
        mainView.attach(logView);
        appgc.setBounds(0, 0, GameProperties.GAME_WINDOW_WIDTH,
                GameProperties.GAME_WINDOW_HEIGHT);
    }

    public Matrix getMatrix()
    {
        return mainView.getMatrixView();
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
        StrategyEnum[] strats = new StrategyEnum[num];
        Object[] possibilities =
        { "Greedy", "Friendly", "UnFriendly", "Random", "Human" };
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

            String s = (String) JOptionPane.showInputDialog(this,
                    "Please select player Strategy:\n", "Player Strategy for "
                            + ret[index], JOptionPane.PLAIN_MESSAGE, null,
                    possibilities, "Human");

            if ((s != null) && (s.length() > 0))
            {
                if (s.equals("Greedy"))
                {
                    strats[index] = StrategyEnum.GREEDY;
                } else if (s.equals("Friendly"))
                {
                    strats[index] = StrategyEnum.FRIENDLY;
                } else if (s.equals("UnFriendly"))
                {
                    strats[index] = StrategyEnum.UNFRIENDLY;
                } else if (s.equals("Random"))
                {
                    strats[index] = StrategyEnum.RANDOM;
                } else if (s.equals("Human"))
                {
                    strats[index] = StrategyEnum.NORMAL;
                }
            }

            logView.log("Player " + ret[index] + " is created with strategy: "
                    + strats[index]);
        }

        GameController.getInstance().buildPlayers(ret, strats);
        return ret;
    }

    public void reSelectPlayerStrategy()
    {
        Object[] possibilities =
        { "Greedy", "Friendly", "UnFriendly", "Random", "Human" };
        for (int index = 0; index < GameController.getInstance().getBoard()
                .getPlayerCount(); index++)
        {
            String s = (String) JOptionPane.showInputDialog(this,
                    "Please select player Strategy:\n", "Player Strategy for "
                            + GameController.getInstance().getBoard()
                                    .getPlayers().get(index).getName(),
                    JOptionPane.PLAIN_MESSAGE, null, possibilities, "Human");

            if ((s != null) && (s.length() > 0))
            {
                if (s.equals("Greedy"))
                {
                    GameController
                            .getInstance()
                            .getBoard()
                            .getPlayers()
                            .get(index)
                            .setStrategy(
                                    new GreedyStrategy(GameController
                                            .getInstance().getBoard()
                                            .getPlayers().get(index)));
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setPlayerType(PlayerTypeEnum.AI);
                } else if (s.equals("Friendly"))
                {
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setStrategy(new FriendlyStrategy());
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setPlayerType(PlayerTypeEnum.AI);
                } else if (s.equals("UnFriendly"))
                {
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setStrategy(new UnfriendlyStrategy());
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setPlayerType(PlayerTypeEnum.AI);
                } else if (s.equals("Random"))
                {
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setStrategy(new RandomStrategy());
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setPlayerType(PlayerTypeEnum.AI);
                } else if (s.equals("Human"))
                {
                    GameController.getInstance().getBoard().getPlayers()
                            .get(index).setPlayerType(PlayerTypeEnum.HUMAN);
                }
            }
            logView.log("Player "
                    + GameController.getInstance().getBoard().getPlayers()
                            .get(index).getName()
                    + " is created with strategy: " + s);
        }
    }

    public void gameStarted()
    {
        mainView.gameStarted();
    }

    @Override
    public void update(GameBoard board)
    {
        gameBoard = board;
        mainView.setGameBoard(board);
    }

    @Override
    public void updateEvent(String event)
    {
        selectionView.showPanel(true);
        selectionView.buildByType(event);
    }
}
