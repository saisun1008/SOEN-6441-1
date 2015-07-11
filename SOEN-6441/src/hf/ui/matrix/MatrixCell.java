package hf.ui.matrix;


import hf.controller.MatrixCalculator;
import hf.game.items.LakeTile;

import org.newdawn.slick.*;

public class MatrixCell extends BasicGame
{
    public MatrixCell(MatrixCalculator ca)
    {
        super(null);
        this.ca = ca;
    }
    
    private int id;
    private int x;
    private int y;
    private int size;
    private String image ="data/grass.png";
    private LakeTile lake;
    private MatrixCalculator ca;
    
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
       Image img = new Image(image);
       img.draw(x, y,size,size);
    }

    public void init(GameContainer arg0) throws SlickException
    {
    }

    public void update(GameContainer gc, int i) throws SlickException
    {
        Input input = gc.getInput();
        int xpos = input.getMouseX();
        int ypos = input.getMouseY();
        
        if ((xpos > x && xpos < x+size) && (ypos > y && ypos < y+size)) 
        {
            System.out.println("maxtrix cell "+id+"has been clicked");
            ca.calculateMatrix(id);
        }
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
    
    public LakeTile getLake()
    {
        return lake;
    }

    public void setLake(LakeTile lake)
    {
        this.image = lake.getImage();
        this.lake = lake;
    }
}
