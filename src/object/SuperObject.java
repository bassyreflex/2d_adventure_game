package object;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldx, worldy;

    public void draw(Graphics2D g2, GamePanel gp){

        int screenx = worldx - gp.player.worldx + gp.player.screenx;
        int screeny = worldy - gp.player.worldy + gp.player.screeny;


        if(     worldx + gp.tileSize > gp.player.worldx - gp.player.screenx &&
                worldx - gp.tileSize  < gp.player.worldx + gp.player.screenx &&
                worldy + gp.tileSize  > gp.player.worldy - gp.player.screeny &&
                worldy - gp.tileSize  < gp.player.worldy + gp.player.screeny){
            g2.drawImage(image, screenx, screeny, gp.tileSize, gp.tileSize, null);
        }

    }
}
