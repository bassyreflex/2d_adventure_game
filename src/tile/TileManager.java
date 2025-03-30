package tile;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

            setup(0,"grass",false);
            setup(1,"wall",true);
            setup(2,"water",true);
            setup(3,"earth",false);
            setup(4,"tree",true);
            setup(5,"sand",false);

    }
    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"+imageName+".png")));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
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
                g2.drawImage(tile[tileNum].image, screenx, screeny, null);
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
