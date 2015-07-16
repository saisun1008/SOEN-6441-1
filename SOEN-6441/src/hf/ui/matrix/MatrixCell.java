package hf.ui.matrix;

import hf.controller.MatrixCalculator;
import hf.game.items.LakeTile;

import org.newdawn.slick.*;

/**
 * 
 * This is play zone Matrix cell UI which one is used to place one lake tile.
 * 
 * @author caogc
 * @since 2015-07-11
 * 
 */
public class MatrixCell extends BasicGame
{
    /**
     * Matrix Cell constructor
     * 
     * @param ca
     *            :Matrix Calculator
     */
    public MatrixCell(MatrixCalculator ca)
    {
        super(null);
        this.ca = ca;
    }

    private int id;
    private int x;
    private int y;
    private int size;
    private String image = "data/grass.png";
    private LakeTile lake;
    private MatrixCalculator ca;

    /**
     * Render Matrix Cell UI.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        Image img = new Image(image);
        if (lake != null && lake.getRotateDegrees() != 0)
        {
            img.setRotation(lake.getRotateDegrees());
            img.draw(x, y);
        } else
        {
            img.draw(x, y, size, size);
        }
    }

    public void setCa(MatrixCalculator ca)
    {
        this.ca = ca;
    }

    /**
     * Initialization of Matrix Cell, is used to initial matrix cell object.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    public void init(GameContainer gc) throws SlickException
    {
    }

    /**
     * Process mouse left click event on matrix cell.
     * 
     * Invoked automatically by UI program.
     * 
     * @param GameContainer
     * 
     * @throws SlickException
     */
    public void update(GameContainer gc, int i) throws SlickException
    {
        Input input = gc.getInput();
        int xpos = input.getMouseX();
        int ypos = input.getMouseY();

        if ((xpos > x && xpos < x + size) && (ypos > y && ypos < y + size))
        {
            System.out.println("maxtrix cell " + id + "has been clicked");
            ca.placeLakeTile(id);
        }
    }

    /**
     * Get Maxtrix cell ID
     * 
     * @return Maxtrix cell id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set Maxtrix cell ID
     * 
     * @param Maxtrix
     *            cell id
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Get Maxtrix cell location coordinate X
     * 
     * @return Maxtrix cell location coordinate X
     */
    public int getX()
    {
        return x;
    }

    /**
     * Set Maxtrix cell location coordinate X
     * 
     * @param Maxtrix
     *            cell location coordinate X
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * Get Maxtrix cell location coordinate Y
     * 
     * @return Maxtrix cell location coordinate Y
     */
    public int getY()
    {
        return y;
    }

    /**
     * Set Maxtrix cell location coordinate Y
     * 
     * @param Maxtrix
     *            cell location coordinate Y
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Get Maxtrix cell location size
     * 
     * @return Maxtrix cell location size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Set Maxtrix cell location size
     * 
     * @param Maxtrix
     *            cell location size
     */
    public void setSize(int size)
    {
        this.size = size;
    }

    /**
     * Get Maxtrix cell image uri and name
     * 
     * @return Maxtrix cell image
     */
    public String getImage()
    {
        return image;
    }

    /**
     * Set Maxtrix cell image
     * 
     * @param Maxtrix
     *            cell image
     */
    public void setImage(String image)
    {
        this.image = image;
    }

    /**
     * Get Lake Tile in this matrix cell
     * 
     * @return Lake Tile in this matrix cell
     */
    public LakeTile getLake()
    {
        return lake;
    }

    /**
     * Set Lake Tile in this matrix cell
     * 
     * Set lake tile image to matrix cell image
     * 
     * @param Lake
     *            Tile
     * @return
     */
    public MatrixCell setLake(LakeTile lake)
    {
        this.image = lake.getImage();
        this.lake = lake;
        return this;
    }
}
