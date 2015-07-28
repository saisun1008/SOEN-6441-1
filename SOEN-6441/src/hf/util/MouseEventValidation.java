package hf.util;

import org.newdawn.slick.*;
/**
 * validate mouse event
 * 
 * @author Ge Yin
 *
 */
public class MouseEventValidation
{
    /**
     * check if left button on the mouse is clicked
     * @param gc
     * @return true if left button is clicked
     */
    public static boolean isMouseLeftClick(GameContainer gc)
    {
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
          return true;
        
        return false;
    }
    
    /**
     * check if right button on the mouse is clicked
     * @param gc
     * @return true if right button is clicked
     */
    public static boolean isMouseRightClick(GameContainer gc)
    {
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
            return true;
        
        return false;
    }
}
