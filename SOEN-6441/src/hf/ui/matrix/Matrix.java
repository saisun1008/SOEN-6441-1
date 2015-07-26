package hf.ui.matrix;

import java.util.ArrayList;
import java.util.Map;

import hf.controller.MatrixCalculator;
import hf.game.GameBoard;
import hf.game.controller.MatrixObserver;
import hf.game.controller.ViewLogObserver;
import hf.game.items.LakeTile;
import hf.util.MouseEventValidation;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

/**
 * This is play zone Matrix UI which is used to show 21*21 Matrix cells.
 * 
 * @author caogc
 * @since 2015-07-11
 * 
 */
public class Matrix extends BasicGame implements MatrixObserver
{

    private MatrixCalculator ca = new MatrixCalculator();
    private ArrayList<ViewLogObserver> observers;
    private ArrayList<MatrixObserver> matrixObservers;
    private boolean isMouseLeftClick = false;
    private boolean isMouseRightClick = false;
    private GameBoard gameBoard;
    
    /**
     * Matrix constructor
     * 
     * @param matrix
     *            name
     */
    public Matrix(String name)
    {
        super(name);
        observers = new ArrayList<ViewLogObserver>();
        matrixObservers = new ArrayList<MatrixObserver>();
    }

    public void setMouseLeftClick(boolean isMouseLeftClick)
    {
        this.isMouseLeftClick = isMouseLeftClick;
    }

    public void setMouseRightClick(boolean isMouseRightClick)
    {
        this.isMouseRightClick = isMouseRightClick;
    }
    
    public void setEntities(Map<Integer, MatrixCell> entities)
    {
        for (int key : entities.keySet())
        {
            entities.get(key).setCa(ca);
        }
        ca.setEntities(entities);
    }

    public Map<Integer, MatrixCell> getEntities()
    {
        return ca.getEntities();
    }

    /**
     * Initialization of Matrix, is used to initial all matrix cells objects.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException
    {
    }
    
    public void loadMatrix(GameBoard gameBoard)
    {
        this.gameBoard = gameBoard;
        ca.init(gameBoard);
    }

    /**
     * Process mouse left/right click event.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    @Override
    public void update(GameContainer gc, int i) throws SlickException
    {
        if (!isMouseLeftClick && !isMouseRightClick)
            return;

        for (BasicGame entity : ca.getEntities().values())
        {
            entity.update(gc, i);
        }

//        for (LakeTile lake : ca.getLakeTiles().values())
        if(gameBoard.getCurrentRoundPlayer()!=null)
            for (Integer lakeIndex : gameBoard.getCurrentRoundPlayer().getLakeTileList())
            {
                Input input = gc.getInput();
                int xpos = input.getMouseX();
                int ypos = input.getMouseY();
    
                LakeTile lake = gameBoard.getLakeTileByIndex(lakeIndex);
                if ((xpos > lake.getX() && xpos < lake.getX() + lake.getSize())
                        && (ypos > lake.getY() && ypos < lake.getY()
                                + lake.getSize()))
                {
//                    if (!lake.isFaceUp())
//                    {
//                        notifyAllObservers("invalid select");
//                        return;
//                    }
    
                    if (isMouseLeftClick)
                    {
                        ca.setSelectedCard(lakeIndex);
                        notifyAllObservers("lake card " + lake.getIndex()
                                + " has been selected");
                    } else if (isMouseRightClick)
                    {
                        lake.rotateTile();
                        gameBoard.setLakeTileByIndex(lakeIndex, lake);
                        notifyAllObservers("lake card " + lake.getIndex()
                                + " been rotated 90");
                    }
                }
            }
    }

    /**
     * Render Matrix UI.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        for (BasicGame entity : ca.getEntities().values())
            entity.render(gc, g);

//        for (LakeTile lake : ca.getLakeTiles().values())
//        {
//            if (lake.getIndex() == 1)// TODO get owner is Matrix
//                continue;
//
//            if (lake.getRotateDegrees() == 0)
//            {
//                Image img = new Image(lake.getImage());
//                img.draw(lake.getX(), lake.getY(), lake.getSize(),
//                        lake.getSize());
//            } else
//            {
//                Image img = new Image(lake.getImage());
//                img.setRotation(lake.getRotateDegrees());
//                img.draw(lake.getX(), lake.getY());
//            }
//        }
    }

    public void attach(ViewLogObserver observer)
    {
        observers.add(observer);
    }

    public void notifyAllObservers(String msg)
    {
        for (ViewLogObserver observer : observers)
        {
            observer.update(msg);
        }
    }

    public void attach(MatrixObserver observer)
    {
        matrixObservers.add(observer);
        ca.attach(observer);
    }

    public void notifyAllObservers()
    {
        for (MatrixObserver observer : matrixObservers)
        {
            observer.update(ca.getEntities());
        }
    }

    @Override
    public void update(Map<Integer, MatrixCell> entities)
    {
        notifyAllObservers();
    }

    public void setGameBoard(GameBoard board)
    {
        gameBoard = board;
    }
    /*
     * public static void main(String [] arguments) { try { AppGameContainer app
     * = new AppGameContainer(new Matrix("aaaa")); app.setDisplayMode(1300,
     * 1000, false); Display.setInitialBackground(1, 1, 1); app.start(); } catch
     * (SlickException e) { e.printStackTrace(); } }
     */
}