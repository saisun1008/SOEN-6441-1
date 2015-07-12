package hf.ui;

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

    public GameCanvas(String title)
    {
        super(title);
    }

    Image image;

    @Override
    public void init(GameContainer gc) throws SlickException
    {
        image = new Image("images/Lanterns-cover.jpg");
        gc.setShowFPS(false);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException
    {
        // now it's simple rotating the image
        Input input = gc.getInput();
        if (input.isMousePressed(input.MOUSE_LEFT_BUTTON))
        {
            if (input.getMouseX() >= 100 && input.getMouseX() <= 150)
            {
                image.rotate(90);
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        g.drawImage(image, 0, 0);
    }

}
