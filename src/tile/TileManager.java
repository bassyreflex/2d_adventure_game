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
        tile = new Tile[50];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldV2.txt");
    }

    public void getTileImage(){

        //placeholders
        setup(0,"grass00",false);
        setup(1,"grass00",false);
        setup(2,"grass00",false);
        setup(3,"grass00",false);
        setup(4,"grass00",false);
        setup(5,"grass00",false);
        setup(6,"grass00",false);
        setup(7,"grass00",false);
        setup(8,"grass00",false);
        setup(9,"grass00",false);

        //actual tiles
        setup(10,"grass00",false);
        setup(11,"grass01",false);
        setup(12,"water00",true);
        setup(13,"water01",true);
        setup(14,"water02",true);
        setup(15,"water03",true);
        setup(16,"water04",true);
        setup(17,"water05",true);
        setup(18,"water06",true);
        setup(19,"water07",true);
        setup(20,"water08",true);
        setup(21,"water09",true);
        setup(22,"water10",true);
        setup(23,"water11",true);
        setup(24,"water12",true);
        setup(25,"water13",true);
        setup(26,"road00",false);
        setup(27,"road01",false);
        setup(28,"road02",false);
        setup(29,"road03",false);
        setup(30,"road04",false);
        setup(31,"road05",false);
        setup(32,"road06",false);
        setup(33,"road07",false);
        setup(34,"road08",false);
        setup(35,"road09",false);
        setup(36,"road10",false);
        setup(37,"road11",false);
        setup(38,"road12",false);
        setup(39,"earth",false);
        setup(40,"wall",true);
        setup(41,"tree",true);















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
