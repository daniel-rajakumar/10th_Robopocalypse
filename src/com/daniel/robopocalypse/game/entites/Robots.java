package com.daniel.robopocalypse.game.entites;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;

import com.daniel.robopocalypse.game.inputHandler;
import com.daniel.robopocalypse.game.robopocalypseApp;
import com.daniel.robopocalypse.game.entites.*;
import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.gfx.font;
import com.daniel.robopocalypse.game.level.Level;

/**
 * Enemy
 */
public class Robots extends Mob {

    private inputHandler input;//for key listener 
    private int colors;//color of the robo
    private int scale;// the default size of the mob
    protected boolean isSwimming = false;// swimming for player
    private static int Movementspeed = 100;// walking/swimming speed(not really using them since every robo will have diffrent speed)
    private int tickCount = 0;// count for every tick
    private String username;// username
    static int numRobot;//count of robo
    static int radius = 50;//the radius, aka eye sight of the robo
    static ArrayList<Integer> distancex = new ArrayList<Integer>();//arraylist to store robo's x distance
    static ArrayList<Integer> distancey = new ArrayList<Integer>();//arralist to store rovo's y distance
    static double waitingMovement;//wait till next life reduce
    static int balance = 8, renderCount;//balance is for making enemy's location blanced from all the animations(idk how to explain this XD): render Count is to count the render, keep track of it
    static boolean move;//is the player moving
    static int xloac_robot, yloac_robot;//(correct, remixed)x and y loc of the robo(this is to share to robo's loc to the player)
    public static int xOffset, yOffset;//other form of x and y loc(not perfect)
    static int[] color_list = new int[100];//arralist to store rovo's y distance
    
    public Robots(Level level, int x, int y, inputHandler input, String username) {
        super(level, "enemy", x, y, 1, (int)(Math.random()*50)+20);// setting up players starting position, name, with speed;      
        Robo_color();//my color lists
        colors = color_list[(int)(Math.random()*23)];//random diffrent color for every robo
        distancex.add(x);//add x loc to arraylist                                                             
        distancey.add(y);//add y lox to arraylist                                                              
        this.input = input;//syncing
        this.username = username;//syncing
        //System.out.println(distancex + " " + distancey);
    }
    
    public boolean hasCollided(int xa, int ya) {//setting up the collided area inside my player
        int xMin = 0;
        int xMax = 7;//0 - 7
        int yMin = 3;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++){//top left - right
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++){//bottom left - right
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++){//left top - bottom
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++){//right bottom - top
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }
    
