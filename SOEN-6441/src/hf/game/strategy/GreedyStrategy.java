package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.items.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GreedyStrategy implements PlayerStrategy
{

    // These attributes will not be saved
    private Player tmpPlayer;
    private ColorEnum wantedColor = null;

    public GreedyStrategy(Player p)
    {
        tmpPlayer = p;
    }

    public void setPlayer(Player p)
    {
        tmpPlayer = p;
    }

    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        return false;
    }

    @Override
    public boolean redeemLanternCard(GameBoard board)
    {
        return false;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        ColorEnum color = getCurrentMaxDedicationColor(board);
        HashMap<ColorEnum, Integer> list = validateLanternCards(tmpPlayer,
                color);
        if (list != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Find out which color of dedication card has the current largest value
     * 
     * @param board
     *            board that currently is played on
     * @return color of the dedication with largest value
     */
    private ColorEnum getCurrentMaxDedicationColor(GameBoard board)
    {
        ColorEnum result = null;
        int maxValue = 0;
        HashMap<ColorEnum, ArrayList<Integer>> list = board
                .getDedicationTokenDeck();
        for (ColorEnum color : list.keySet())
        {
            if (board.getDedicationTokenByIndex(list.get(color).get(0))
                    .getCardValue() > maxValue)
            {
                maxValue = board.getDedicationTokenByIndex(
                        list.get(color).get(0)).getCardValue();
                result = color;
            }
        }
        return result;
    }

    private HashMap<ColorEnum, Integer> validateLanternCards(Player player,
            ColorEnum color)
    {
        HashMap<ColorEnum, Integer> exchangeList = null;
        switch (color)
        {
        // this means a four of kind needs to be done
        case RED:
            // TODO
            break;
        // this means a three pair exchange needs to be done
        case BLUE:
            break;
        // this means a seven unique needs to be done
        case GREEN:
            break;

        default:
            break;
        }
        return exchangeList;
    }

}
