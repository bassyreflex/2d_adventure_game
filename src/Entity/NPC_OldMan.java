package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{


    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        getOldManImage();
        setDialogue();

    }
    public void getOldManImage(){

        up1 = setup("/NPC/oldman_up_1");
        up2 = setup("/NPC/oldman_up_2");
        right1 = setup("/NPC/oldman_right_1");
        right2 = setup("/NPC/oldman_right_2");
        down1 = setup("/NPC/oldman_down_1");
        down2 = setup("/NPC/oldman_down_2");
        left1 = setup("/NPC/oldman_left_1");
        left2 = setup("/NPC/oldman_left_2");

    }

    public void setDialogue(){

        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So you've come to this island to\n find the treasure?";
        dialogues[2] = "I used to be a great wizard, but now......\n I'm a bit too old for taking an adventure";
        dialogues[3] = "Well, good luck on you";
        dialogues[4] = "I say: be gone!";
        dialogues[5] = "Salutations!";
        dialogues[6] = "Have you nothing better to do??";
        dialogues[7] = "Let me wander in PEACE!";
        dialogues[8] = "You don't want to do this...";
        dialogues[9] = "Last warning.............";
        dialogues[10] = "Fine, you leave me with no choice.";


    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter > 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick number 1-100

            if(i<=25){
                direction = "up";
            }
            else if(i<=50){
                direction = "down";
            }
            else if(i<=75){
                direction = "left";
            }
            else if(i<=100){
                direction = "right";
            }

            actionLockCounter = 0;
        }


    }
    public void speak(){
        super.speak();
    }


}
