package hf.game.strategy;

import java.util.ArrayList;
import java.util.HashMap;
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

public class UnfriendlyStrategy implements PlayerStrategy
{

 private StringBuilder sb = new StringBuilder();
    
    /**
     * this strategy is always do worst thing for next play.
     * 
     * calculate which is worst lake tile to place. calculate which is worst
     * location.
     * 
     * place the lake tile to the location.
     * 
     * 
     * @param board GameBoard
     * @return boolean place the lake tile success or not
     */
    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        Matrix matrix = board.getMatrix();
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        Player nextRoundPlayer = board.getNextRoundPlayer();

        sb.append("To be blocked palyer is " +nextRoundPlayer.getName()+";\r\n");
        if (currentRoundPlayer.getLakeTileList().size() == 0)
        {
            sb.append("There is no lake tile to place;\r\n");
            return false;
        }

        LocationEnum sitLocation = currentRoundPlayer.getSitLocation();
        sb.append("sit in the location " + sitLocation.name()+";\r\n");
        
        Map<Integer, Integer> matrixLocationIndex = board
                .getMatrixLocationIndex();
        MatrixCalculator ca = matrix.getMatrixCalculator();

        LakeTile lakeTile = null;
        Integer rightLocation = 0;
        List<Integer> freeLocation = null;
        switch (sitLocation)
        {
        case TOP:
            freeLocation = matrixLocationIndex
                    .keySet()
                    .stream()
                    .filter(m -> !matrixLocationIndex.containsKey(m.intValue() - 21))
                    .collect(Collectors.toList());

            OK: for (Integer location : freeLocation)
            {
                ColorEnum locationColor = board.getLakeTileByIndex(
                        matrixLocationIndex.get(location)).getLocationColor(
                        sitLocation);
                for (Integer index : currentRoundPlayer.getLakeTileList())
                {
                    LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                    if (lakeTileByIndex.get_bottomColor() == locationColor)
                    {

                    } else if (lakeTileByIndex.get_topColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_leftColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_rightColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                    }else
                    {
                        continue;
                    }
                    
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location - 21;
                    sb.append("lake tile index " + index+" in the location " + rightLocation +"and rotate "+ lakeTile.getRotateDegrees()+";\r\n");
                    sb.append("can't get two lartern cards.\r\n");
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
            {
                rightLocation = freeLocation.get(0) - 21;
                sb.append("lake tile index " + lakeTile.getIndex()+" in the location " + rightLocation+" randomly;\r\n");
            }
            break;

        case BOTTOM:
            freeLocation = matrixLocationIndex
                    .keySet()
                    .stream()
                    .filter(m -> !matrixLocationIndex.containsKey(m.intValue() + 21))
                    .collect(Collectors.toList());

            OK: for (Integer location : freeLocation)
            {
                ColorEnum locationColor = board.getLakeTileByIndex(
                        matrixLocationIndex.get(location)).getLocationColor(
                        sitLocation);
                for (Integer index : currentRoundPlayer.getLakeTileList())
                {
                    LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                    if (lakeTileByIndex.get_bottomColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_topColor() == locationColor)
                    {

                    } else if (lakeTileByIndex.get_leftColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_rightColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    }else
                    {
                        continue;
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location + 21;
                    
                    sb.append("lake tile index " + index+" in the location " + rightLocation +"and rotate "+ lakeTile.getRotateDegrees()+";\r\n");
                    sb.append("can't get two lartern cards.\r\n");
                    
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
            {
                rightLocation = freeLocation.get(0) + 21;
                sb.append("lake tile index " + lakeTile.getIndex()+" in the location " + rightLocation+" randomly;\r\n");
            }
            break;

        case LEFT:
            freeLocation = matrixLocationIndex
                    .keySet()
                    .stream()
                    .filter(m -> !matrixLocationIndex.containsKey(m.intValue() - 1))
                    .collect(Collectors.toList());

            OK: for (Integer location : freeLocation)
            {
                ColorEnum locationColor = board.getLakeTileByIndex(
                        matrixLocationIndex.get(location)).getLocationColor(
                        sitLocation);
                for (Integer index : currentRoundPlayer.getLakeTileList())
                {
                    LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                    if (lakeTileByIndex.get_bottomColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_topColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_leftColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_rightColor() == locationColor)
                    {
                    }else
                    {
                        continue;
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location - 1;
                    
                    sb.append("lake tile index " + index+" in the location " + rightLocation +"and rotate "+ lakeTile.getRotateDegrees()+"\r\n");
                    sb.append("can't get two lartern cards.\r\n");
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
            {
                rightLocation = freeLocation.get(0) - 1;
                sb.append("lake tile index " + lakeTile.getIndex()+" in the location " + rightLocation+" randomly;\r\n");
            }
            break;

        case RIGHT:
            freeLocation = matrixLocationIndex
                    .keySet()
                    .stream()
                    .filter(m -> !matrixLocationIndex.containsKey(m.intValue() + 1))
                    .collect(Collectors.toList());

            OK: for (Integer location : freeLocation)
            {
                ColorEnum locationColor = board.getLakeTileByIndex(
                        matrixLocationIndex.get(location)).getLocationColor(
                        sitLocation);
                for (Integer index : currentRoundPlayer.getLakeTileList())
                {
                    LakeTile lakeTileByIndex = board.getLakeTileByIndex(index);
                    if (lakeTileByIndex.get_bottomColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_topColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    } else if (lakeTileByIndex.get_leftColor() == locationColor)
                    {
                    } else if (lakeTileByIndex.get_rightColor() == locationColor)
                    {
                        lakeTileByIndex.rotateTile();
                        lakeTileByIndex.rotateTile();
                    }else
                    {
                        continue;
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location + 1;
                    sb.append("lake tile index " + index+" in the location " + rightLocation +"and rotate "+ lakeTile.getRotateDegrees()+";\r\n");
                    sb.append("can't get two lartern cards.\r\n");
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
            {
                rightLocation = freeLocation.get(0) + 1;
                sb.append("lake tile index " + lakeTile.getIndex()+" in the location " + rightLocation+" randomly;\r\n");
            }
            break;
        }

        ca.setSelectedCard(lakeTile.getIndex());
        ca.placeLakeTile(rightLocation);

        return true;
    }

    @Override
    public boolean redeemLanternCard(GameBoard board)
    {
        Player currentRoundPlayer = board.getCurrentRoundPlayer();

        // {RED=[33], ORANGE=[28, 14], BLUE=[32]}
        HashMap<ColorEnum, ArrayList<Integer>> lanternList = currentRoundPlayer
                .getLanternList();

        List<ColorEnum> four = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() >= 4)
                .collect(Collectors.toList());
        if (four.size() > 0)
        {
            HashMap<ColorEnum, Integer> fourMap = new HashMap<>();
            four.stream().forEach(
                    c -> fourMap.put(c, lanternList.get(c).size()));
            board.exchangeFourOfKind(fourMap);
        }

        List<ColorEnum> three = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() >= 2)
                .collect(Collectors.toList());
        if (three.size() >= 3)
        {
            HashMap<ColorEnum, Integer> threeMap = new HashMap<>();
            three.stream().forEach(c ->
            {
                if (threeMap.size() < 3)
                    threeMap.put(c, 2);
            });
            board.exchangeThreePair(threeMap);
        }

        List<ColorEnum> seven = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() >= 1)
                .collect(Collectors.toList());
        if (seven.size() >= 7)
        {
            HashMap<ColorEnum, Integer> sevenMap = new HashMap<>();
            seven.stream().forEach(c ->
            {
                if (sevenMap.size() < 7)
                    sevenMap.put(c, 1);
            });
            board.exchangeSevenUnique(sevenMap);
        }

        return true;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        if(currentRoundPlayer.getFavorTokenList().size()<=2)
            return false;

        HashMap<ColorEnum, ArrayList<Integer>> lanternList = currentRoundPlayer
                .getLanternList();

        HashMap<ColorEnum, ArrayList<Integer>> latternDecks = board.getLatternDecks();
        ColorEnum colorTake=null;
        List<ColorEnum> four = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() == 3)
                .collect(Collectors.toList());
        if (four.size() > 0)
        {
            for(ColorEnum color:four)
            {
                if(latternDecks.get(color)!=null && latternDecks.get(color).size()>0)
                {
                    colorTake = color;
                    break;
                }
            }
        }
        
        if(useFavorToken(board, lanternList, colorTake))
            return true;

        List<ColorEnum> three = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() >= 2)
                .collect(Collectors.toList());
        if (three.size() == 2)
        {
            List<ColorEnum> one = lanternList.keySet().stream()
            .filter(c -> lanternList.get(c).size() == 1)
            .collect(Collectors.toList());
            
            if (one.size() > 0)
            {
                for(ColorEnum color:one)
                {
                    if(latternDecks.get(color)!=null && latternDecks.get(color).size()>0)
                    {
                        colorTake = color;
                        break;
                    }
                }
            }
            
            if(useFavorToken4Three(board, lanternList, colorTake,three))
                return true;
        }

        List<ColorEnum> seven = lanternList.keySet().stream()
                .filter(c -> lanternList.get(c).size() >= 1)
                .collect(Collectors.toList());
        if (seven.size() == 6)
        {
            List<ColorEnum> six = board.getLatternDecks().keySet().stream()
                    .filter(c -> !lanternList.containsKey(c) && board.getLatternDecks().get(c).size()>0)
                    .collect(Collectors.toList());
            
            if (six.size() > 0)
            {
                        colorTake = six.get(0);
            }
            
            if(useFavorToken4six(board, lanternList, colorTake,seven))
                return true;
            
        }

        return true;
    }

    private boolean useFavorToken4six(GameBoard board,
            HashMap<ColorEnum, ArrayList<Integer>> lanternList,
            ColorEnum colorTake, List<ColorEnum> seven)
    {
        ColorEnum colorGive;
        if(colorTake!=null)
        {
            for(ColorEnum c:lanternList.keySet())
            {
                if(!colorTake.equals(c) && lanternList.get(c).size()>0)
                {
                    if(seven.contains(c) &&  lanternList.get(c).size()==1)
                        continue;
                    
                    colorGive = c;
                    board.useFavorToken(colorGive, colorTake);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean useFavorToken4Three(GameBoard board,
            HashMap<ColorEnum, ArrayList<Integer>> lanternList,
            ColorEnum colorTake, List<ColorEnum> three)
    {
        ColorEnum colorGive;
        if(colorTake!=null)
        {
            for(ColorEnum c:lanternList.keySet())
            {
                if(!colorTake.equals(c) && lanternList.get(c).size()>0)
                {
                    if(three.contains(c) &&  lanternList.get(c).size()==2)
                        continue;
                    
                    colorGive = c;
                    board.useFavorToken(colorGive, colorTake);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean useFavorToken(GameBoard board,
            HashMap<ColorEnum, ArrayList<Integer>> lanternList,
            ColorEnum colorTake)
    {
        ColorEnum colorGive;
        if(colorTake!=null)
        {
            for(ColorEnum c:lanternList.keySet())
            {
                if(!colorTake.equals(c) && lanternList.get(c).size()>0)
                {
                    colorGive = c;
                    board.useFavorToken(colorGive, colorTake);
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public String printReasoning()
    {
        return sb.toString();
    }

}
