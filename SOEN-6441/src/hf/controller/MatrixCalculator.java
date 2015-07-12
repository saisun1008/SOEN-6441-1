package hf.controller;

import hf.game.common.CardType;
import hf.game.common.ColorEnum;
import hf.game.items.Card;
import hf.game.items.LakeTile;
import hf.ui.matrix.MatrixCell;

import java.util.*;

public class MatrixCalculator{
    private int widthStart = 200;
    private int heightStart = 200;
    private int matrixElementlenght = 30;
    private Map<Integer,MatrixCell> entities = new HashMap<>();
    private Map<Integer,LakeTile> lakeTiles = new HashMap<>();
    private Card selectedCard;

    public Map<Integer,MatrixCell> getEntities()
    {
        return entities;
    }

    public void setEntities(Map<Integer,MatrixCell> entities)
    {
        this.entities = entities;
    }

    public Map<Integer, LakeTile> getLakeTiles()
    {
        return lakeTiles;
    }

    public void setLakeTiles(Map<Integer, LakeTile> lakeTiles)
    {
        this.lakeTiles = lakeTiles;
    }
    
    public Card getSelectedCard()
    {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard)
    {
        this.selectedCard = selectedCard;
    }

    public void init(){
        initLakeTiles();
        initMatrixCell();
    }
    
    public void initLakeTiles()
    {
        LakeTile lake = new LakeTile();
        lake.setImage("data/starttile.png");
        lake.set_topColor(ColorEnum.BLACK);
        lake.set_rightColor(ColorEnum.BLUE);
        lake.set_bottomColor(ColorEnum.RED);
        lake.set_leftColor(ColorEnum.GREY);
        lake.setIndex(1);;
        
        LakeTile lake2 = new LakeTile();
        lake2.setImage("data/starttile.png");
        lake2.set_topColor(ColorEnum.BLACK);
        lake2.set_rightColor(ColorEnum.BLUE);
        lake2.set_bottomColor(ColorEnum.RED);
        lake2.set_leftColor(ColorEnum.GREY);
        lake2.setX(200);
        lake2.setY(150);
        lake2.setSize(30);
        lake2.setIndex(2);
        lake2.flipFaceUp();
        
        LakeTile lake3 = new LakeTile();
        lake3.setImage("data/starttile.png");
        lake3.set_topColor(ColorEnum.BLACK);
        lake3.set_rightColor(ColorEnum.BLUE);
        lake3.set_bottomColor(ColorEnum.RED);
        lake3.set_leftColor(ColorEnum.GREY);
        lake3.setX(231);
        lake3.setY(150);
        lake3.setSize(30);
        lake3.setIndex(3);
        lake3.flipFaceUp();
        
        LakeTile lake4 = new LakeTile();
        lake4.setImage("data/starttile.png");
        lake4.set_topColor(ColorEnum.BLACK);
        lake4.set_rightColor(ColorEnum.BLUE);
        lake4.set_bottomColor(ColorEnum.RED);
        lake4.set_leftColor(ColorEnum.GREY);
        lake4.setX(262);
        lake4.setY(150);
        lake4.setSize(30);
        lake4.setIndex(4);
        lake4.flipFaceUp();
        
        lakeTiles.put(lake.getIndex(), lake);
        lakeTiles.put(lake2.getIndex(), lake2);
        lakeTiles.put(lake3.getIndex(), lake3);
        lakeTiles.put(lake4.getIndex(), lake4);
    }
    
    public void initMatrixCell()
    {
        int rows = 21;
        int cols = 21;
        int gap = 1;
        int id = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MatrixCell matrixElement = new MatrixCell(this);
                matrixElement.setId(id);
                matrixElement.setX(widthStart + j * (matrixElementlenght + gap));
                matrixElement.setY(heightStart + i * (matrixElementlenght + gap));
                matrixElement.setSize(matrixElementlenght);
                if(i==rows/2 && j==cols/2)
                {
                    matrixElement.setLake(lakeTiles.get(1));
                }
                id++;
                entities.put(matrixElement.getId(),matrixElement);
            }
        }
    }
    
    /**
     * Place lake tile to matrix cell.
     * 
     * @param id Lake Tile id
     */
    public void placeLakeTile(int id)
    {
        LakeTile lake = entities.get(id).getLake();
        if(id==221 &&lake!=null && !lake.isFaceUp())
        {
            placeStartLake(lake);
        }else
        {
            if(selectedCard!=null && selectedCard.getCardType()==CardType.LAKETILE)
              placeNewLake((LakeTile)selectedCard, id);
        }
    }
    
    /**
     * Place new lake tile to matrix cell.
     * 
     * @param lake LakeTile
     * @param id Lake Tile id
     */
    public void placeNewLake(LakeTile lake,int id)
    {
        LakeTile left = null;
        LakeTile right = null;
        LakeTile top = null;
        LakeTile bottom = null;
        if(id%21!=1)
            left = entities.get(id-1).getLake();
        
        if(id%21!=0)
            right = entities.get(id+1).getLake();
        
        if(id-21>0)
            top = entities.get(id-21).getLake();
        
        if(id+21<=441)
            bottom = entities.get(id+21).getLake();
        
        if(left==null && right==null && top==null && bottom==null)
        {
            return;
        }
        
        System.out.println("place new lake.");
        entities.get(id).setLake(lake);
        selectedCard = null;
        
        System.out.println("left lake: "+(left==null? "no" :left.getIndex()));
        System.out.println("right lake: "+(right==null? "no" : right.getIndex()));
        System.out.println("top lake: "+(top==null?"no":top.getIndex()));
        System.out.println("bottom lake: "+ (bottom==null?"no":bottom.getIndex()));
        giveLanternCard();
    }
    
    /**
     * Flip start lake tile up
     * 
     * @param lake LakeTile
     */
    public void placeStartLake(LakeTile lake)
    {
        System.out.println("place start lake.");
        lake.flipFaceUp();
        entities.get(lake.getIndex()).setLake(lake);
        giveLanternCard();
    }
    
    public void giveLanternCard()
    {
        System.out.println("give card");
        //TODO
    }
    
}