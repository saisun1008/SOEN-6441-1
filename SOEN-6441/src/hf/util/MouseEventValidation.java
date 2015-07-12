package hf.util;

import org.newdawn.slick.*;

public class MouseEventValidation
{
    public static boolean isMouseLeftClick(GameContainer gc)
    {
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
          return true;
        
        return false;
    }
    
    public static boolean isMouseRightClick(GameContainer gc)
    {
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON))
            return true;
        
        return false;
    }
}
