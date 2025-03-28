import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // create game window 
        JFrame window = new JFrame();
        // make window closeable with x button 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // make window not resizable 
        window.setResizable(false);
        // set title of window 
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        // make window moveable around the screen 
        window.setLocationRelativeTo(null);
        //make visible 
        window.setVisible(true);

        gamePanel.startGameThread();

    }
} 