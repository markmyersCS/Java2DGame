package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//TODO NEED TO USE GIMP to create own images/assets
public class SuperObject {
    public BufferedImage bufferedImage;
    public String name;
    public boolean collision = false;
    public int worldXPosition;
    public int worldYPosition;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    UtilityTool utilityTool = new UtilityTool();

    /**
     * Draws {@link SuperObject} derived objects into the game
     *
     * @param graphics2D
     *         {@link Graphics2D} library for drawing in objects
     * @param gamePanel
     *         Our {@link GamePanel} for placing objects into the world
     */
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {

        int screenX = worldXPosition - gamePanel.player.worldXPosition + gamePanel.player.screenXPosition;
        int screenY = worldYPosition - gamePanel.player.worldYPosition + gamePanel.player.screenYPosition;

        //Create a "boundary" and only render world inside this boundary (saves resources)
        if (worldXPosition + gamePanel.tileSize > gamePanel.player.worldXPosition - gamePanel.player.screenXPosition &&
                worldXPosition - gamePanel.tileSize < gamePanel.player.worldXPosition + gamePanel.player.screenXPosition &&
                worldYPosition + gamePanel.tileSize > gamePanel.player.worldYPosition - gamePanel.player.screenYPosition &&
                worldYPosition - gamePanel.tileSize < gamePanel.player.worldYPosition + gamePanel.player.screenYPosition) {

            graphics2D.drawImage(bufferedImage, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,
                    null);
        }

    }

}
