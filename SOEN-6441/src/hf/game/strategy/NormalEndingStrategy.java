package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.items.Player;

public class NormalEndingStrategy implements GameEndingStrategy
{

    private Player winner = null;

    @Override
    public boolean validateGameEndingCondition(GameBoard board)
    {
        int lakeTileCnt = 0;
        int maxScore = 0;
        int winner = 0;
        String ret = "";
        for (Player player : board.getPlayers())
        {
            lakeTileCnt = lakeTileCnt + player.getLakeTileList().size();
            int score = 0;
            for (ColorEnum index : player.getDedicationTokenList().keySet())
            {
                for (int i : player.getDedicationTokenList().get(index))
                {
                    score += board.getDedicationTokenByIndex(i).getCardValue();
                }

            }
            if (maxScore < score)
            {
                winner = board.getPlayers().indexOf(player);
                maxScore = score;
            }
            player.setScore(score);
            ret = ret.concat(board.getPlayers()
                    .get(board.getPlayers().indexOf(player)).getName()
                    + "score:" + score + "\n ");
        }

        lakeTileCnt += board.getLakeTileDeck().size();

        if (lakeTileCnt == 0)
        {
            // System.out.println(m_players.get(winner).getName() +
            // " has won!!");
            this.winner = board.getPlayers().get(winner);
            return true;
        }

        ret = ret.concat(board.getPlayers().get(winner).getName()
                + " has won!!");
        return false;
    }

    @Override
    public String printoutWinner()
    {
        if (winner != null)
        {
            return winner.getName() + " has won!! with Score of "
                    + winner.getScore();
        } else
        {
            return "";
        }
    }
}
