package hf.game.strategy;

import hf.game.GameBoard;

/**
 * This class is the interface for player strategies.
 * 
 * @author sai
 *
 */
public interface PlayerStrategy
{
    /**
     * Place a lake tile on the board, this function will directly modify the
     * board state.
     * 
     * @param board
     *            board that currently is played on
     * @return true if the lake tile is placed successfully
     */
    public boolean placeLakeTile(GameBoard board);

    /**
     * Redeem lantern card to dedication tokens based on the strategy
     * 
     * @param board
     *            board that currently is played on
     * @return true if no exception is encountered, however the return value
     *         doesn't mean a redeem has been done, the redeem might have failed
     *         due to insufficient card
     */
    public boolean redeemLanternCard(GameBoard board);

    /**
     * Redeem Favor token to perform an exchange of lantern cards
     * 
     * @param board
     *            board that currently is played on
     * @return true if no exception is encountered, however the return value
     *         doesn't mean a redeem has been done, the redeem might have failed
     *         due to insufficient card
     */
    public boolean redeemFavorToken(GameBoard board);
}
