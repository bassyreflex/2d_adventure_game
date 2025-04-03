package Main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica,arial40;
    public boolean messageOn = false;
    public String message = "";

    int messageCounter = 0;
    public boolean gameFinished = false;

    public String currentDialogue = "";

    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;


        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        arial40 = new Font("Arial", Font.PLAIN, 40);

    }

    public void showMessage(String text){
        message=text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        this.g2=g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);
        //title state
        if(gp.gameState==gp.titleState){
            drawTitleScreen();
        }
        else if(gp.gameState==gp.playState){
            // do playstate stuff (later)
        }
        else if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        else if (gp.gameState==gp.dialogueState){
            drawDialogueScreen();
        }
    }
    public void drawTitleScreen(){

        //g2.setColor(new Color(70,120,80));
        //g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
        //title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96));
        String text = "Blue Boy Adventure";
        int x= getTextCenterx(text);
        int y= gp.tileSize*3;


        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);
        //main text
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //image
        x = gp.screenWidth/2 - gp.tileSize;
        y+=gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2,null);

        //menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48));

        text = "NEW GAME";
        x= getTextCenterx(text);
        y+= (int) (gp.tileSize*3.5);
        g2.drawString(text,x,y);
        if(commandNum==0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "LOAD GAME";
        x= getTextCenterx(text);
        y+= gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum==1){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT GAME";
        x= getTextCenterx(text);
        y+= gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum==2){
            g2.drawString(">",x-gp.tileSize,y);
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,34F));
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
