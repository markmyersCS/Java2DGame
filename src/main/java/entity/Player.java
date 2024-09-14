package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

//Can use: https://www.piskelapp.com/p/create/sprite to create sprites
public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenXPosition;
    public final int screenYPosition;
    int standingCounter = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;

        //Scrolls screen with player
        screenXPosition = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenYPosition = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

        //Set solid player area (x, y, width, height)
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultXPosition = (int) solidArea.getX();
        solidAreaDefaultYPosition = (int) solidArea.getY();

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        //Starting position
        worldXPosition = gamePanel.tileSize * 23;
        worldYPosition = gamePanel.tileSize * 21;

        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = setUpEntityImage("/player/boy_up_1");
        up2 = setUpEntityImage("/player/boy_up_2");
        down1 = setUpEntityImage("/player/boy_down_1");
        down2 = setUpEntityImage("/player/boy_down_2");
        left1 = setUpEntityImage("/player/boy_left_1");
        left2 = setUpEntityImage("/player/boy_left_2");
        right1 = setUpEntityImage("/player/boy_right_1");
        right2 = setUpEntityImage("/player/boy_right_2");
    }


    public void update() {
        //only update image (sprite counter) if keys are pressed
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            //Check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);//Pass this entity

            //Check object collision (pass in current object and since player use true)
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            //Check NPC collision
            int npcIndex = gamePanel.collisionChecker.checkEntityCollision(this, gamePanel.npc);
            interactNPC(npcIndex);

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
        } else { //change back to default sprite
            standingCounter++;
            //Slow down this change to default sprite
            if (standingCounter == 20) {
                spriteNumber = 1;
                standingCounter = 0;
            }
        }

    }

    public void pickUpObject(int index) {
        //if it is 999 (arbitrary number, cannot be used by obj array index), we did not touch any objects.
        if (index != 999) {

        }
    }

    public void interactNPC(int index) {
        if (index != 999) {
            if (gamePanel.keyHandler.enterPressed) {
                //If we hit NPC, change state
                gamePanel.gameState = gamePanel.dialogState;
                gamePanel.npc[index].speak();
            }
        }
        gamePanel.keyHandler.enterPressed = false;
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage bufferedImage = null;
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
        graphics2D.drawImage(bufferedImage, screenXPosition, screenYPosition, null);

        //Debugging, for player collision
        if (keyHandler.debug) {
            graphics2D.setColor(Color.RED);
            graphics2D.drawRect(screenXPosition + solidArea.x, screenYPosition + solidArea.y,
                    solidArea.width, solidArea.height);
        }
    }
}
