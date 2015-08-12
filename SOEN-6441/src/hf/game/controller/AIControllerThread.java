package hf.game.controller;

import hf.game.common.PlayerTypeEnum;
import hf.game.views.LogView;

/**
 * Thread to control AI players
 * 
 * @author Sai
 *
 */
public class AIControllerThread implements Runnable
{
    private boolean paused = false;
    private LogView log;

    public AIControllerThread(LogView log)
    {
        this.log = log;
    }

    public void pause()
    {
        paused = true;
    }

    public void resume()
    {
        paused = false;
    }

    public void restart()
    {
        paused = true;
        try
        {
            log.log("AI Playing will commence in 10 seconds:");
            int counter = 9;
            for (; counter >= 0; counter--)
            {
                Thread.sleep(1000);
                log.log("AI will start playing at " + counter + " seconds");
            }

        } catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        paused = false;
    }

    @Override
    public void run()
    {
        try
        {
            log.log("AI Playing will commence in 10 seconds:");
            int counter = 9;
            for (; counter >= 0; counter--)
            {
                Thread.sleep(1000);
                log.log("AI will start playing at " + counter + " seconds");
            }

        } catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        while (!GameController.getInstance().getBoard().gameEnded)
        {
            if (!paused)
            {
                if (GameController.getInstance().getBoard()
                        .getCurrentRoundPlayer().getPlayerType() == PlayerTypeEnum.AI)
                {
                    String msg;
                    GameController
                            .getInstance()
                            .getBoard()
                            .getCurrentRoundPlayer()
                            .getStrategy()
                            .redeemLanternCard(
                                    GameController.getInstance().getBoard());
                    GameController
                            .getInstance()
                            .getBoard()
                            .getCurrentRoundPlayer()
                            .getStrategy()
                            .redeemFavorToken(
                                    GameController.getInstance().getBoard());
                    GameController
                            .getInstance()
                            .getBoard()
                            .getCurrentRoundPlayer()
                            .getStrategy()
                            .redeemLanternCard(
                                    GameController.getInstance().getBoard());
                    msg = GameController.getInstance().getBoard()
                            .getCurrentRoundPlayer().getStrategy()
                            .printReasoning();
                    GameController
                            .getInstance()
                            .getBoard()
                            .getCurrentRoundPlayer()
                            .getStrategy()
                            .placeLakeTile(
                                    GameController.getInstance().getBoard());
                    log.log(msg);

                }
                // after each AI player, let's wait a little bit...
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
