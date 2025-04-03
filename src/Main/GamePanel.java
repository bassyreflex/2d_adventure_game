package Main;

import Entity.Entity;
import Entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

    //screen settings 
    final int originalTileSize = 16; //16 pixel x 16 pixel tile 
    final int scale = 3; //scaling up the tiles so they are visible x3 

    public final int tileSize = originalTileSize * scale; // final tile pixel size
    public final int maxScreenCol = 16; // 16 columns of tiles on screen
    public final int maxScreenRow = 12; // 12 rows of tiles on screen
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall

    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;



    //fps
    public int FPS = 60;
    int[] averageFPS = new int[100];

    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound SE = new Sound();
    Sound music = new Sound();

    public UI ui = new UI(this);


    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    //entity and object
    public Player player = new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];

    //game state
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;



    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // make all painting done in off-screen buffer
        this.addKeyListener(keyH); // allows game panel to access key input
        this.setFocusable(true);
    }
    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;
        int fpsCounter = 0;

        while(gameThread!=null){

            currentTime = System.nanoTime();

            delta+= (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                //System.out.println("FPS: "+ drawCount);
                averageFPS[fpsCounter] = drawCount;
                fpsCounter++;
                if(fpsCounter>99){
                    fpsCounter = 0;
                }
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void calculateAverageFPS(){
        int total = 0;
        int dataPointCounter = 0;
        float average;
        for (int i = 0; i < averageFPS.length; i++) {

            if(averageFPS[i]>1){
                total+=averageFPS[i];
                dataPointCounter++;

            }

        }
        average = (float) total /dataPointCounter;
        System.out.println("Average FPS from "+dataPointCounter+" data points: "+average+"fps");
        //System.out.println(Arrays.toString(averageFPS));
    }
    public void update(){
        if(gameState == playState){
            player.update();

            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].update();
                }
            }
        }
        else if (gameState == pauseState){

        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //debug
        long drawStart = 0;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }

        //title screen
        if(gameState==titleState){
            ui.draw(g2);
        }
        else{

            //tile drawing
            tileM.draw(g2);

            //object drawing
            for(int i=0;i<obj.length;i++){
                if(obj[i]!=null){
                    obj[i].draw(g2,this);
                }
            }

            //npc drawing
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    npc[i].draw(g2);
                }
            }

            //player drawing
            player.draw(g2);

            //UI
            ui.draw(g2);
        }




        //debug
        if(keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd-drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time:"+ passed, 10, 400);
            //System.out.println("Draw Time:"+ passed);
        }


        g2.dispose(); // dispose of graphics content and release used system resources
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        SE.setFile(i);
        SE.play();
    }
} 