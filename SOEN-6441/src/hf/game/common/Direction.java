package hf.game.common;

public enum Direction
{
    TOP(0), LEFT(1), BOTTOM(2), RIGHT(3);

    int index = 0;

    private Direction(int directionIndex)
    {
        index = directionIndex;
    }
}
