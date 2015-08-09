package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.controller.MatrixCalculator;
import hf.game.items.Player;
import hf.ui.matrix.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GreedyStrategy implements PlayerStrategy
{

    // These attributes will not be saved
    private Player tmpPlayer;
    private ColorEnum wantedColor = null;
    private ColorEnum leastWantedColor = null;
    private ColorEnum wantedDedicationColor = null;
    private HashMap<ColorEnum, Integer> maxValues = null;

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
            placeLakeTile(board, -1);
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
                placeLakeTile(board, -1);
            }
        }
        return true;
    }

    /**
     * Place a lake tile at a random location
     * 
     * @param board
     *            currently playing board
     * @param cardIndex
     *            card index of the card to be placed, -1 means random select a
     *            card and place
     */
    private void placeLakeTile(GameBoard board, int cardIndex)
    {
        Matrix matrix = board.getMatrix();
        MatrixCalculator ca = matrix.getMatrixCalculator();
        int cardID = cardIndex == -1 ? 0 : cardIndex;
        Map<Integer, Integer> map = board.getMatrixLocationIndex();
        int rightLocation = -1;
        int randomplace = new Random().nextInt(4);
        for (int key : map.keySet())
        {
            if (!map.containsKey(key - 1) && randomplace == 0)
            {
                rightLocation = key - 1;
                break;
            } else if (!map.containsKey(key + 1) && randomplace == 1)
            {
                rightLocation = key + 1;
                break;
            } else if (!map.containsKey(key + 21) && key + 21 < 441
                    && randomplace == 2)
            {
                rightLocation = key + 21;
                break;
            } else if (!map.containsKey(key - 21) && key - 21 >= 0
                    && randomplace == 3)
            {
                rightLocation = key - 21;
                break;
            }
        }
        if (cardIndex == -1)
        {
            board.getCurrentRoundPlayer().getLakeTileList().remove(cardID);
        }
        ca.setSelectedCard(cardID);
        ca.placeLakeTile(rightLocation);
    }

    private boolean validateLakeTileLocation(int location)
    {

        boolean ret = false;
        return ret;
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
        for (int index : board.getCurrentRoundPlayer().getLakeTileList())
        {
            if (board.getLakeTileByIndex(index).hasColor(wantedColor))
            {
                board.getLakeTileByIndex(index).rotateCardToDesiredDegree(
                        board.getCurrentRoundPlayer().getSitLocation(),
                        wantedColor);
                int pos = board.getCurrentRoundPlayer().getLakeTileList()
                        .indexOf(index);
                board.getCurrentRoundPlayer().getLakeTileList().remove(pos);
                placeLakeTile(board, index);
                return true;
            }
        }
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
        } else
        {
            // now we can't get the max dedication card, let's try the second
            // max
            int secondlargest = 0;
            ColorEnum second = null;
            for (ColorEnum key : maxValues.keySet())
            {
                if (key == color)
                {
                    continue;
                } else
                {
                    if (secondlargest < board.getDedicationTokenByIndex(
                            board.getDedicationTokenDeck().get(key).get(0))
                            .getCardValue())
                    {
                        second = key;
                        secondlargest = board.getDedicationTokenByIndex(
                                board.getDedicationTokenDeck().get(key).get(0))
                                .getCardValue();

                    }
                }
            }

            HashMap<ColorEnum, Integer> list1 = validateLanternCards(tmpPlayer,
                    second);
            if (list1 != null)
            {
                switch (second)
                {
                case RED:
                    board.exchangeFourOfKind(list1);
                    break;
                case BLUE:
                    board.exchangeThreePair(list1);
                    break;
                case GREEN:
                    board.exchangeSevenUnique(list1);
                    break;
                default:
                    break;
                }
                return true;
            } else
            {
                for (ColorEnum key : maxValues.keySet())
                {
                    if (key == color || key == second || key == ColorEnum.WHITE)
                    {
                        continue;
                    } else
                    {
                        HashMap<ColorEnum, Integer> list2 = validateLanternCards(
                                tmpPlayer, key);
                        if (list2 != null)
                        {
                            switch (key)
                            {
                            case RED:
                                board.exchangeFourOfKind(list2);
                                break;
                            case BLUE:
                                board.exchangeThreePair(list2);
                                break;
                            case GREEN:
                                board.exchangeSevenUnique(list2);
                                break;
                            default:
                                break;
                            }
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    @Override
    public boolean redeemFavorToken(GameBoard board)
    {
        if (board.getCurrentRoundPlayer().getFavorTokenList().size() >= 2
                && board.getLatternDecks().get(wantedColor).size() > 0)
        {
            ColorEnum color = getCurrentMaxDedicationColor(board);
            HashMap<ColorEnum, Integer> list = validateLanternCards(tmpPlayer,
                    color);
            if (tmpPlayer.getLanternList().get(leastWantedColor).size() > 0)
            {
                board.useFavorToken(leastWantedColor, wantedColor);
            }
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
        maxValues = new HashMap<ColorEnum, Integer>();
        for (ColorEnum color : list.keySet())
        {
            if (list.get(color).size()>0 && board.getDedicationTokenCollection().size()>0)
            {
                if (board.getDedicationTokenByIndex(list.get(color).get(0))
                        .getCardValue() > maxValue )
                {
                    maxValue = board.getDedicationTokenByIndex(
                            list.get(color).get(0)).getCardValue();
                    result = color;
                }
                maxValues.put(color,
                        board.getDedicationTokenByIndex(list.get(color).get(0))
                                .getCardValue());
            }
        }
        wantedDedicationColor = result;
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
            exchangeList = new HashMap<ColorEnum, Integer>();
            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 4)
                {

                    exchangeList.put(key, 4);
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
            exchangeList = new HashMap<ColorEnum, Integer>();
            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 2)
                {

                    exchangeList.put(key, 2);
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
            exchangeList = new HashMap<ColorEnum, Integer>();
            for (ColorEnum key : lanternHandCards.keySet())
            {
                if (lanternHandCards.get(key).size() >= 1)
                {

                    exchangeList.put(key, 1);
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
                if (targets.size() > 0)
                {
                    wantedColor = targets.get(0);
                } else
                {
                    wantedColor = ColorEnum.RED;
                }
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
        String msg = "Player " + tmpPlayer.getName() + " wanted " + wantedColor
                + " lantern card, in order to get  " + wantedDedicationColor
                + " dedication card";

        return msg;
    }
}
