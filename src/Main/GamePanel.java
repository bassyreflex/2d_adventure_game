package Main;

import Entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //screen settings 
    final int originalTileSize = 16; //16 pixel x 16 pixel tile 
    final int scale = 3; //scaling up the tiles so they are visible x3 

    public final int tileSize = originalTileSize * scale; // final tile pixel size
    public final int maxScreenCol = 16; // 16 columns of tiles on screen
    public final int maxScreenRow = 12; // 12 rows of tiles on screen
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide 
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall 

    //fps
    int FPS = 60;


    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // make all painting done in off-screen buffer
        this.addKeyListener(keyH); // allows game panel to access key input
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // ALTERNATE GAMELOOP
    /*
    public void run() {

        double drawInterval = 1000000000/FPS; //one second in nanoseconds divided by the fps - 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){
            //System.out.println("The loop is looping");

            //update player info
            update();
            //draw screen
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
     */
    public void run(){
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

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
                System.out.println("FPS: "+ drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);

        g2.dispose(); // dispose of graphics content and release used system resources
    }
} 