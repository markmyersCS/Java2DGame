package main;

import object.OBJ_Key;

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
    //    BufferedImage keyIcon;
    public boolean messageOn = false;
    public String message = "";
    int messageDisplayTime = 0;
    public boolean gameComplete = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);
//        OBJ_Key key = new OBJ_Key(gamePanel);
//        keyIcon = key.bufferedImage;
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
        return  (gamePanel.screenWidth / 2) - (length / 2);
    }
}
