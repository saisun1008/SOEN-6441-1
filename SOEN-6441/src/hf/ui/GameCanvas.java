package hf.ui;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.Direction;
import hf.game.common.GameProperties;
import hf.game.items.Player;

import java.awt.MenuBar;

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
public class GameCanvas extends BasicGame
{

    private boolean gameStarted = false;
    private Image startImage;
    private Image foldedLakeTileImage;
    private GameBoard gameBoard;
    private boolean rerender = false;

    public GameCanvas(String title)
    {
        super(title);
        gameStarted = false;
    }

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        startImage = new Image("images/Lanterns-cover.jpg");
        foldedLakeTileImage = new Image("images/tiles/foldedTile.jpg");
        gc.setShowFPS(false);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException
    {
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
        }
    }

    private void renderTokens(Graphics g)
    {

    }

    private void renderPlayers(Graphics g)
    {
        for (Player player : gameBoard.getPlayers())
        {
            if (player.getSitLocation() == Direction.TOP)
            {
                g.drawString(player.getName(),
                        GameProperties.GAME_WINDOW_WIDTH / 2, 10);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            GameProperties.GAME_WINDOW_WIDTH / 2 + 60, 10);
                }
            } else if (player.getSitLocation() == Direction.LEFT)
            {
                g.drawString(player.getName(),
                        10 + GameProperties.SIDE_PANELMARGIN,
                        GameProperties.GAME_WINDOW_HEIGHT / 2);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            60 + GameProperties.SIDE_PANELMARGIN,
                            GameProperties.GAME_WINDOW_HEIGHT / 2);
                }
            } else if (player.getSitLocation() == Direction.BOTTOM)
            {
                g.drawString(player.getName(),
                        GameProperties.GAME_WINDOW_WIDTH / 2,
                        GameProperties.GAME_WINDOW_HEIGHT - 50);
                if (gameBoard.getCurrentRoundPlayer().getName()
                        .equals(player.getName()))
                {
                    g.drawString("(Playing)",
                            GameProperties.GAME_WINDOW_WIDTH / 2 + 50,
                            GameProperties.GAME_WINDOW_HEIGHT - 50);
                }
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
            }
        }
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
    }

    public void gameStarted()
    {
        gameStarted = true;
        rerender = true;
    }

    public void setGameBoard(GameBoard board)
    {
        gameBoard = board;
    }

}
