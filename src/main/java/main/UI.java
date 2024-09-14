package main;

import object.OBJ_Key;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Graphics2D graphics2D;
    final Font arial_20;
    final Font arial_80B;
    public boolean messageOn = false;
    public String message = "";
    int messageDisplayTime = 0;
    public boolean gameComplete = false;
    public String currentDialogue = "";

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(arial_20);
        graphics2D.setColor(Color.WHITE);

        if (gamePanel.gameState == gamePanel.playState) {
            //Do play state stuff
        }
        if (gamePanel.gameState == gamePanel.pauseState) {
            //Display pause screen
            drawPauseScreen();
        }
        if (gamePanel.gameState == gamePanel.dialogState) {
            drawDialogueScreen();
        }
    }

    public void drawDialogueScreen() {
        //Create window on screen
        int windowX = gamePanel.tileSize * 2; //two tiles from right edge
        int windowY = gamePanel.tileSize / 2; //half tile form top
        int windowWidth = gamePanel.screenWidth - (gamePanel.tileSize * 4); //4 tiles wide
        int windowHeight = (gamePanel.tileSize * 4); //5 tiles high

        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

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
