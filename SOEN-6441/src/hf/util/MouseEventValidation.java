package hf.util;

import org.newdawn.slick.*;

public class MouseEventValidation
{
    public static boolean isMouseClick(GameContainer gc)
    {
        Input input = gc.getInput();
        if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
          return true;
        
        return false;
    }
}
