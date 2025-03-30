package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenx;
    public final int screeny;

    public int hasKey = 0;

    public int walkAnimSpeed = 12;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;



        screenx = gp.screenWidth/2 - (gp.tileSize/2);
        screeny = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(14,25,20,20);
        solidAreaDefaultx = solidArea.x;
        solidAreaDefaulty = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        worldx=gp.tileSize*23;
        worldy=gp.tileSize*21;
        speed=4;
        direction = "down";
    }
    public void getPlayerImage(){

        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");

    }
    public  BufferedImage setup (String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/"+imageName+".png")));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void update(){

        if(keyH.upPressed|| keyH.downPressed|| keyH.leftPressed|| keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objectIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objectIndex);

            //if collision is false, player can move
            if(!collisionOn){
                switch (direction){
                    case "up":
                        worldy-= speed; // upper left corner is origin, so subtracting y moves the player up
                        break;
                    case "down":
                        worldy+= speed;
                        break;
                    case "right":
                        worldx+= speed;
                        break;
                    case "left":
                        worldx-= speed;
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter>walkAnimSpeed){
                if(spriteNum==1){
                    spriteNum=2;
                }
                else if(spriteNum==2){
                    spriteNum=1;
                }
                spriteCounter=0;
            }
        }

    }
    public void pickUpObject(int i){
        if(i!=9999){

            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You found a key!");
                    //System.out.println("You got a key! Total: " + hasKey);
                    break;
                case "Door":
                    if(hasKey>0){
                        gp.ui.showMessage("You unlocked a door!");
                        gp.playSE(3);
                        hasKey--;
                        gp.obj[i] = null;
                        //System.out.println("You opened a door! Total keys: " + hasKey);
                    }
                    else{
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
                case "Boots":
                    gp.playSE(2);
                    gp.ui.showMessage("Speed up!");
                    speed += 2;
                    walkAnimSpeed = 8;
                    gp.obj[i] = null;
                    break;

            }
        }
    }
    public void draw(Graphics2D g2){
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

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
        g2.drawImage(image, screenx, screeny, null);

        //show player hitbox
        g2.setColor(Color.red);
        g2.drawRect(screenx+solidArea.x,screeny+solidArea.y,solidArea.width,solidArea.height);
    }
}
