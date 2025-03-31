package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity {

    GamePanel gp;
    public int worldx, worldy;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultx, solidAreaDefaulty;
    public boolean collisionOn = false;

    public int actionLockCounter = 0;
    Random random = new Random();
    public boolean walking = true;
    int walkingCounter = 0;

    public Entity(GamePanel gp){
        this.gp=gp;
    }

    public BufferedImage setup (String filePath){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath+".png")));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void setAction(){}

    public void update() {

        setAction();
        walkingCounter++;

        if (walkingCounter > 150) {
            walking = random.nextBoolean();
            walkingCounter = 0;
        }
        if (walking) {


            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, false);
            gp.cChecker.checkPlayer(this);


            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldy -= speed; // upper left corner is origin, so subtracting y moves the player up
                        break;
                    case "down":
                        worldy += speed;
                        break;
                    case "right":
                        worldx += speed;
                        break;
                    case "left":
                        worldx -= speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        int screenx = worldx - gp.player.worldx + gp.player.screenx;
        int screeny = worldy - gp.player.worldy + gp.player.screeny;

        BufferedImage image = null;


        if(     worldx + gp.tileSize > gp.player.worldx - gp.player.screenx &&
                worldx - gp.tileSize  < gp.player.worldx + gp.player.screenx &&
                worldy + gp.tileSize  > gp.player.worldy - gp.player.screeny &&
                worldy - gp.tileSize  < gp.player.worldy + gp.player.screeny){
            switch(direction){
                case"up":
                    if(spriteNum==1){
                        image=up1;
                    }
                    if(spriteNum==2){
                        image=up2;
                    }
                    break;
                case"down":
                    if(spriteNum==1){
                        image=down1;
                    }
                    if(spriteNum==2){
                        image=down2;
                    }
                    break;
                case"left":
                    if(spriteNum==1){
                        image=left1;
                    }
                    if(spriteNum==2){
                        image=left2;
                    }
                    break;
                case"right":
                    if(spriteNum==1){
                        image=right1;
                    }
                    if(spriteNum==2){
                        image=right2;
                    }
                    break;
            }
            g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
        }
    }

}
