package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int[][] mapTileNumber;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tiles = new Tile[50];
        mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldV2.txt");
    }

    public void getTileImage() {

//        setupTiles(0, "grass01", false);
//        setupTiles(1, "wall", true);
//        setupTiles(2, "water01", true);
//        setupTiles(3, "earth", false);
//        setupTiles(4, "tree", true);
//        setupTiles(5, "sand", false);

        setupTiles(10, "grass00", false);
        setupTiles(11, "grass01", false);
        setupTiles(12, "water00", true);
        setupTiles(13, "water01", true);
        setupTiles(14, "water02", true);
        setupTiles(15, "water03", true);
        setupTiles(16, "water04", true);
        setupTiles(17, "water05", false);
        setupTiles(18, "water06", true);
        setupTiles(19, "water07", true);
        setupTiles(20, "water08", true);
        setupTiles(21, "water09", true);
        setupTiles(22, "water10", true);
        setupTiles(23, "water11", true);
        setupTiles(24, "water12", true);
        setupTiles(25, "water13", true);
        setupTiles(26, "road00", false);
        setupTiles(27, "road01", false);
        setupTiles(28, "road02", false);
        setupTiles(29, "road03", false);
        setupTiles(30, "road04", false);
        setupTiles(31, "road05", false);
        setupTiles(32, "road06", false);
        setupTiles(33, "road07", false);
        setupTiles(34, "road08", false);
        setupTiles(35, "road09", false);
        setupTiles(36, "road10", false);
        setupTiles(37, "road11", false);
        setupTiles(38, "road12", false);
        setupTiles(39, "earth", false);
        setupTiles(40, "wall", true);
        setupTiles(41, "tree", true);



    }

    public void setupTiles(int index, String imageName, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();
        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tiles[index].image = utilityTool.scaleImage(tiles[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tiles[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFileLocation) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapFileLocation);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String[] numbersArray = line.split(" ");
                    int num = Integer.parseInt(numbersArray[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNumber = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldXPosition + gamePanel.player.screenXPosition;
            int screenY = worldY - gamePanel.player.worldYPosition + gamePanel.player.screenYPosition;

            //Create a "boundary" and only render world inside this boundary (saves resources)
            if (worldX + gamePanel.tileSize > gamePanel.player.worldXPosition - gamePanel.player.screenXPosition &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldXPosition + gamePanel.player.screenXPosition &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldYPosition - gamePanel.player.screenYPosition &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldYPosition + gamePanel.player.screenYPosition) {

                graphics2D.drawImage(tiles[tileNumber].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
