package hf.game.common;

/**
 * This the the color class should be used by all the game components
 * 
 * @author sai sun
 *
 */
public enum ColorEnum
{
    ORANGE(GameProperties.ORANGE_LANTERN), GREEN(GameProperties.GREEN_LANTERN), PURPLE(
            GameProperties.PURPLE_LANTERN), WHITE(GameProperties.WHITE_LANTERN), BLUE(
            GameProperties.BLUE_LANTERN), RED(GameProperties.RED_LANTERN), BLACK(
            GameProperties.BLACK_LANTERN);

    private String imagePath;

    private ColorEnum(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getImagePath()
    {
        return this.imagePath;
    }
}
