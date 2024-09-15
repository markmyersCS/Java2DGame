package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Heart";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            bufferedImage2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            bufferedImage3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            bufferedImage = utilityTool.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
            bufferedImage2 = utilityTool.scaleImage(bufferedImage2, gamePanel.tileSize, gamePanel.tileSize);
            bufferedImage3 = utilityTool.scaleImage(bufferedImage3, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
