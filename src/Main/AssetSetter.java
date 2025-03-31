package Main;


import Entity.NPC_OldMan;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

    }
    public void setNPC(){

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldx = gp.tileSize*21;
        gp.npc[0].worldy = gp.tileSize*21;

    }
}
