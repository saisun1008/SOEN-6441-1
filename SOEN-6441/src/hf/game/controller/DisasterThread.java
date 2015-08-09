package hf.game.controller;

import java.util.Map;
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
public class DisasterThread extends Thread
{
    private LogView log;
    private boolean terminated = true;
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
        try
        {
            Thread.sleep(10000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        log.log("Disasters will start at any time now");
        run();
    }

    public void stopDisaster()
    {
        terminated = true;
    }

    public void run()
    {
        while (!terminated)
        {
            if (GameController.getInstance().getBoard().gameEnded)
            {
                terminated = true;
                break;
            }
            try
            {
                Thread.sleep(500);
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
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
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

        for (int key : map.keySet())
        {
            if (key != 221)
            {
                map.remove(key);
            }
        }

        GameController.getInstance().getBoard().manuallySetMap(map);
        log.log("Tsunami has been triggered, all lake tiles(except starting card) removed from board");
        tsunamiTiming = generator.nextInt(BOUND);
    }

    private void createPassingBoat()
    {
        int numlakeTiles = generator.nextInt(3);
        Map<Integer, Integer> map = GameController.getInstance().getBoard()
                .getMatrixLocationIndex();

        int attempts = 0;
        int tileIndex = 0;
        while (numlakeTiles > 0)
        {
            int item = new Random().nextInt(map.size()); // In real life, the
                                                         // Random object should
                                                         // be rather more
                                                         // shared than this
            int i = 0;

            for (int obj : map.keySet())
            {
                if (i == item)
                {
                    tileIndex = obj;
                }
                i = i + 1;
            }
            if (!map.containsKey(tileIndex + 21)
                    && !map.containsKey(tileIndex - 21)
                    || !map.containsKey(tileIndex + 1)
                    && !map.containsKey(tileIndex - 1))
            {
                attempts++;
                continue;
            } else
            {
                map.remove(tileIndex);
                numlakeTiles--;
            }
            if (attempts >= map.size())
            {
                break;
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
                .getBoard().getPlayerCount());
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
        while (numPlayer > 0)
        {
            for (Player p : GameController.getInstance().getBoard()
                    .getPlayers())
            {
                while (numDedication > 0)
                {
                    if (p.getLanternList().get(dedicationColor).size() > 0)
                    {
                        p.getLanternList().get(dedicationColor).remove(0);
                    }
                }
            }
            numPlayer--;
        }

        lightningTiming = generator.nextInt(BOUND);
    }
}
