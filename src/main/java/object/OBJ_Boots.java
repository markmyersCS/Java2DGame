package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Boots";
        try {
            bufferedImage = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            utilityTool.scaleImage(bufferedImage, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
