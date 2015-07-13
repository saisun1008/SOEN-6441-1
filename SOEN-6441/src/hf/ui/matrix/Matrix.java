package hf.ui.matrix;

import java.util.ArrayList;

import hf.controller.MatrixCalculator;
import hf.game.controller.ViewEventObserver;
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
public class Matrix extends BasicGame
{

    private MatrixCalculator ca = new MatrixCalculator();
    private ArrayList<ViewEventObserver> observers;

    /**
     * Matrix constructor
     * 
     * @param matrix
     *            name
     */
    public Matrix(String name)
    {
        super(name);
        observers = new ArrayList<ViewEventObserver>();
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
        ca.init();
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

        boolean mouseLeftClick = MouseEventValidation.isMouseLeftClick(gc);
        boolean mouseRightClick = MouseEventValidation.isMouseRightClick(gc);
        if (!mouseLeftClick && !mouseRightClick)
            return;

        for (BasicGame entity : ca.getEntities().values())
        {
            entity.update(gc, i);
        }

        for (LakeTile lake : ca.getLakeTiles().values())
        {
            Input input = gc.getInput();
            int xpos = input.getMouseX();
            int ypos = input.getMouseY();

            if ((xpos > lake.getX() && xpos < lake.getX() + lake.getSize())
                    && (ypos > lake.getY() && ypos < lake.getY()
                            + lake.getSize()))
            {
                if (!lake.isFaceUp())
                {
                    notifyAllObservers("invalid select");
                    return;
                }

                if (mouseLeftClick)
                {
                    ca.setSelectedCard(lake);
                    notifyAllObservers("lake card " + lake.getIndex()
                            + " has been selected");
                } else if (mouseRightClick)
                {
                    lake.rotateTile();
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

        for (LakeTile lake : ca.getLakeTiles().values())
        {
            if (lake.getIndex() == 1)// TODO get owner is Matrix
                continue;

            if (lake.getRotateDegrees() == 0)
            {
                Image img = new Image(lake.getImage());
                img.draw(lake.getX(), lake.getY(), lake.getSize(),
                        lake.getSize());
            } else
            {
                Image img = new Image(lake.getImage());
                img.setRotation(lake.getRotateDegrees());
                img.draw(lake.getX(), lake.getY());
            }
        }
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

    /*
     * public static void main(String [] arguments) { try { AppGameContainer app
     * = new AppGameContainer(new Matrix("aaaa")); app.setDisplayMode(1300,
     * 1000, false); Display.setInitialBackground(1, 1, 1); app.start(); } catch
     * (SlickException e) { e.printStackTrace(); } }
     */
}