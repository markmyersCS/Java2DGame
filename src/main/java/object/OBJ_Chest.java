package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Chest";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            utilityTool.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
