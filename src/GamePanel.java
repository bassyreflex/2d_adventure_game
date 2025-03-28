import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //screen settings 
    final int originalTileSize = 16; //16 pixel x 16 pixel tile 
    final int scale = 3; //scaling up the tiles so they are visible x3 

    final int tileSize = originalTileSize * scale; // final tile pixel size 
    final int maxScreenCol = 16; // 16 columns of tiles on screen 
    final int maxScreenRow = 12; // 12 rows of tiles on screen 
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide 
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall 

    Thread gameThread;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // make all painting done in off-screen buffer

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        while(gameThread != null){

            System.out.println("The loop is looping");

            //update player info
            update();
            //draw screen
            repaint();
        }
    }
    public void update(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
} 