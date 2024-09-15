package main;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    //    final Font arial_20;
//    final Font arial_80B;
    Font gameFont;
    BufferedImage heart_full;
    BufferedImage heart_half;
    BufferedImage heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageDisplayTime = 0;
    public boolean gameComplete = false;
    public String currentDialogue = "";
    public int menuChoice = 0;
    public int titleScreenState = 0; // 0: main title screen, 1: character creator//TODO Character Creator

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream fontFile = getClass().getResourceAsStream("/fonts/ARCADECLASSIC.TTF");
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        arial_20 = new Font("Arial", Font.PLAIN, 20);
//        arial_80B = new Font("Arial", Font.BOLD, 80);

        //Create HUD
        SuperObject heart = new OBJ_Heart(gamePanel);
        heart_full = heart.bufferedImage;
        heart_half = heart.bufferedImage2;
        heart_blank = heart.bufferedImage3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(gameFont);
        graphics2D.setColor(Color.WHITE);

        //Title State
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }
        //Game State
        if (gamePanel.gameState == gamePanel.playState) {
            //Do play state stuff
            drawPlayerLife();
        }
        //Pause State
        if (gamePanel.gameState == gamePanel.pauseState) {
            //Display pause screen
            drawPlayerLife();
            drawPauseScreen();
        }
        //Dialogue State
        if (gamePanel.gameState == gamePanel.dialogState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    public void drawPlayerLife() {
        //Draw Max Life
        int xPos = gamePanel.tileSize / 2;
        int yPos = gamePanel.tileSize / 2;
        int i = 0;
        while (i < gamePanel.player.maxHealth / 2) {
            graphics2D.drawImage(heart_blank, xPos, yPos, null);
            i++;
            xPos += gamePanel.tileSize;
        }
        //Current Life (like coloring blank hearts)
        xPos = gamePanel.tileSize / 2;
        yPos = gamePanel.tileSize / 2;
        i = 0;
        while (i < gamePanel.player.health) {
            graphics2D.drawImage(heart_half, xPos, yPos, null);
            i++;
            if (i < gamePanel.player.health) {
                graphics2D.drawImage(heart_full, xPos, yPos, null);
            }
            i++;
            xPos += gamePanel.tileSize;
        }
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            //Background Color
            graphics2D.setColor(new Color(0, 0, 0));
            graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            //Title Name
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
            String title = "Java 2D Game";
            int xPos = getXForCenteredText(title);
            int yPos = gamePanel.tileSize * 3;

            //Shadow
            graphics2D.setColor(Color.GRAY);
            graphics2D.drawString(title, xPos + 5, yPos + 5); //Slightly offset text and redraw

            //Title Text Color
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(title, xPos, yPos);

            //Character Image
            xPos = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
            yPos += gamePanel.tileSize * 2; //offset by two tiles.
            graphics2D.drawImage(gamePanel.player.down1, xPos, yPos,
                    gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            //MENU
            graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));
            String menu = "New Game";
            xPos = getXForCenteredText(menu);
            yPos += gamePanel.tileSize * 4;
            graphics2D.drawString(menu, xPos, yPos);
            if (menuChoice == 0) {
                //Use "DRAW IMAGE" to use image instead of ">"
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
            menu = "Load Game";
            xPos = getXForCenteredText(menu);
            yPos += gamePanel.tileSize;
            graphics2D.drawString(menu, xPos, yPos);
            if (menuChoice == 1) {
                //Use "DRAW IMAGE" to use image instead of ">"
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
            menu = "Quit";
            xPos = getXForCenteredText(menu);
            yPos += gamePanel.tileSize;
            graphics2D.drawString(menu, xPos, yPos);
            if (menuChoice == 2) {
                //Use "DRAW IMAGE" to use image instead of ">"
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
        } else if (titleScreenState == 1) {
            //TODO CHARACTER CREATION
            //Class selection screen
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(graphics2D.getFont().deriveFont(42F));
            String creationText = "Select your class!";
            int xPos = getXForCenteredText(creationText);
            int yPos = gamePanel.tileSize * 3;
            graphics2D.drawString(creationText, xPos, yPos);

            creationText = "Fighter";
            xPos = getXForCenteredText(creationText);
            yPos += gamePanel.tileSize * 3;
            graphics2D.drawString(creationText, xPos, yPos);
            if (menuChoice == 0) {
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
            creationText = "Mage";
            xPos = getXForCenteredText(creationText);
            yPos += gamePanel.tileSize;
            graphics2D.drawString(creationText, xPos, yPos);
            if (menuChoice == 1) {
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
            creationText = "Theif";
            xPos = getXForCenteredText(creationText);
            yPos += gamePanel.tileSize;
            graphics2D.drawString(creationText, xPos, yPos);
            if (menuChoice == 2) {
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
            creationText = "Back";
            xPos = getXForCenteredText(creationText);
            yPos += gamePanel.tileSize * 2;
            graphics2D.drawString(creationText, xPos, yPos);
            if (menuChoice == 3) {
                graphics2D.drawString(">", xPos - gamePanel.tileSize, yPos);
            }
        }
    }

    public void drawDialogueScreen() {
        //Create window on screen
        int windowX = gamePanel.tileSize * 2; //two tiles from right edge
        int windowY = gamePanel.tileSize / 2; //half tile form top
        int windowWidth = gamePanel.screenWidth - (gamePanel.tileSize * 4); //4 tiles wide
        int windowHeight = (gamePanel.tileSize * 4); //5 tiles high

        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 24F));
        //Where to draw text
        windowX += gamePanel.tileSize;
        windowY += gamePanel.tileSize;
        //Split on "\n" for we can do new lines //TODO may want to make this automatic based on length
        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, windowX, windowY);
            windowY += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200); //create black color (4th is transparency)
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = new Color(255, 255, 255);
        graphics2D.setColor(color);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public void drawPauseScreen() {
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    private int getXForCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return (gamePanel.screenWidth / 2) - (length / 2);
    }
}
