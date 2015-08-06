package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.items.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GreedyStrategy implements PlayerStrategy
{

    // These attributes will not be saved
    private Player tmpPlayer;
    private ColorEnum wantedColor = null;
    private ColorEnum leastWantedColor = null;

    public GreedyStrategy(Player p)
    {
        tmpPlayer = p;
    }

    public void setPlayer(Player p)
    {
        tmpPlayer = p;
    }

    /**
     * Will place lake tile to achieve the goal of acquiring the wanted color,
     * if no lake tile in hand can fulfill that, then pick one lake tile and
     * just place it
     */
    @Override
    public boolean placeLakeTile(GameBoard board)
    {
        ColorEnum color = getCurrentMaxDedicationColor(board);
        HashMap<ColorEnum, Integer> list = validateLanternCards(tmpPlayer,
                color);

        // randomly place a lake tile is fine
        if (wantedColor == null)
        {
            randomlyPlaceLakeTile(board);
        }
        // need to check if current owned lake tile can give player the wanted
        // color, if not, randomly place a lake tile
        else
        {
            if (pickAndPlaceWantedLakeTile(color, board))
            {
                return true;
            } else
            {
                randomlyPlaceLakeTile(board);
            }
        }
        return true;
    }

    /**
     * Randomly pick a owned lake tile and place it at a random available
     * location
     */
    private void randomlyPlaceLakeTile(GameBoard board)
    {

    }

    /**
     * Pick a lake tile to allow player to acquire the wanted lantern card, will
     * also rotate the card to proper degree, and finally place it
     * 
     * @param color
     *            wanted lantern card color
     * @return true if the lake tile can be found and rotated to ready to place
     */
    private boolean pickAndPlaceWantedLakeTile(ColorEnum color, GameBoard board)
    {
        return false;
    }

    @Override
    public boolean redeemLanternCard(GameBoard board)
    {
        ColorEnum color = getCurrentMaxDedicationColor(board);
        HashMap<ColorEnum, Integer> list = validateLanternCards(tmpPlayer,
                color);
        if (list != null)
        {
            switch (color)
            {
            case RED:
                board.exchangeFourOfKind(list);
                break;
            case BLUE:
                board.exchangeThreePair(list);
                break;
            case GREEN:
                board.exchangeSevenUnique(list);
                break;
            default:
                break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        ColorEnum color = getCurrentMaxDedicationColor(board);
        HashMap<ColorEnum, Integer> list = validateLanternCards(tmpPlayer,
                color);
        board.useFavorToken(leastWantedColor, wantedColor);
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
        HashMap<ColorEnum, ArrayList<Integer>> lanternHandCards = player
                .getLanternList();
        int mostClosedCandidate = 100;
        int mostImpossibleCandidate = 0;
        int comboCnter = 0;
        switch (color)
        {
        // this means a four of kind needs to be done
        case RED:
            wantedColor = null;
            mostClosedCandidate = 100;
            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 4)
                {
                    exchangeList = new HashMap<ColorEnum, Integer>();
                    exchangeList.put(key, 0);
                    break;
                } else
                {
                    if (mostClosedCandidate > 4 - lanternHandCards.get(key)
                            .size())
                    {
                        mostClosedCandidate = 4 - lanternHandCards.get(key)
                                .size();
                        wantedColor = key;
                    }
                    if (mostImpossibleCandidate < 4 - lanternHandCards.get(key)
                            .size())
                    {
                        mostImpossibleCandidate = 4 - lanternHandCards.get(key)
                                .size();
                        leastWantedColor = key;
                    }
                }
            }
            break;
        // this means a three pair exchange needs to be done
        case BLUE:
            comboCnter = 0;
            mostClosedCandidate = 100;
            mostImpossibleCandidate = 0;
            wantedColor = null;
            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 2)
                {
                    exchangeList = new HashMap<ColorEnum, Integer>();
                    exchangeList.put(key, 0);
                    comboCnter++;
                    if (comboCnter == 3)
                    {
                        break;
                    }
                } else
                {
                    if (mostClosedCandidate > 2 - lanternHandCards.get(key)
                            .size())
                    {
                        mostClosedCandidate = 2 - lanternHandCards.get(key)
                                .size();
                        wantedColor = key;
                    }

                    if (mostImpossibleCandidate < 2 - lanternHandCards.get(key)
                            .size())
                    {
                        mostImpossibleCandidate = 2 - lanternHandCards.get(key)
                                .size();
                        leastWantedColor = key;
                    }
                }
            }
            // set list to null if not enough cards
            if (comboCnter < 3)
            {
                exchangeList = null;
            }
            break;
        // this means a seven unique needs to be done
        case GREEN:
            comboCnter = 0;
            mostClosedCandidate = 100;
            wantedColor = null;

            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 1)
                {
                    exchangeList = new HashMap<ColorEnum, Integer>();
                    exchangeList.put(key, 0);
                    comboCnter++;
                    if (lanternHandCards.get(key).size() >= 2)
                    {
                        leastWantedColor = key;
                    }
                    if (comboCnter == 7)
                    {
                        break;
                    }
                } else
                {
                    if (mostClosedCandidate > 1 - lanternHandCards.get(key)
                            .size())
                    {
                        mostClosedCandidate = 1 - lanternHandCards.get(key)
                                .size();
                        wantedColor = key;
                    }
                    if (mostImpossibleCandidate < 1 - lanternHandCards.get(key)
                            .size())
                    {
                        mostImpossibleCandidate = 1 - lanternHandCards.get(key)
                                .size();
                        leastWantedColor = key;
                    }
                }
            }
            // there are some colors not in the hashmap yet, let's find it
            if (wantedColor == null)
            {
                ArrayList<ColorEnum> targets = new ArrayList<ColorEnum>(
                        Arrays.asList(ColorEnum.values()));
                for (ColorEnum key : lanternHandCards.keySet())
                {
                    if (targets.contains(key))
                    {
                        targets.remove(key);
                    }
                }
                wantedColor = targets.get(0);
            }
            // set list to null if not enough cards
            if (comboCnter < 7)
            {
                exchangeList = null;
            }
            break;

        default:
            break;
        }
        return exchangeList;
    }

    @Override
    public String printReasoning()
    {
        return null;
    }
}
