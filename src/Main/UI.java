package Main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial40,arial80B;
    public boolean messageOn = false;
    public String message = "";

    int messageCounter = 0;
    public boolean gameFinished = false;

    public String currentDialogue = "";

    public UI(GamePanel gp){
        this.gp = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text){
        message=text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        this.g2=g2;

        g2.setFont(arial40);
        g2.setColor(Color.white);

        if(gp.gameState==gp.playState){
            // do playstate stuff (later)
        }
        else if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        else if (gp.gameState==gp.dialogueState){
            drawDialogueScreen();
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,70F));
        String text = "PAUSED";
        int x = getTextCenterx(text);
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - length/2;

        int y = gp.screenHeight/2;

        g2.drawString(text,x,y);
    }
    public void drawDialogueScreen(){

        //dialogue window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,height,width);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }



    }
    public void drawSubWindow(int x,int y,int height,int width){

        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(225,225,225);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);

    }
    public int getTextCenterx(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int totalx = gp.screenWidth/2 - length/2;
        return totalx;
    }
}
