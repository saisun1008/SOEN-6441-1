package hf.ui;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.common.GameProperties;
import hf.game.controller.BoardObserver;
import hf.game.controller.ViewEventObserver;
import hf.game.items.Player;
import hf.game.views.GameView;
import hf.ui.matrix.Matrix;

import java.awt.Container;
import java.awt.MenuBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Main game canvas, should perform all the UI rendering the updates
 * 
 * @author Sai
 *
 */
public class GameCanvas extends BasicGame implements ViewEventObserver
{

    private boolean gameStarted = false;
    private Image startImage;
    private Image foldedLakeTileImage;
    private GameBoard gameBoard;
    private boolean rerender = false;
    private ArrayList<ViewEventObserver> observers;
    private static Matrix matrixView;
    private boolean redrawn = false;

    public GameCanvas(String title)
    {
        super(title);
        gameStarted = false;
        matrixView = new Matrix("battle board");
        matrixView.attach(this);
        observers = new ArrayList<ViewEventObserver>();
    }

    public void reDrawMatrix()
    {
        matrixView = new Matrix("battle board");
        matrixView.attach(this);
        redrawn = true;
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        startImage = new Image("images/Lanterns-cover.jpg");
        foldedLakeTileImage = new Image("images/tiles/foldedTile.jpg");
        gc.setShowFPS(false);
        matrixView.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException
    {
        if (redrawn)
        {
            matrixView.init(gc);
            redrawn = false;
            if (matrixView.getEntities() == null)
            {
                matrixView.setEntities(gameBoard.getEntities());
            }
        }
        if (rerender)
        {
            render(gc, gc.getGraphics());
            rerender = false;
        }
        Input input = gc.getInput();
        if (input.isMousePressed(input.MOUSE_LEFT_BUTTON))
        {
            if (input.getMouseX() >= GameProperties.GAME_WINDOW_WIDTH / 2 - 200
                    && input.getMouseX() <= GameProperties.GAME_WINDOW_WIDTH / 2 - 140
                    && input.getMouseY() >= 10 && input.getMouseY() <= 70)
            {
                gameBoard.makeNewRound();
            }
        }
        matrixView.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        if (!gameStarted)
        {
            g.drawImage(startImage, 0, 0);
        } else
        {
            renderDeckArea(g);
            renderPlayers(g);
            renderTokens(g);
            Image newRound = new Image("images/newRound.jpg");
            g.drawImage(newRound, GameProperties.GAME_WINDOW_WIDTH / 2 - 200,
                    10);
            matrixView.render(gc, g);
        }

    }

    private void renderTokens(Graphics g)
    {

    }

    private void renderPlayers(Graphics g) throws SlickException
    {
        int x = 0;
        int y = 0;
        for (Player player : gameBoard.getPlayers())
        {
            if (player.getSitLocation() == LocationEnum.TOP)
            {
                g.drawString(player.getName(),
                        GameProperties.GAME_WINDOW_WIDTH / 2, 10);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            GameProperties.GAME_WINDOW_WIDTH / 2 + 60, 10);
                }
                x = GameProperties.GAME_WINDOW_WIDTH / 2;
                y = 20;
            } else if (player.getSitLocation() == LocationEnum.LEFT)
            {
                g.drawString(player.getName(),
                        10 + GameProperties.SIDE_PANELMARGIN,
                        GameProperties.GAME_WINDOW_HEIGHT / 2);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            10 + GameProperties.SIDE_PANELMARGIN,
                            GameProperties.GAME_WINDOW_HEIGHT / 2 + 20);
                }
                x = 10 + GameProperties.SIDE_PANELMARGIN;
                y = GameProperties.GAME_WINDOW_HEIGHT / 2 + 30;
            } else if (player.getSitLocation() == LocationEnum.BOTTOM)
            {
                g.drawString(player.getName(),
                        GameProperties.GAME_WINDOW_WIDTH / 2,
                        GameProperties.GAME_WINDOW_HEIGHT - 150);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            GameProperties.GAME_WINDOW_WIDTH / 2 + 50,
                            GameProperties.GAME_WINDOW_HEIGHT - 150);
                }
                x = GameProperties.GAME_WINDOW_WIDTH / 2;
                y = GameProperties.GAME_WINDOW_HEIGHT - 130;
            } else
            {
                g.drawString(player.getName(), GameProperties.GAME_WINDOW_WIDTH
                        - GameProperties.SIDE_PANELMARGIN,
                        GameProperties.GAME_WINDOW_HEIGHT / 2);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)", GameProperties.GAME_WINDOW_WIDTH
                            - GameProperties.SIDE_PANELMARGIN - 30,
                            GameProperties.GAME_WINDOW_HEIGHT / 2 + 20);
                }
                x = GameProperties.GAME_WINDOW_WIDTH
                        - GameProperties.SIDE_PANELMARGIN;
                y = GameProperties.GAME_WINDOW_HEIGHT / 2 + 30;
            }
            renderPlayerHandCards(g, player, x - 100, y - 10);
        }
    }

    private void renderPlayerHandCards(Graphics g, Player p, int x, int y)
            throws SlickException
    {
        int xCount = 0;
        int yCount = 0;
        // draw lake tiles
        for (int index : p.getLakeTileList())
        {
            g.drawImage(new Image(gameBoard.getLakeTileByIndex(index)
                    .getImage()), x + xCount * 50, y + 40 + yCount * 50);

            xCount++;
        }
        g.drawString(Integer.toString(p.getLakeTileList().size()), x + xCount
                * 50, y + 40 + yCount * 50 + 50);

        // draw lantern card
        if (p.getSitLocation() == LocationEnum.LEFT
                || p.getSitLocation() == LocationEnum.RIGHT)
        {
            yCount++;
            xCount = 0;
        }
        HashMap<ColorEnum, ArrayList<Integer>> lanterns = p.getLanternList();
        if (lanterns.size() == 0)
        {
            g.drawString("0 Lantern", x + xCount * 50 + 30, y + 40 + yCount
                    * 50 + 50);
            xCount = xCount + 3;
        }
        for (ColorEnum color : lanterns.keySet())
        {
            g.drawImage(new Image(color.getImagePath()), x + xCount * 50, y
                    + 40 + yCount * 50);
            g.drawString(Integer.toString(lanterns.get(color).size()), x
                    + xCount * 50, y + 40 + yCount * 50 + 50);
            xCount++;
        }

        // draw dedication tokens
        if (p.getSitLocation() == LocationEnum.LEFT
                || p.getSitLocation() == LocationEnum.RIGHT)
        {
            yCount++;
            xCount = 0;
        }
        HashMap<ColorEnum, ArrayList<Integer>> dedications = p
                .getDedicationTokenList();
        if (dedications.size() == 0)
        {
            g.drawString("0 dedication", x + xCount * 50, y + 40 + yCount * 50
                    + 50);
            xCount = xCount + 3;
        }
        for (ColorEnum color : dedications.keySet())
        {
            /*
             * g.drawImage(new Image(color.getImagePath()), x + xCount * 50, y +
             * 40 + yCount * 50);
             */
            String msg = color + ": ";
            for (int index : dedications.get(color))
            {
                msg = msg
                        + gameBoard.getDedicationTokenByIndex(index)
                                .getCardValue()
                        + (index < dedications.get(color).size() - 1 ? ", "
                                : ".");
            }
            g.drawString(msg, x + xCount * 50, y + 40 + yCount * 50 + 50);
            xCount++;
        }

        // draw favor token
        if (p.getSitLocation() == LocationEnum.LEFT
                || p.getSitLocation() == LocationEnum.RIGHT)
        {
            yCount = 4;
            xCount = 0;
        }
        g.drawImage(new Image(gameBoard.getFavorTokenByIndex(0).getImage()), x
                + xCount * 50, y + 40 + yCount * 50);
        g.drawString(Integer.toString(p.getFavorTokenList().size()), x + xCount
                * 50, y + 40 + yCount * 50 + 50);
    }

    /**
     * Render decks of cards area
     * 
     * @param g
     * @throws SlickException
     */
    private void renderDeckArea(Graphics g) throws SlickException
    {
        // render folded laketiles
        int imageCount = 0;
        g.drawImage(foldedLakeTileImage, 10, 10);
        g.drawString(Integer.toString(gameBoard.getLakeTileDeck().size()),
                GameProperties.DECK_WIDTH + 10, 10);
        imageCount++;
        // render lanttern decks
        for (ColorEnum color : gameBoard.getLatternDecks().keySet())
        {
            Image im = new Image(color.getImagePath());
            g.drawImage(im, 10, imageCount * GameProperties.DECK_HEIGHT + 10);
            g.drawString(
                    Integer.toString(gameBoard.getLatternDecks().get(color)
                            .size()), GameProperties.DECK_WIDTH + 10,
                    imageCount * GameProperties.DECK_HEIGHT + 10);
            imageCount++;
        }
        // render dedication token
        for (ColorEnum color : gameBoard.getDedicationTokenDeck().keySet())
        {
            ArrayList<Integer> list = gameBoard.getDedicationTokenDeck().get(
                    color);
            Collections.sort(list);
            Image im = new Image(gameBoard.getDedicationTokenByIndex(
                    list.get(0)).getImage());
            g.drawImage(im, 10, imageCount * GameProperties.DECK_HEIGHT + 10);
            g.drawString(Integer.toString(list.size()),
                    GameProperties.DECK_WIDTH + 10, imageCount
                            * GameProperties.DECK_HEIGHT + 10);
            imageCount++;
        }

        // render favor token
        Image im = new Image(gameBoard.getFavorTokenByIndex(0).getImage());
        g.drawImage(im, 10, imageCount * GameProperties.DECK_HEIGHT + 10);
        g.drawString(
                Integer.toString(gameBoard.getFavorTokenCollection().size()),
                GameProperties.DECK_WIDTH + 10, imageCount
                        * GameProperties.DECK_HEIGHT + 10);
    }

    public void gameStarted()
    {
        gameStarted = true;
        rerender = true;
    }

    public void setGameBoard(GameBoard board)
    {
        gameBoard = board;
        setEntities();
    }

    public void setEntities()
    {
        matrixView.setEntities(gameBoard.getEntities());
    }

    public Matrix getMatrixView()
    {
        return matrixView;
    }

    public void attach(ViewEventObserver observer)
    {
        observers.add(observer);
    }

    public void notifyAllObservers(String msg)
    {
        for (ViewEventObserver observer : observers)
        {
            observer.update(msg);
        }
    }

    @Override
    public void update(String msg)
    {
        notifyAllObservers(msg);
    }

}
