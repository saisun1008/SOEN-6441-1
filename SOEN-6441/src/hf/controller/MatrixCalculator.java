package hf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.controller.MatrixObserver;
import hf.game.items.LakeTile;
import hf.game.items.Player;
import hf.ui.matrix.MatrixCell;

public class MatrixCalculator
{
    private int widthStart = 300;
    private int heightStart = 150;
    private int matrixElementlenght = 30;
    private Map<Integer, MatrixCell> entities = new HashMap<>();
    // private Map<Integer, LakeTile> lakeTiles = new HashMap<>();
    // List<Integer> lakeTikeIndexList = new ArrayList<>();
    private Integer selectedCardIndex;
    private GameBoard gameBoard;

    @XStreamOmitField
    private ArrayList<MatrixObserver> observers;
   
    public MatrixCalculator()
    {
        observers = new ArrayList<MatrixObserver>();
        entities = new HashMap<>();
    }

    public Map<Integer, MatrixCell> getEntities()
    {
        return entities;
    }

    public void setEntities(Map<Integer, MatrixCell> entities)
    {
        this.entities = entities;
    }

    // public Map<Integer, LakeTile> getLakeTiles()
    // {
    // return lakeTiles;
    // }
    //
    // public void setLakeTiles(Map<Integer, LakeTile> lakeTiles)
    // {
    // this.lakeTiles = lakeTiles;
    // for(int i :lakeTiles.keySet())
    // gameBoard.setLakeTileByIndex(i, lakeTiles.get(i));
    // }

    public Integer getSelectedCard()
    {
        return selectedCardIndex;
    }

    public void setSelectedCard(Integer selectedCard)
    {
        this.selectedCardIndex = selectedCard;
    }

    public void init(GameBoard board)
    {
        gameBoard = board;
        initLakeTiles();
        initMatrixCell();
    }

