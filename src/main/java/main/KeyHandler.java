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

        //Title State
        if (gamePanel.gameState == gamePanel.titleState) {

            if (gamePanel.ui.titleScreenState == 0) {

                switch (keyCode) {
                    case KeyEvent.VK_W:
                        gamePanel.ui.menuChoice--;
                        if (gamePanel.ui.menuChoice < 0) {
                            gamePanel.ui.menuChoice = 2;
                        }
                        break;
                    case KeyEvent.VK_S:
                        gamePanel.ui.menuChoice++;
                        if (gamePanel.ui.menuChoice > 2) {
                            gamePanel.ui.menuChoice = 0;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        //New Game
                        if (gamePanel.ui.menuChoice == 0) {
                            gamePanel.ui.titleScreenState = 1; //TODO for character creation
//                            gamePanel.gameState = gamePanel.playState;
//                        gamePanel.playGameMusic(0); //Play main music

                        }
                        //Load Game
                        if (gamePanel.ui.menuChoice == 1) {
                            //TODO ADD LATER

                        }
                        if (gamePanel.ui.menuChoice == 2) {
                            System.exit(0);
                        }
                        break;
                }
            }
            //Character Creation Screen
            else if (gamePanel.ui.titleScreenState == 1) {
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        gamePanel.ui.menuChoice--;
                        if (gamePanel.ui.menuChoice < 0) {
                            gamePanel.ui.menuChoice = 3;
                        }
                        break;
                    case KeyEvent.VK_S:
                        gamePanel.ui.menuChoice++;
                        if (gamePanel.ui.menuChoice > 3) {
                            gamePanel.ui.menuChoice = 0;
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        if (gamePanel.ui.menuChoice == 0) {
                            System.out.println("SELECTED FIGHTER");
                            //TODO ADD THINGS HERE AS NEEDED
                            gamePanel.gameState = gamePanel.playState;
                        }
                        //Load Game
                        if (gamePanel.ui.menuChoice == 1) {
                            System.out.println("SELECTED MAGE");
                            gamePanel.gameState = gamePanel.playState;
                        }
                        if (gamePanel.ui.menuChoice == 2) {
                            System.out.println("SELECTED THIEF");
                            gamePanel.gameState = gamePanel.playState;
                        }
                        if (gamePanel.ui.menuChoice == 3) {
                            gamePanel.ui.titleScreenState = 0;
                            break;
                        }
                }
            }
        }

        //Play State
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
        else if (gamePanel.gameState == gamePanel.pauseState) {
            if (keyCode == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }

        }
        //Dialogue State
        else if (gamePanel.gameState == gamePanel.dialogState) {
            if (keyCode == KeyEvent.VK_ENTER) {
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