    public void tick() {
        int xa = 0;
        int ya = 0;
        
        //making the robot follow my location...
        // ya = Player.ya;
        //xa = Player.xa;
        
        //  if (input.up2.isPressed()) {y
            //    xa = Player.xloac;
            //  ya = Player.yloac;
        //}
        
        
        
        /**TODO: for
         * else if(Player.xloac <= x){
         xa++;
        }
        /** */
        
        // if((Player.xloac+radius) >= x) xa--;
        
        /*
        if(Player.xloac - x >= 40){
            colors = color.get(-1, 248, 213, 2);
        } else {
            colors = color.get(-1, 0, 0, 343);
        }
        */         
        
        if (Player.yloac+balance >= (int)y) {//if the robo is top of player than...
            //if insde radius
            if (((Player.yloac+balance)-radius) >= (int)y) {
              //ya--;
            } else if(((Player.yloac+balance)-(int)y) <= (radius-2)){
                ya++;//move down
            }    
        } 
        
        if ((Player.yloac+balance <= (int)y)) {//if the robo is bottom of player than...
            //if insde radius            
            if (((int)y - Player.yloac) <= (radius + balance)) {
                ya--;//move up
            } else if (((int)y - Player.yloac) >= ((radius+2) + balance)){
             //ya++; 
            }
        }
    
        
        balance = 4;
        
        
        if (Player.xloac+balance-12 >= (int)x) {//if the robo is left of player than...
            //if insde radius            
            if (((Player.xloac+balance)-radius) >= (int)x) {
               // xa--;
            } else if(((Player.xloac+balance)-(int)x) <= (radius-2)){
                xa++;//move right
            } 
        } 
        
        if ((Player.xloac+balance+12 <= (int)x)) {//if the robo is right of player than...
            //if insde radius            
            if (((int)x - Player.xloac) <= ((radius-2) + balance)) {
                xa--;//move left
            } else if (((int)x - Player.xloac) >= (radius + balance)){
              //xa++; 
            }
        } 
        

        /*
        test = 8;
        if (Player.yloac-test <= (int)y) {
            if ((Player.yloac-test)+radius <= (int)y) {
                ya--;
            } else if((Player.yloac-test)+(int)y >= radius-2){
                ya++;
            }    
        }   
    

        
        if (Player.xloac+test >= (int)x) {
            if ((Player.xloac+test)-radius >= (int)x) {
                xa++;
            } else if((Player.xloac+test)-(int)x <= radius-2){
                xa--;
            }    
        } if (Player.xloac+test <= (int)x) {
            xa--;
        }
        */
        
        


        /*
        
        if(Player.yloac + radius >= (int)y){
            if (Player.yloac + radius != (int)y && Player.yloac - radius != (int)y) {
                ya++;
            }
        } else if (Player.yloac <= (int)y) { //if the robo x is >> than mine then robo move <<
            ya--;
        } 
        
        if (Player.yloac > (int)y) {
            if (Player.yloac - radius != (int)y && Player.yloac + radius != (int)y) {
                ya-=2;
            } if (Player.yloac > y && Player.yloac - radius > y) {
                ya+=2;
            }
        }
        
        
        if(Player.xloac + radius >= (int)x){
            if (Player.xloac + radius != (int)x && Player.xloac - radius != (int)x) {
                xa++;
            }
        } else if (Player.xloac <= (int)x) { //if the robo x is >> than mine then robo move <<
            xa--;
        } 
        
        if (Player.xloac > (int)x) {
            if (Player.xloac - radius != (int)x && Player.xloac + radius != (int)x) {
                xa-=2;
            } if (Player.xloac > x && Player.xloac - radius > x) {
                xa+=2;
            }
        }
        
        */
        
        /*
        if (Player.xloac - 30 >= (int)x) {//check if im >> of the bot
            if ((Player.xloac - 30) != (int)x) {//and i am 30 <<
                xa-=2;
            }
        } else if (Player.xloac - 30 <= (int)x) { //if the robo x is >> than mine then robo move <<
            //   xa+=2;
        }
        */
        
        
        /*
         if(Player.yloac <= y){//if
            ya--;
        } else if(Player.yloac >= y){
            ya++;
        }
        
        
        
        if (Player.xloac != (int)x) {
            if (Player.xloac <= (int)x) { //if the robo x is >> than mine then robo move <<
                xa--;  //left   
            } else if(Player.xloac >= (int)x){//else if the robo x is << than mine than robo move >>
                xa++;
            } 
        } else {
            
        }

        */
        
        
            //System.out.println(Robots.yOffset+ " - " + Robots.yloac_robot);
           // System.out.println();
        

        
        /**
         if (Player.yloac <= y) {
             ya--; //up
            } 
            
            if (Player.yloac >= y) {
                ya++;  //down
            }
            
            
            if (Player.xloac <= x) {
                xa--;  //left
            }
            
            
            if (Player.xloac >= x) {
                xa++;  //right
            }      
            * 
            */
            /*
            if(input.up.isPressed()){
                ya--;//move 1px up
            }
            if(input.down.isPressed()){
                ya++;//move 1px down
            }
            if(input.left.isPressed()){
                xa--;//move 1px left
            }
            if(input.right.isPressed()){
                xa++;//move 1px right
            }
            */
            
            if ((xa != 0) || (ya != 0)) {//if the player is moving then...
                move(xa, ya);//move
                isMoving = true;//moving is true 
            } else {
                isMoving = false;//else if x or y is 0 then... that means your are not moving...
            }
            
            if (level.getTile((int)this.x >>3, (int)this.y>>3).getId() == 3) {//when the player get into water
                isSwimming = true;//robo is swimming
            }
            
            if (isSwimming && level.getTile((int)this.x >> 3, (int)this.y>>3).getId() != 3) {//when the player get out of water
                isSwimming = false;//robo is not swimming
            }
            
            tickCount++;//counting the tick
            this.scale = 1;//scale of the player... this will automatically update during debuging while rendering the player
        }
        
