package hf.ui.matrix;

import hf.controller.MatrixCalculator;
import hf.game.common.ColorEnum;
import hf.game.items.LakeTile;
import hf.util.MouseEventValidation;

import java.util.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;

public class Matrix extends BasicGame {
    
    MatrixCalculator ca = new MatrixCalculator();
    
    public Matrix(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        ca.init();
    }
    
   
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        
        boolean mouseLeftClick = MouseEventValidation.isMouseLeftClick(gc);
        boolean mouseRightClick = MouseEventValidation.isMouseRightClick(gc);
        if(!mouseLeftClick&&!mouseRightClick)
            return;
        
        for( BasicGame entity : ca.getEntities().values() ) {
               entity.update( gc, i );
         }
        
        for( LakeTile lake : ca.getLakeTiles().values() ){
            Input input = gc.getInput();
            int xpos = input.getMouseX();
            int ypos = input.getMouseY();
            
            if ((xpos > lake.getX() && xpos < lake.getX()+lake.getSize()) && (ypos > lake.getY() && ypos < lake.getY()+lake.getSize())) 
            {
                if(!lake.isFaceUp())
                {
                    System.out.println("invalid select");
                    return;
                }
                
                if(mouseLeftClick)
                {
                    ca.setSelectedCard(lake);
                    System.out.println("lake card "+ lake.getIndex() +" has been selected");
                }else if(mouseRightClick)
                {
                    lake.rotateTile();
                    System.out.println("lake card "+ lake.getIndex() +" been rotated 90");
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
//        Random rand = new Random();
//        final int gap = 1;
//        int rows = 36;//(gc.getHeight() - 100) / (size + gap);
//        int cols = 36;//gc.getWidth() / (size + gap);
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
////                g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
//                g.drawImage(new Image("data/grass.png"), 11, 11);
//                g.fillRect(widthStart + j * (matrixElementlenght + gap), heightStart + i * (matrixElementlenght + gap), matrixElementlenght, matrixElementlenght);
//            }
//        }
        for( BasicGame entity : ca.getEntities().values() )
            entity.render( gc, g );
        
        for( LakeTile lake : ca.getLakeTiles().values() ){
            if(lake.getIndex() == 1)//TODO get owner is Matrix
                continue;
            
            if(lake.getRotateDegrees()==0)
            {
                Image img = new Image(lake.getImage());
                img.draw(lake.getX(), lake.getY(),lake.getSize(),lake.getSize());
            }else
            {
                Image img = new Image(lake.getImage());
//                img.setCenterOfRotation((lake.getX() + lake.getSize() )/ 2, (lake.getY() + lake.getSize())/ 2);
                img.setRotation(lake.getRotateDegrees());
                img.draw(lake.getX(), lake.getY());
//                g.drawImage(img,lake.getX(), lake.getY());
//                g.fillRect(lake.getX(), lake.getY(),lake.getSize(),lake.getSize());
            }
        }
    }
    
    public static void main(String [] arguments)
    {
        try
        {
            AppGameContainer app = new AppGameContainer(new Matrix("aaaa"));
            app.setDisplayMode(1300, 900, false);
            Display.setInitialBackground(1, 1, 1);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}