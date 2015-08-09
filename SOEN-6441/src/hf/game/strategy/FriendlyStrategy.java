package hf.game.strategy;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
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
    /**
     * this strategy is always do best thing for next play.
     * 
     * calculate which is best lake tile to place. calculate which is best
     * location.
     * 
     * place the lake tile to the location.
     * 
     * @return boolean place the lake tile success or not
     */
    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        Matrix matrix = board.getMatrix();
        Player currentRoundPlayer = board.getCurrentRoundPlayer();
        Player nextRoundPlayer = board.getNextRoundPlayer();

        if (currentRoundPlayer.getLakeTileList().size() == 0)
            return false;

        LocationEnum sitLocation = nextRoundPlayer.getSitLocation();
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
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location - 21;
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
                rightLocation = freeLocation.get(0) - 21;
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
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location + 21;
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
                rightLocation = freeLocation.get(0) + 21;
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
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location - 1;
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
                rightLocation = freeLocation.get(0) - 1;
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
                    }
                    board.setLakeTileByIndex(index, lakeTileByIndex);
                    lakeTile = lakeTileByIndex;
                    rightLocation = location + 1;
                    break OK;
                }
            }

            if (lakeTile == null)
                lakeTile = board.getLakeTileByIndex(currentRoundPlayer
                        .getLakeTileList().get(0));

            if (rightLocation == 0)
                rightLocation = freeLocation.get(0) + 1;
            break;
        }

        ca.setSelectedCard(lakeTile.getIndex());
        ca.placeLakeTile(rightLocation);

        // try
        // {
        // Thread.sleep(1000);
        // } catch (InterruptedException e)
        // {
        // e.printStackTrace();
        // }
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