        public void render(Screen screen) {//render the player
            int xTile = 24;//x axis in sprite sheet
            int yTIle = 28;//y axis in sprite sheet
            int walkingSpeed = 4;
            int flipTop = (numSteps >> walkingSpeed) & 1;//changes the animations
            int flipBottom = (numSteps >> walkingSpeed) & 1;
            renderCount++;
            
            if (movingDir == 1) {//if moving up
                xTile += 2;//moving 2 tiles to the right from our sprite sheet
            } else if (movingDir == 2) {/// (2 and 3) moving left or right
                xTile += 4/**it moves 4 tiles (two 16x16) */ + ((numSteps >> walkingSpeed) & 1) * 2;//if the number is one then it will give us #2 else if the # is 0 it give us the number "0"
                flipTop = (movingDir - 1) % 2;//give a number between 0 and 1 depense of the players mobing Dir
            } else if (movingDir == 3) {/// (2 and 3) moving left or right
                xTile += 4/**it moves 4 tiles (two 16x16) */ + ((numSteps >> walkingSpeed) & 1) * 2;//if the number is one then it will give us #2 else if the # is 0 it give us the number "0"
                flipTop = (movingDir - 1) % 2;//give a number between 0 and 1 depense of the players mobing Dir
                flipBottom = (movingDir - 1) % 2;//give a number between 0 and 1 depense of the players mobing Dir
            }
            
            int modifier = 8/**in the game move 8px*/ * scale;//size of the player(8 is because one tile is (8x8)
            xOffset = (int) ((int)x - modifier / 2);// div by2 because we want him in the center
            yOffset = (int) ((int)y - modifier / 2 - 4);// minus 4 is to get the player meet in center since we divided our
            // player into 4 so we are minusing this by 4
            
            
            //rendering the art from sprite sheet
           // for (int i = 0; i < 1000; i++) {//I REALLY HAVE NO IDEA HOW THIS LOOP IS WORKING... 
                if (numRobot-1 == 0) {
                    xOffset = Player.xloac - (Player.distancex - distancex.get(1));
                    yOffset = Player.yloac - (Player.distancey - distancey.get(1)); 
                    
                //    xOffset = (int) (Player.xloac - modifier / 2);// div by2 because we want him in the center
                //    yOffset = (int) (Player.yloac - modifier / 2 - 4);// minus 4 is to get the player meet in center since we divided our
            } 
            distancex.add(xOffset);//add the current xloc in the arraylist
            distancey.add(yOffset);//add the current yloc in the arraylist
           // System.out.println(distancey.get(renderCount) + "");
            xloac_robot = xOffset;//this is to send my(player) location to robots
            yloac_robot = yOffset;//this is to send my(player) location to the robots

         //   distancex.add(xOffset);
           // distancey.add(yOffset);
            //}  
            

            if (isSwimming) {//what to do if swimming...
                int waterColor = 0;//color of the water
                yOffset += 4;
                //animation of the splashy wave when on the water when the player is in the water
                if (tickCount % 60 < 15) {//0 - 14
                    waterColor = color.get(-1, -1, 225, -1);
                } else if (15 <= tickCount%60 && tickCount%60 < 30){//15 - 29
                yOffset -=1;//move 1 px up
                waterColor = color.get(-1, 225, 115, -1);
            } else if (30 <= tickCount%60 && tickCount%60 < 45){//30 - 44
                waterColor = color.get(-1, 225, 115, -1);
            } else {
                yOffset -=1;//move 1px up 
                waterColor = color.get(-1, 225, 115, -1);// 45 or above (45 - 60)
            }
            //rendering the animation into our game...
            screen.render((xOffset), yOffset + 3, (0 + 27 * 32), waterColor, 0x00, 1);//left-side
            screen.render((xOffset+8), yOffset + 3, (0 + 27 * 32), waterColor, 0x01, 1);//right-side
        }

        //now render the player out from sproteSheet to our game(by 1 tile 8x8 totle 4 so 8(8x8)x4(tiles) = 32 px)
        
        screen.render((xOffset) + (modifier * flipTop),                (yOffset),            ((xTile) +    (yTIle) *   32),   colors,   flipTop,   scale);//up-left tile(from our sprite-Sheet/player)

        screen.render((xOffset + modifier - (modifier * flipTop)),     (yOffset),            ((xTile+1) +  (yTIle) *   32),   colors,   flipTop,   scale);//up-right tile(from our sprite-Sheet/player)
        
        
        if (!isSwimming){//if the player is not swimming
        screen.render((xOffset)  + (modifier * flipBottom),                (yOffset + modifier), ((xTile) +    (yTIle+1) * 32),   colors,   flipBottom,   scale);//down-left tile(from our sprite-Sheet/player)
    
        screen.render((xOffset  + modifier)  - (modifier * flipBottom),    (yOffset + modifier), ((xTile+1) +  (yTIle+1) * 32),   colors,   flipBottom,   scale);//down-right tile(from our sprite-Sheet/player)
        }

        if(username != null){//rendering username (font)(robots actually dont need a name LOL)
            font.render(username, screen, (xOffset - ((username.length() -1)/2 * 8))/**putting the username to the center */, yOffset - 10, color.get(-1, -1, -1, 555), 1);
        }
    }

