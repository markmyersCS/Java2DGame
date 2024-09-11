package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;

        getOldManImage();
    }

    public void getOldManImage() {

        up1 = setUpEntityImage("/npc/oldman_up_1");
        up2 = setUpEntityImage("/npc/oldman_up_2");
        down1 = setUpEntityImage("/npc/oldman_down_1");
        down2 = setUpEntityImage("/npc/oldman_down_2");
        left1 = setUpEntityImage("/npc/oldman_left_1");
        left2 = setUpEntityImage("/npc/oldman_left_2");
        right1 = setUpEntityImage("/npc/oldman_right_1");
        right2 = setUpEntityImage("/npc/oldman_right_2");
    }

    /**
     * Used for AI of OLD MAN
     */
    @Override
    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) { //only periodically want to update NPC OLD MAN position

            //Simple "AI"
            Random random = new Random();
            int i = random.nextInt(100) + 1; //get a random from 1-100, +1 avoids 0 and 99;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0; //reset action counter
        }

    }


}
