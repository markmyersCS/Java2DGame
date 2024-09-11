package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.SuperObject;

/**
 * Places {@link SuperObject} derived objects in specified locations
 */
public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() { }

    public void setNPC() {
        gamePanel.npc[0] = new NPC_OldMan(gamePanel);
        gamePanel.npc[0].worldXPosition = gamePanel.tileSize*21;
        gamePanel.npc[0].worldYPosition = gamePanel.tileSize*21;

    }
}