    public void initLakeTiles()
    {
    }
    /**
     * initial matrix cells
     * @author Guocai
     */
    public void initMatrixCell()
    {
        int rows = 21;
        int cols = 21;
        int gap = 1;
        int id = 1;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                MatrixCell matrixElement = new MatrixCell(this);
                matrixElement.setId(id);
                matrixElement
                        .setX(widthStart + j * (matrixElementlenght + gap));
                matrixElement.setY(heightStart + i
                        * (matrixElementlenght + gap));
                matrixElement.setSize(matrixElementlenght);
                // if (i == rows / 2 && j == cols / 2)
                // {
                // matrixElement.setLake(gameBoard.getLakeTileByIndex(1));
                // }
                if (gameBoard.getMatrixLocationIndex().containsKey(id))
                    matrixElement.setLake(gameBoard
                            .getLakeTileByIndex(gameBoard
                                    .getMatrixLocationIndex().get(id)));
                id++;
                entities.put(matrixElement.getId(), matrixElement);
            }
        }
        placeStartLake(gameBoard.getLakeTileByIndex(gameBoard
                .getMatrixLocationIndex().get(221)), 221);
        notifyAllObservers();
    }

    /**
     * Place lake tile to matrix cell.
     * 
     * @param id
     *            Lake Tile id
     */
    public void placeLakeTile(int id)
    {
        LakeTile lake = entities.get(id).getLake();
        if (id == 221 && lake != null)
        {
            placeStartLake(lake, id);
        } else
        {
            if (selectedCardIndex != null)
                placeNewLake(selectedCardIndex, id);
        }
        notifyAllObservers();
    }

    /**
     * Place new lake tile to matrix cell.
     * 
     * @param lake
     *            LakeTile
     * @param id
     *            Lake Tile id
     */
    public void placeNewLake(int index, int id)
    {
        LakeTile left = null;
        LakeTile right = null;
        LakeTile top = null;
        LakeTile bottom = null;
        if (id % 21 != 1)
            left = entities.get(id - 1).getLake();

        if (id % 21 != 0)
            right = entities.get(id + 1).getLake();

        if (id - 21 > 0)
            top = entities.get(id - 21).getLake();

        if (id + 21 <= 441)
            bottom = entities.get(id + 21).getLake();

        if (left == null && right == null && top == null && bottom == null)
        {
            return;
        }

        LakeTile lake = gameBoard.getLakeTileByIndex(index);
        System.out.println("place new lake.");
        entities.get(id).setLake(lake);

        gameBoard.getCurrentRoundPlayer().getLakeTileList()
                .remove(selectedCardIndex);
        if (gameBoard.getLakeTileDeck() != null
                && !gameBoard.getLakeTileDeck().isEmpty())
        {
            gameBoard.getCurrentRoundPlayer().getLakeTileList()
                    .add(gameBoard.getLakeTileDeck().get(0));
            gameBoard.getLakeTileDeck().remove(0);
        }
        selectedCardIndex = null;

        System.out.println("left lake: "
                + (left == null ? "no" : left.getIndex()));
        System.out.println("right lake: "
                + (right == null ? "no" : right.getIndex()));
        System.out
                .println("top lake: " + (top == null ? "no" : top.getIndex()));
        System.out.println("bottom lake: "
                + (bottom == null ? "no" : bottom.getIndex()));
        giveLanternCard(lake, left, right, top, bottom);
        gameBoard.makeNewRound();
    }

    /**
     * Flip start lake tile up
     * 
     * @param lake
     *            LakeTile
     * @param id
     *            Lake tile id
     */
    public void placeStartLake(LakeTile lake, int id)
    {
        System.out.println("place start lake.");
        lake.flipFaceUp();
        entities.put(id, entities.get(id).setLake(lake));
        giveLanternCard(lake, null, null, null, null);
    }
    
    /**
     * Give away lattern card to every player
     * @param lake
     * @param lakeLeft
     * @param lakeRight
     * @param lakeTop
     * @param lakeBtm
     */
    public void giveLanternCard(LakeTile lake, LakeTile lakeLeft,
            LakeTile lakeRight, LakeTile lakeTop, LakeTile lakeBtm)
    {
        System.out.println("give card");

        for (Player p : gameBoard.getPlayers())
        {
            if (lakeLeft != null
                    && lake.get_leftColor() == lakeLeft.get_rightColor())
            {
                ArrayList<Integer> deckLartain1 = null;
                if (p.getSitLocation() == LocationEnum.RIGHT)
                {
                    deckLartain1 = gameBoard.getLatternDecks().get(
                            lake.get_leftColor());
                    int cardToGive = -1;
                    if (deckLartain1.size() > 0)
                    {
                        cardToGive = deckLartain1.get(deckLartain1.size() - 1);
                        deckLartain1.remove(deckLartain1.size() - 1);
                        gameBoard.getLatternDecks().put(lake.get_leftColor(),
                                deckLartain1);
                    }

                    ArrayList<Integer> palyLantern = p.getLanternList().get(
                            lake.get_leftColor());
                    if (palyLantern == null)
                        palyLantern = new ArrayList<>();

                    if (cardToGive != -1)
                    {
                        palyLantern.add(cardToGive);
                    }
                    p.getLanternList().put(lake.get_leftColor(), palyLantern);
                }

                if (lakeLeft.hasSpecialIcon())
                {
                    gameBoard
                            .getCurrentRoundPlayer()
                            .getFavorTokenList()
                            .add(gameBoard.getFavorTokenCollection().get(0)
                                    .getIndex());
                    gameBoard.getFavorTokenCollection().remove(0);
                }
            }

            if (lakeRight != null
                    && lake.get_rightColor() == lakeRight.get_leftColor())
            {
                ArrayList<Integer> deckLartain1 = null;
                int cardToGive = -1;
                if (p.getSitLocation() == LocationEnum.LEFT)
                {
                    deckLartain1 = gameBoard.getLatternDecks().get(
                            lake.get_rightColor());
                    if (deckLartain1.size() > 0)
                    {
                        cardToGive = deckLartain1.get(deckLartain1.size() - 1);
                        deckLartain1.remove(deckLartain1.size() - 1);
                        gameBoard.getLatternDecks().put(lake.get_rightColor(),
                                deckLartain1);
                    }

                    ArrayList<Integer> palyLantern = p.getLanternList().get(
                            lake.get_rightColor());
                    if (palyLantern == null)
                        palyLantern = new ArrayList<>();

                    if (cardToGive != -1)
                    {
                        palyLantern.add(cardToGive);
                    }
                    p.getLanternList().put(lake.get_rightColor(), palyLantern);
                }

                if (lakeRight.hasSpecialIcon())
                {
                    gameBoard
                            .getCurrentRoundPlayer()
                            .getFavorTokenList()
                            .add(gameBoard.getFavorTokenCollection().get(0)
                                    .getIndex());
                    gameBoard.getFavorTokenCollection().remove(0);
                }
            }

            if (lakeTop != null
                    && lake.get_topColor() == lakeTop.get_bottomColor())
            {
                ArrayList<Integer> deckLartain1 = null;
                if (p.getSitLocation() == LocationEnum.BOTTOM)
                {
                    deckLartain1 = gameBoard.getLatternDecks().get(
                            lake.get_topColor());
                    int cardToGive = -1;
                    if (deckLartain1.size() > 0)
                    {
                        cardToGive = deckLartain1.get(deckLartain1.size() - 1);
                        deckLartain1.remove(deckLartain1.size() - 1);
                        gameBoard.getLatternDecks().put(lake.get_topColor(),
                                deckLartain1);
                    }

                    ArrayList<Integer> palyLantern = p.getLanternList().get(
                            lake.get_topColor());
                    if (palyLantern == null)
                        palyLantern = new ArrayList<>();

                    if (cardToGive != -1)
                    {
                        palyLantern.add(cardToGive);
                    }
                    p.getLanternList().put(lake.get_topColor(), palyLantern);
                }

                if (lakeTop.hasSpecialIcon())
                {
                    gameBoard
                            .getCurrentRoundPlayer()
                            .getFavorTokenList()
                            .add(gameBoard.getFavorTokenCollection().get(0)
                                    .getIndex());
                    gameBoard.getFavorTokenCollection().remove(0);
                }
            }

            if (lakeBtm != null
                    && lake.get_bottomColor() == lakeBtm.get_topColor())
            {
                ArrayList<Integer> deckLartain1 = null;
                if (p.getSitLocation() == LocationEnum.TOP)
                {
                    int cardToGive = -1;
                    deckLartain1 = gameBoard.getLatternDecks().get(
                            lake.get_bottomColor());
                    if (deckLartain1.size() > 0)
                    {
                        cardToGive = deckLartain1.get(deckLartain1.size() - 1);
                        deckLartain1.remove(deckLartain1.size() - 1);
                        gameBoard.getLatternDecks().put(lake.get_bottomColor(),
                                deckLartain1);
                    }

                    ArrayList<Integer> palyLantern = p.getLanternList().get(
                            lake.get_bottomColor());
                    if (palyLantern == null)
                        palyLantern = new ArrayList<>();

                    if (cardToGive != -1)
                    {
                        palyLantern.add(cardToGive);
                    }
                    p.getLanternList().put(lake.get_bottomColor(), palyLantern);
                }

                if (lakeBtm.hasSpecialIcon())
                {
                    gameBoard
                            .getCurrentRoundPlayer()
                            .getFavorTokenList()
                            .add(gameBoard.getFavorTokenCollection().get(0)
                                    .getIndex());
                    gameBoard.getFavorTokenCollection().remove(0);
                }
            }

            if (lake.hasSpecialIcon()
                    && p.getName().equals(
                            gameBoard.getCurrentRoundPlayer().getName()))
            {
                gameBoard
                        .getCurrentRoundPlayer()
                        .getFavorTokenList()
                        .add(gameBoard.getFavorTokenCollection().get(0)
                                .getIndex());
                gameBoard.getFavorTokenCollection().remove(0);
            }

            ArrayList<Integer> deckLartain = null;
            ColorEnum color = null;
            if (p.getSitLocation() == LocationEnum.LEFT)
            {
                color = lake.get_leftColor();
            } else if (p.getSitLocation() == LocationEnum.RIGHT)
            {
                color = lake.get_rightColor();
            } else if (p.getSitLocation() == LocationEnum.TOP)
            {
                color = lake.get_topColor();
            } else if (p.getSitLocation() == LocationEnum.BOTTOM)
            {
                color = lake.get_bottomColor();
            }

            int cardToGive = -1;
            deckLartain = gameBoard.getLatternDecks().get(color);
            if (deckLartain.size() > 0)
            {
                cardToGive = deckLartain.get(deckLartain.size() - 1);
                deckLartain.remove(deckLartain.size() - 1);
                gameBoard.getLatternDecks().put(color, deckLartain);
            }

            ArrayList<Integer> palyLantern = p.getLanternList().get(color);
            if (palyLantern == null)
                palyLantern = new ArrayList<>();

            if (cardToGive != -1)
            {
                palyLantern.add(cardToGive);
            }
            p.getLanternList().put(color, palyLantern);
        }
        // TODO
    }

    /**
     * Attach to observer
     * @param observer
     */
    public void attach(MatrixObserver observer)
    {
        observers.add(observer);
    }
    
    /**
     * Entity to index map
     * 
     * @return HashMap<Integer, Integer> 
     *                                    
     */

    public HashMap<Integer, Integer> entityToIndexMap()
    {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int key : entities.keySet())
        {
            if (entities.get(key).getLake() == null)
            {
                continue;
            }
            map.put(key, entities.get(key).getLake().getIndex());
        }
        return map;
    }

    public void notifyAllObservers()
    {
        for (MatrixObserver observer : observers)
        {
            observer.update(entityToIndexMap());
        }
    }
}