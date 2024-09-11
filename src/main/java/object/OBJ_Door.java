package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    GamePanel gamePanel;
    public OBJ_Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Door";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            utilityTool.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
