package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

//Can use: https://www.piskelapp.com/p/create/sprite to create sprites
public class Entity {

    GamePanel gamePanel;
    public int worldXPosition;
    public int worldYPosition;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //can override in specific classes
    public int solidAreaDefaultXPosition;
    public int solidAreaDefaultYPosition;
    public boolean collisionOn = false;
    public int actionLockCounter = 0; //counter for adding time between action updates
    ArrayList<String> dialogues = new ArrayList<>();
    int dialogueIndex = 0;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public BufferedImage setUpEntityImage(String imagePath) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            bufferedImage = utilityTool.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * Draws the entity into world, and swaps sprite based on direction
     *
     * @param graphics2D
     *         the {@link Graphics2D} library
     */
    public void draw(Graphics2D graphics2D) {

        BufferedImage bufferedImage = null;

        int screenX = worldXPosition - gamePanel.player.worldXPosition + gamePanel.player.screenXPosition;
        int screenY = worldYPosition - gamePanel.player.worldYPosition + gamePanel.player.screenYPosition;

        //Create a "boundary" and only render world inside this boundary (saves resources)
        if (worldXPosition + gamePanel.tileSize > gamePanel.player.worldXPosition - gamePanel.player.screenXPosition &&
                worldXPosition - gamePanel.tileSize < gamePanel.player.worldXPosition + gamePanel.player.screenXPosition &&
                worldYPosition + gamePanel.tileSize > gamePanel.player.worldYPosition - gamePanel.player.screenYPosition &&
                worldYPosition - gamePanel.tileSize < gamePanel.player.worldYPosition + gamePanel.player.screenYPosition) {

            switch (direction) {
                case "up":
                    if (spriteNumber == 1) {
                        bufferedImage = up1;
                    }
                    if (spriteNumber == 2) {
                        bufferedImage = up2;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        bufferedImage = down1;
                    }
                    if (spriteNumber == 2) {
                        bufferedImage = down2;
                    }

                    break;
                case "left":
                    if (spriteNumber == 1) {
                        bufferedImage = left1;
                    }
                    if (spriteNumber == 2) {
                        bufferedImage = left2;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        bufferedImage = right1;
                    }
                    if (spriteNumber == 2) {
                        bufferedImage = right2;
                    }
                    break;
                default:
                    break;
            }


            graphics2D.drawImage(bufferedImage, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,
                    null);
        }
    }

    /**
     * Used for AI of entities
     */
    public void setAction() {
    }

    public void speak() {
        if (dialogues != null && dialogues.size() <= dialogueIndex) {
            dialogueIndex = 0;
        }
        if (dialogues != null) {
            gamePanel.ui.currentDialogue = dialogues.get(dialogueIndex);
            dialogueIndex++;
        }
        //While speaking, have npc face character.
        switch (gamePanel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    /**
     * Updates entity position/direction
     */
    public void update() {
        setAction();//uses subclass implementation (it is priority)
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkPlayerCollision(this);

        //If collision is false, player moves
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldYPosition -= speed;
                    break;
                case "down":
                    worldYPosition += speed;
                    break;
                case "left":
                    worldXPosition -= speed;
                    break;
                case "right":
                    worldXPosition += speed;
                    break;
                default:
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

    }
}
