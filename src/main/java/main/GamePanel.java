package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import object.SuperObject;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings
    final int originalTitleSize = 16; //16x16 tiles
    final int scale = 3; //Scale to fix too small sprites/icons
    public final int tileSize = originalTitleSize * scale; //this will scale sprites/icons
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels width
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels height

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    //System
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound gameMusic = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    public boolean musicPlaying = false;

    //Entity and Objects
    public Player player = new Player(this, keyHandler);
    public SuperObject[] object = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    //Game State
    public int gameState; //are we paused, playing, in a menu, etc.?
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
//        playGameMusic(0);//Play main game music//TODO UNCOMMENT FOR MUSIC
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Run method for our thread
     */
    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS; // 0.1666 seconds
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;
        //for FPS display
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (long) (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                //1 UPDATE: update information such as character position
                update();
                //2 DRAW: draw screen with the updated information
                repaint();//calls paint component method (just java things)
                delta--;
                drawCount++;

                if (timer >= 1000000000) {
                    drawCount = 0;
                    timer = 0;
                }
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            //NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
        }
        if (gameState == pauseState) {
            //nothing for now
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        //DEBUG
        long drawStart = 0;
        if (keyHandler.debug) {
            drawStart = System.nanoTime();
        }

        //Tile
        tileManager.draw(graphics2D); //need to draw tiles first because of layering
        //Object
        for (SuperObject superObject : object) {
            if (superObject != null) {
                superObject.draw(graphics2D, this);
            }
        }

        //NPC
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(graphics2D);
            }
        }

        //Player
        player.draw(graphics2D);

        //UI
        ui.draw(graphics2D);

        //DEBUG
        if (keyHandler.debug) {
            long drawEnd = System.nanoTime();
            long passedTime = drawEnd - drawStart;
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("DrawTime: " + passedTime, 10, 400);
            System.out.println("Draw Time: " + passedTime);
        }

        graphics2D.dispose(); //release system resources
    }

    public void playGameMusic(int index) {
        gameMusic.setFile(index);
        gameMusic.play();
        musicPlaying = true;
        gameMusic.loop();
    }

    public void stopGameMusic() {
        gameMusic.stop();
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
