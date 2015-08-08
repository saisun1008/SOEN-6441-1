package hf.game.strategy;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.LocationEnum;
import hf.game.controller.MatrixCalculator;
import hf.game.items.LakeTile;
import hf.game.items.Player;
import hf.ui.matrix.Matrix;

public class FriendlyStrategy implements PlayerStrategy
{
    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        Matrix matrix = board.getMatrix();
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        Player nextRoundPlayer = board.getNextRoundPlayer();
        
        if(currentRoundPlayer.getLakeTileList().size()==0)
            return false;
        
        LocationEnum sitLocation = nextRoundPlayer.getSitLocation();
        Map<Integer, Integer> matrixLocationIndex = board.getMatrixLocationIndex();
        MatrixCalculator ca = matrix.getMatrixCalculator();

        LakeTile lakeTile = null;
        Integer rightLocation = 0;
        List<Integer> freeLocation = null;
        switch(sitLocation)
        {
            case TOP:
                freeLocation = matrixLocationIndex.keySet().stream().filter(m->!matrixLocationIndex.containsKey(m.intValue()-21)).collect(Collectors.toList());
                
                OK:
                for(Integer location:freeLocation)
                {
                    ColorEnum locationColor = board.getLakeTileByIndex(matrixLocationIndex.get(location)).getLocationColor(sitLocation);
                    for(Integer index :currentRoundPlayer.getLakeTileList())
                    {
                        LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                        if(lakeTileByIndex.get_bottomColor()==locationColor)
                        {
                            
                        }else if(lakeTileByIndex.get_topColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_leftColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_rightColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                        }
                        board.setLakeTileByIndex(index, lakeTileByIndex);
                        lakeTile =lakeTileByIndex;
                        rightLocation = location -21;
                        break OK;
                    }
                }
                
                if(lakeTile==null)
                    lakeTile = board.getLakeTileByIndex(currentRoundPlayer.getLakeTileList().get(0));
                
                if(rightLocation==0)
                    rightLocation = freeLocation.get(0)-21;
            break;
            
            case BOTTOM:
                freeLocation = matrixLocationIndex.keySet().stream().filter(m->matrixLocationIndex.containsKey(m.intValue()+21)).collect(Collectors.toList());
                for(Integer location:freeLocation)
                {
                    ColorEnum locationColor = board.getLakeTileByIndex(matrixLocationIndex.get(location)).getLocationColor(sitLocation);
                    for(Integer index :currentRoundPlayer.getLakeTileList())
                    {
                        LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                        if(lakeTileByIndex.get_bottomColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_topColor()==locationColor)
                        {
                            
                        }else if(lakeTileByIndex.get_leftColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_rightColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }
                        board.setLakeTileByIndex(index, lakeTileByIndex);
                        lakeTile =lakeTileByIndex;
                        rightLocation = location +21;
                        break;
                    }
                }
                
                if(lakeTile==null)
                    lakeTile = board.getLakeTileByIndex(currentRoundPlayer.getLakeTileList().get(0));
                
                if(rightLocation==0)
                    rightLocation = freeLocation.get(0)+21;
                break;
                
            case LEFT:
                freeLocation = matrixLocationIndex.keySet().stream().filter(m->matrixLocationIndex.containsKey(m.intValue()-1)).collect(Collectors.toList());
                for(Integer location:freeLocation)
                {
                    ColorEnum locationColor = board.getLakeTileByIndex(matrixLocationIndex.get(location)).getLocationColor(sitLocation);
                    for(Integer index :currentRoundPlayer.getLakeTileList())
                    {
                        LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                        if(lakeTileByIndex.get_bottomColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_topColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_leftColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_rightColor()==locationColor)
                        {
                        }
                        board.setLakeTileByIndex(index, lakeTileByIndex);
                        lakeTile =lakeTileByIndex;
                        rightLocation = location -1;
                        break;
                    }
                }
                
                if(lakeTile==null)
                    lakeTile = board.getLakeTileByIndex(currentRoundPlayer.getLakeTileList().get(0));
                
                if(rightLocation==0)
                    rightLocation = freeLocation.get(0)-1;
                break;
                
            case RIGHT:
                freeLocation = matrixLocationIndex.keySet().stream().filter(m->matrixLocationIndex.containsKey(m.intValue()+1)).collect(Collectors.toList());
                for(Integer location:freeLocation)
                {
                    ColorEnum locationColor = board.getLakeTileByIndex(matrixLocationIndex.get(location)).getLocationColor(sitLocation);
                    for(Integer index :currentRoundPlayer.getLakeTileList())
                    {
                        LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                        if(lakeTileByIndex.get_bottomColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_topColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }else if(lakeTileByIndex.get_leftColor()==locationColor)
                        {
                        }else if(lakeTileByIndex.get_rightColor()==locationColor)
                        {
                            lakeTileByIndex.rotateTile();
                            lakeTileByIndex.rotateTile();
                        }
                        board.setLakeTileByIndex(index, lakeTileByIndex);
                        lakeTile =lakeTileByIndex;
                        rightLocation = location +1;
                        break;
                    }
                }
                
                if(lakeTile==null)
                    lakeTile = board.getLakeTileByIndex(currentRoundPlayer.getLakeTileList().get(0));
                
                if(rightLocation==0)
                    rightLocation = freeLocation.get(0)+1;
                break;
        }
        
        ca.setSelectedCard(lakeTile.getIndex());
        ca.placeLakeTile(rightLocation);
        
        return true;
    }

    @Override
    public boolean redeemLanternCard(GameBoard board)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String printReasoning()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
