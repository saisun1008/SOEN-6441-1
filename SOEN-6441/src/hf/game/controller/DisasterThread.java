package hf.game.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import hf.game.common.ColorEnum;
import hf.game.items.Player;
import hf.game.views.LogView;

/**
 * A thread to control random happened disasters
 * 
 * @author Sai
 *
 */
public class DisasterThread implements Runnable
{
    private LogView log;
    private boolean terminated = true;
    private boolean paused = true;
    private int tsunamiTiming = 0;
    private int passingBoartTiming = 0;
    private int lightningTiming = 0;
    private Random generator;
    private final int BOUND = 500;
    private int timer = 0;

    public DisasterThread(LogView log)
    {
        this.log = log;
        generator = new Random();
        tsunamiTiming = generator.nextInt(BOUND);
        passingBoartTiming = generator.nextInt(BOUND);
        lightningTiming = generator.nextInt(BOUND);
    }

    public void start()
    {
        terminated = false;
        paused = false;
        log.log("Disasters will start at any time now");
    }

    public void stopDisaster()
    {
        terminated = true;
    }

    public void restart()
    {
        paused = true;
        try
        {
            log.log("Disasters will commence in 10 seconds:");
            int counter = 9;
            for (; counter >= 0; counter--)
            {
                Thread.sleep(1000);
                log.log("Disasters will start at " + counter + " seconds");
            }

        } catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        paused = false;
    }

    public void run()
    {
        try
        {
            try
            {
                log.log("Disasters will commence in 10 seconds:");
                int counter = 9;
                for (; counter >= 0; counter--)
                {
                    Thread.sleep(1000);
                    log.log("Disasters will start at " + counter + " seconds");
                }

            } catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
            while (!terminated)
            {
                if (!paused)
                {
                    if (GameController.getInstance().getBoard().gameEnded)
                    {
                        terminated = true;
                        break;
                    }

                    Thread.sleep(10);
                    timer++;
                    if (timer == tsunamiTiming)
                    {
                        createTsunami();
                    }
                    if (timer == passingBoartTiming)
                    {
                        createPassingBoat();
                    }
                    if (timer == lightningTiming)
                    {
                        createLightning();
                    }
                    if (timer > BOUND)
                    {
                        timer = 0;
                    }

                }
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Remove all lake tile cards except the starting tile from the board and
     * re-generate the timing
     */
    private void createTsunami()
    {
        Map<Integer, Integer> map = GameController.getInstance().getBoard()
                .getMatrixLocationIndex();

        Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
        while (it.hasNext())
        {
            Entry<Integer, Integer> item = it.next();
            if (item.getKey() != 221)
            {
                it.remove();
            }
        }

        GameController.getInstance().getBoard().manuallySetMap(map);
        log.log("Tsunami has been triggered, all lake tiles(except starting card) removed from board");
        tsunamiTiming = generator.nextInt(BOUND);
    }

    private void createPassingBoat()
    {
        int numlakeTiles = generator.nextInt(10);
        Map<Integer, Integer> map = GameController.getInstance().getBoard()
                .getMatrixLocationIndex();
        log.log("Passing boat has been triggered: " + numlakeTiles
                + " will be removed: ");
        int attempts = 0;
        int tileIndex = 0;
        Integer[] arr = new Integer[map.keySet().size()];
        while (numlakeTiles > 0)
        {
            if (attempts >= map.size())
            {
                break;
            }
            arr = map.keySet().toArray(arr);
            tileIndex = arr[generator.nextInt(arr.length)];

            if (!map.containsKey(tileIndex + 21)
                    && !map.containsKey(tileIndex - 21)
                    || !map.containsKey(tileIndex + 1)
                    && !map.containsKey(tileIndex - 1) || tileIndex == 221)
            {
                attempts++;
                continue;
            } else
            {
                map.remove(tileIndex);
                log.log("Passing boat now removing lake tile which has index of  "
                        + tileIndex);
                numlakeTiles--;
            }
        }

        GameController.getInstance().getBoard().manuallySetMap(map);
        passingBoartTiming = generator.nextInt(BOUND);
    }

    /**
     * Create lightning, randomly select number of affected players, dedication
     * card color and number of cards to be removed. And re-generate the timing
     * for next such event
     */
    private void createLightning()
    {
        int numPlayer = generator.nextInt(GameController.getInstance()
                .getBoard().getPlayerCount()) + 1;
        int numDedication = generator.nextInt(6);

        int tmp = generator.nextInt(3);
        ColorEnum dedicationColor = tmp == 0 ? ColorEnum.RED
                : tmp == 1 ? ColorEnum.BLUE : ColorEnum.GREEN;

        log.log("Lightning has been triggered: "
                + numPlayer
                + " player(s) will be affected, "
                + numDedication
                + " "
                + dedicationColor
                + " dedication card(s) will be removed from the affected players");
        numDedication = numDedication * numPlayer;
        while (numPlayer > 0)
        {
            for (Player p : GameController.getInstance().getBoard()
                    .getPlayers())
            {
                while (numDedication > 0)
                {
                    if (p.getDedicationTokenList().get(dedicationColor) == null)
                    {
                        break;
                    }
                    if (p.getDedicationTokenList().size() != 0
                            && p.getDedicationTokenList().get(dedicationColor)
                                    .size() > 0)
                    {
                        p.getDedicationTokenList().get(dedicationColor)
                                .remove(0);

                    }
                    numDedication--;
                }
            }
            numPlayer--;
        }

        lightningTiming = generator.nextInt(BOUND);
    }
}
