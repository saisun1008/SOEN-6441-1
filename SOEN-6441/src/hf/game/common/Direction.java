package hf.game.common;

/**
 * Direction Enum, also used by card to check which place it belongs to:
 * TOP,LEFT,BOTTOM,RIGHT means it belongs to a player, CENTER means it belongs
 * to the deck
 * 
 * @author Sai
 *
 */
public enum Direction
{
    TOP(0), LEFT(3), BOTTOM(2), RIGHT(1), CENTER(4);

    int index = 0;

    private Direction(int directionIndex)
    {
        index = directionIndex;
    }
}