    public static void Robo_color() {//just few sample of colors
        color_list[0] = color.get(-1, 555, 000, 000);//black

        color_list[1] = color.get(-1, 555, 500, 000);//red
        color_list[2] = color.get(-1, 555, 050, 000);//green
        color_list[3] = color.get(-1, 555, 005, 000);//blue
        color_list[4] = color.get(-1, 555, 550, 000);//yellow
        color_list[5] = color.get(-1, 555, 055, 000);//Cyan
        color_list[6] = color.get(-1, 555, 505, 000);//pink

        color_list[7] = color.get(-1, 555, 530, 000);//orange
        color_list[8] = color.get(-1, 555, 503, 000);//pink
        color_list[9] = color.get(-1, 555, 035, 000);//grey_darkGreen
        color_list[10] = color.get(-1, 555, 305, 000);//purple
        color_list[11] = color.get(-1, 555, 053, 000);//blue_green
        color_list[12] = color.get(-1, 555, 350, 000);//yellow_green(my fav (:)

        color_list[13] = color.get(-1, 555, 300, 000);//darkRed
        color_list[14] = color.get(-1, 555, 055, 000);//lightBlue
        color_list[15] = color.get(-1, 555, 003, 000);//darkBlue
        color_list[16] = color.get(-1, 555, 330, 000);//darkYellow
        color_list[17] = color.get(-1, 555, 033, 000);//lightGreen
        color_list[18] = color.get(-1, 555, 303, 000);//darkPurple/darkPink
        
        color_list[19] = color.get(-1, 555, 111, 000);//darkGrey
        color_list[20] = color.get(-1, 555, 222, 000);//grey
        color_list[21] = color.get(-1, 555, 333, 000);//lightGrey
        color_list[22] = color.get(-1, 555, 444, 000);//veryLightGrey
        
        color_list[23] = color.get(-1, 555, 555, 000);//white
    }
}