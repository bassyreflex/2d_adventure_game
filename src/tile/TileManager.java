package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    int mapTileNum[][];

    public TileManager(GamePanel gp){

        this.gp = gp;
        tile = new Tile[10];

        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxScreenCol && row < gp.maxScreenRow){

                String line = br.readLine(); //reads a single line of the map text

                while(col<gp.maxScreenCol){
                    String numbers[] = line.split(" "); //splits the line into different array elements using a space (0 1 0 2) becomes numbers[0]=0, numbers[1]=1, numbers[2]=0, numbers[3]=2.

                    int num= Integer.parseInt(numbers[col]); //changes numbers from string to number

                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }

            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x =0;
        int y =0;

        while(col<gp.maxScreenCol && row <gp.maxScreenRow){

            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y+= gp.tileSize;
            }
        }

        //g2.drawImage(tile[0].image, 0,0, gp.tileSize, gp.tileSize, null);

    }
}
