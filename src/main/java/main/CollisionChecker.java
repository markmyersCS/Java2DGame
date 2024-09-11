package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Checks rather a collision is occurring (between {@link  entity.Player} and {@link Entity})
     *
     * @param entity
     *         the entity we are checking collisions
     */
    public void checkTile(Entity entity) {
        //Need to check: leftX, rightX, topY, bottomY
        int entityLeftWorldX = entity.worldXPosition + entity.solidArea.x;
        int entityRightWorldX = entity.worldXPosition + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldYPosition + entity.solidArea.y;
        int entityBottomWorldY = entity.worldYPosition + entity.solidArea.y + entity.solidArea.height;

        int entityLeftColumn = entityLeftWorldX / gamePanel.tileSize;
        int entityRightColumn = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1;
        int tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightColumn][entityTopRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision) {
                    //player is hitting solid tile
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftColumn][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightColumn][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision) {
                    //player is hitting solid tile
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftColumn = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityLeftColumn][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision) {
                    //player is hitting solid tile
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightColumn = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityRightColumn][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightColumn][entityBottomRow];
                if (gamePanel.tileManager.tiles[tileNum1].collision ||
                        gamePanel.tileManager.tiles[tileNum2].collision) {
                    //player is hitting solid tile
                    entity.collisionOn = true;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Checks collisions with objects
     *
     * @param entity
     *         the {@link Entity} being checked for collisions
     * @param player
     *         boolean on rather or not the object is the player
     * @return the index
     */
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.object.length; i++) {
            if (gamePanel.object[i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldXPosition + entity.solidArea.x;
                entity.solidArea.y = entity.worldYPosition + entity.solidArea.y;
                //Get object's solid area position
                gamePanel.object[i].solidArea.x = gamePanel.object[i].worldXPosition + gamePanel.object[i].solidArea.x;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].worldYPosition + gamePanel.object[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision == true) { //solid object
                                entity.collisionOn = true;
                            }
                            if (player) { //get index
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision == true) { //solid object
                                entity.collisionOn = true;
                            }
                            if (player) { //get index
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision == true) { //solid object
                                entity.collisionOn = true;
                            }
                            if (player) { //get index
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision == true) { //solid object
                                entity.collisionOn = true;
                            }
                            if (player) { //get index
                                index = i;
                            }
                        }
                        break;
                    default:
                        break;
                }
                //reset solid area or they increase every time
                entity.solidArea.x = entity.solidAreaDefaultXPosition;
                entity.solidArea.y = entity.solidAreaDefaultYPosition;
                gamePanel.object[i].solidArea.x = gamePanel.object[i].solidAreaDefaultX;//TODO look into this, is should be? same as above
                gamePanel.object[i].solidArea.y = gamePanel.object[i].solidAreaDefaultY;//TODO look into this, is should be? same as above
            }
        }

        return index;
    }

    /**
     * Checks NPC OR MONSTER collision
     *
     * @param entity
     *         the current {@link Entity}
     * @param target
     *         the {@link Entity} to check collision
     * @return int representing
     */
    public int checkEntityCollision(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldXPosition + entity.solidArea.x;
                entity.solidArea.y = entity.worldYPosition + entity.solidArea.y;
                //Get object's solid area position
                target[i].solidArea.x = target[i].worldXPosition + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldYPosition + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    default:
                        break;
                }
                //reset solid area or they increase every time
                entity.solidArea.x = entity.solidAreaDefaultXPosition;
                entity.solidArea.y = entity.solidAreaDefaultYPosition;
                target[i].solidArea.x = target[i].solidAreaDefaultXPosition;
                target[i].solidArea.y = target[i].solidAreaDefaultYPosition;
            }
        }

        return index;
    }

    public void checkPlayerCollision(Entity entity) {
        //Get entity's solid area position
        entity.solidArea.x = entity.worldXPosition + entity.solidArea.x;
        entity.solidArea.y = entity.worldYPosition + entity.solidArea.y;
        //Get object's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldXPosition + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldYPosition + gamePanel.player.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            default:
                break;
        }
        //reset solid area or they increase every time
        entity.solidArea.x = entity.solidAreaDefaultXPosition;
        entity.solidArea.y = entity.solidAreaDefaultYPosition;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultXPosition;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultYPosition;
    }
}
