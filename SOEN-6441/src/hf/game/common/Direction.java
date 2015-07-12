package hf.game.common;

/**
 * Direction Enum
 * 
 * @author Sai
 *
 */
public enum Direction
{
    TOP(0), LEFT(1), BOTTOM(2), RIGHT(3);

    int index = 0;

    private Direction(int directionIndex)
    {
        index = directionIndex;
    }
}
