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
    public Tile[] tile;

    public int[][] mapTileNum;

    public TileManager(GamePanel gp){

        this.gp = gp;
        tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col<gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine(); //reads a single line of the map text

                while(col<gp.maxWorldCol){
                    String[] numbers = line.split(" "); //splits the line into different array elements using a space (0 1 0 2) becomes numbers[0]=0, numbers[1]=1, numbers[2]=0, numbers[3]=2.

                    int num= Integer.parseInt(numbers[col]); //changes numbers from string to number

                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }

            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;



        while(worldCol<gp.maxWorldCol && worldRow <gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldx = worldCol * gp.tileSize;
            int worldy = worldRow * gp.tileSize;
            int screenx = worldx - gp.player.worldx + gp.player.screenx;
            int screeny = worldy - gp.player.worldy + gp.player.screeny;


            if(     worldx + gp.tileSize > gp.player.worldx - gp.player.screenx &&
                    worldx - gp.tileSize  < gp.player.worldx + gp.player.screenx &&
                    worldy + gp.tileSize  > gp.player.worldy - gp.player.screeny &&
                    worldy - gp.tileSize  < gp.player.worldy + gp.player.screeny){
                g2.drawImage(tile[tileNum].image, screenx, screeny, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

        //g2.drawImage(tile[0].image, 0,0, gp.tileSize, gp.tileSize, null);

    }
}
