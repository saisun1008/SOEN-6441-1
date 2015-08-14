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
import hf.game.strategy.GameEndingStrategy;
import hf.game.strategy.GreedyStrategy;
import hf.game.strategy.NHonorPointsEndingStrategy;
import hf.game.strategy.NLakeTileEndingStrategy;
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

        gameBoard = GameController.getInstance().getBoard();

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

    public void selectGameEndingStrategy(int playernumber)
    {
        Object[] possibilities =
        { "Normal Ending", "N Lake Tile Ending", "N Honor points Ending" };
        String s = (String) JOptionPane
                .showInputDialog(this, "Please select Game Ending Strategy:\n",
                        "Game Ending Strategy for current game",
                        JOptionPane.PLAIN_MESSAGE, null, possibilities,
                        "Normal Ending");

        if ((s != null) && (s.length() > 0))
        {
            if (s.equals("Normal Ending"))
            {
                logView.log("Normal Ending Strategy has been selected");
                // don't have to do anything
            } else if (s.equals("N Lake Tile Ending"))
            {
                int NValue = 0;
                switch(playernumber)
                {
                case 2:
                    Object[] Nlaketile_2 =
                    { "2", "3", "4" , "5", "6", "7", "8", "9", "10", "11"};
                    String snlaketile_2 = (String) JOptionPane
                        .showInputDialog(this, "Please select N value:\n",
                                "N value for Nlaketile Strategy",
                                JOptionPane.PLAIN_MESSAGE, null, Nlaketile_2,
                                "2");
                    NValue = Integer.parseInt(snlaketile_2);
                    break;
                case 3:
                    Object[] Nlaketile_3 =
                    { "2", "3", "4" , "5", "6", "7", "8", "9"};
                    String snlaketile_3 = (String) JOptionPane
                        .showInputDialog(this, "Please select N value:\n",
                            "N value for Nlaketile Strategy",
                            JOptionPane.PLAIN_MESSAGE, null, Nlaketile_3,
                            "2");
                    NValue = Integer.parseInt(snlaketile_3);
                    break;
                case 4:
                    Object[] Nlaketile_4 =
                    { "2", "3", "4" , "5", "6", "7", "8"};
                    String snlaketile_4 = (String) JOptionPane
                    .showInputDialog(this, "Please select N value:\n",
                            "N value for Nlaketile Strategy",
                            JOptionPane.PLAIN_MESSAGE, null, Nlaketile_4,
                            "2");
                    NValue = Integer.parseInt(snlaketile_4);
                    break;
                 }              
/*                String N = (String) JOptionPane.showInputDialog(this, "",
                        "Please enter N value", JOptionPane.PLAIN_MESSAGE,
                        null, null, "0");
                if ((N == null) || (N.length() <= 0))
                {
                    NValue = Integer.parseInt(N);
                } else
                {
                    NValue = 3;
                }
*/
                GameEndingStrategy stra = new NLakeTileEndingStrategy(NValue);
                GameController.getInstance().getBoard()
                        .setGameEndingStrategy(stra);

                logView.log("N Lake Tile Ending Strategy has been selected, N value is set to "
                        + NValue);
            } else if (s.equals("N Honor points Ending"))
            {
                boolean correctNum = false;
                while(!correctNum){
                    int NValue = 0;
                    String N = (String) JOptionPane.showInputDialog(this, "",
                        "Please enter N value", JOptionPane.PLAIN_MESSAGE,
                        null, null, "4");
                    NValue = Integer.parseInt(N);
                    switch(playernumber)
                    {
                    case 2:
                        if ((NValue >= 4) && NValue <= 72)
                        {
                            correctNum = true;
                            GameEndingStrategy stra = new NHonorPointsEndingStrategy(NValue);
                            GameController.getInstance().getBoard()
                                    .setGameEndingStrategy(stra);

                            logView.log("N Honor points Ending Strategy has been selected, N value is set to "
                                    + NValue);
                        } else
                        {
                            logView.log("Number are not correct, please make sure the number are correct!");
                        }
                        break;
                    case 3:
                        if ((NValue >= 4) && NValue <= 57)
                        {
                            correctNum = true;
                            GameEndingStrategy stra = new NHonorPointsEndingStrategy(NValue);
                            GameController.getInstance().getBoard()
                                    .setGameEndingStrategy(stra);

                            logView.log("N Honor points Ending Strategy has been selected, N value is set to "
                                    + NValue);
                        } else
                        {
                            logView.log("Number are not correct, please make sure the number are correct!");
                        }
                        break;
                    case 4:
                        if ((NValue >= 4) && NValue <= 48)
                        {
                            correctNum = true;
                            GameEndingStrategy stra = new NHonorPointsEndingStrategy(NValue);
                            GameController.getInstance().getBoard()
                                    .setGameEndingStrategy(stra);

                            logView.log("N Honor points Ending Strategy has been selected, N value is set to "
                                    + NValue);
                        } else
                        {
                            logView.log("Number are not correct, please make sure the number are correct!");
                        }
                        break;
                    }     
/*
                if ((N == null) || (N.length() <= 0))
                {
                    NValue = Integer.parseInt(N);
                } else
                {
                    logView.log("Number are not correct, please make sure the number are correct!");
                }
*/
                                    
                }
            }
        }
    }

    public boolean EnableDisaster()
    {
        int n = JOptionPane.showConfirmDialog(this,
                "Would you like to enable disasters?", "Disaster Control",
                JOptionPane.YES_NO_OPTION);

        if (n == 0)
        {
            logView.log("Disasters have been enabled, have FUN!");
        } else
        {
            logView.log("Disasters have been disabled...");
        }
        return n == 0 ? true : false;
    }

    public void gameStarted()
    {
        mainView.gameStarted();
    }

    @Override
    public void update(GameBoard board)
    {
        gameBoard = GameController.getInstance().getBoard();
        mainView.setGameBoard(board);
    }

    @Override
    public void updateEvent(String event)
    {
        selectionView.showPanel(true);
        selectionView.buildByType(event);
    }
}
