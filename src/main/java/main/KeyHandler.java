package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    GamePanel gamePanel;

    //DEBUG
    public boolean debug = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //Not used
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (gamePanel.gameState == gamePanel.playState) {
            switch (keyCode) {
                case KeyEvent.VK_W:
                    upPressed = true;
                    break;
                case KeyEvent.VK_S:
                    downPressed = true;
                    break;
                case KeyEvent.VK_A:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
                case KeyEvent.VK_P: //for pausing
                    gamePanel.gameState = gamePanel.pauseState;
                    break;
                case KeyEvent.VK_ENTER: //for pausing
                    enterPressed = true;
                    break;
                default:
                    break;
            }


            //DEBUG
            if (keyEvent.getKeyCode() == KeyEvent.VK_T) {
                if (!debug) {
                    debug = true;
                } else if (debug) {
                    debug = false;
                }
            }
        }
        //Pause State
        else if(gamePanel.gameState == gamePanel.pauseState) {
            if(keyCode == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }

        }
        //Dialogue State
        else if(gamePanel.gameState == gamePanel.dialogState) {
            if(keyCode == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            default:
                break;
        }
    }
}
