package hf.game.strategy;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.items.Player;

public class NLakeTileEndingStrategy implements GameEndingStrategy
{
    private Player winner = null;
    private int Nlaketile = 0;
    
    public NLakeTileEndingStrategy(int nValue)
    {
        Nlaketile = nValue;
    }

    @Override
    public boolean validateGameEndingCondition(GameBoard board)
    {
        boolean gameend = false;
        int lakeTileCnt = 0;
        int maxScore = 0;
        int winner = 0;
        int rounds = 0;
        String ret = "";
        int playernum = board.getPlayerCount();
        for (Player player : board.getPlayers())
        {
            rounds += player.getRounds();
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
        if(rounds >= playernum*Nlaketile)
            gameend = true;
        
/*
        lakeTileCnt += board.getLakeTileDeck().size();

        switch(playernum)
        {
        case 2:
            if(lakeTileCnt <= 22-2*Nlaketile)
                gameend = true;
            break;
            
        case 3:
            if(lakeTileCnt <= 27-3*Nlaketile)
                gameend = true;
            break;
            
        case 4:
            if(lakeTileCnt <= 32-4*Nlaketile)
                gameend = true;
            break;
        }
*/        
        if (gameend)
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
