package com.daniel.robopocalypse.game.entites;

import java.awt.Color;
import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.daniel.robopocalypse.game.inputHandler;
import com.daniel.robopocalypse.game.robopocalypseApp;
import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.gfx.font;
import com.daniel.robopocalypse.game.level.Level;

/**
 * Player
 * 
 * 
 */
public class Player extends Mob {

    private inputHandler input;// key listener
    private int colors = color.get(-1, 111, 145, 543);// color of the robo
    private int scale;// the default size of the mob
    protected boolean isSwimming = false;// swimming for player
    private static int Movementspeed = 80 * 1;// walking/swimming speed
    private int tickCount = 0, renderCount = -1;// count for every tick
    private String username;// username
    static int xa, ya, xloac, yloac;// player locations (x and y)
    public static int distancex, distancey;// player's loc to send to robo(x & y)
    protected static int health = 4 * 8;// health
    private static int health_doge = 1;// this is for health reducing speed...
    private static int tick_hold;// this is for to hold the tick value for certain period of render
    private static boolean health_fix = false;// to make health reduce balanced
    private static int health_update;// update the health(used for testing)
    // public static int testing;
    public static boolean space_keypress = false, w_keypress = false;// this makes holding a key USELESS(with this
                                                                     // pressing = holding key)
    public static boolean damage_time = true;// damage time: if the robo is attacking you that mean this is TRUE else
                                             // its FALSE.

    // static ArrayList<Integer> health_list2 = new ArrayList<Integer>();
    static boolean timer = true;// timer for counting purposes
    static int touch = 0;// touch 1 = true, 0 = false
    static int touch_timer = 0;// for how long does the robo attaking you(touch)

    public Player(Level level, int x, int y, inputHandler input, int health, String username) {
        super(level, "daniel", x, y, health, Movementspeed);// setting up players starting position, name, with
                                                            // speed;
        distancex = x;// syncing x loc
        distancey = y;// syncing y loc
        // Player.health = health;
        this.input = input;// syncing
        this.username = username;// syncing
    }

    public boolean hasCollided(int xa, int ya) {// setting up the collided area inside my player
        int xMin = 0;
        int xMax = 7;// 0 - 7
        int yMin = 3;
        int yMax = 7;
        for (int x = xMin; x < xMax; x++) {// top left - right
            if (isSolidTile(xa, ya, x, yMin)) {
                return true;
            }
        }
        for (int x = xMin; x < xMax; x++) {// bottom left - right
            if (isSolidTile(xa, ya, x, yMax)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {// left top - bottom
            if (isSolidTile(xa, ya, xMin, y)) {
                return true;
            }
        }
        for (int y = yMin; y < yMax; y++) {// right bottom - top
            if (isSolidTile(xa, ya, xMax, y)) {
                return true;
            }
        }
        return false;
    }

    public void tick() {
        xa = 0;
        ya = 0;

        if (input.up.isPressed()) {
            ya--;// move 1px up
        }
        if (input.down.isPressed()) {
            ya++;// move 1px down
        }
        if (input.left.isPressed()) {
            xa--;// move 1px left
        }
        if (input.right.isPressed()) {
            xa++;// move 1px right
        }

        if (input.space.isPressed()) {
            // System.out.println("(y: " + y + " )(x: " + x + " )");
        }

        if ((xa != 0) || (ya != 0)) {// if the player is moving then...
            move(xa, ya);// move
            isMoving = true;// moving is true
        } else {
            isMoving = false;// else if x or y is 0 then... that means your are not moving...
        }

        if (level.getTile((int) this.x >> 3, (int) this.y >> 3).getId() == 3) {// when the player get into water
            isSwimming = true;// tobo is not swimming
        }

        if (isSwimming && level.getTile((int) this.x >> 3, (int) this.y >> 3).getId() != 3) {// when the player get out
                                                                                             // of water
            isSwimming = false;// player is not swimming
        }

        tickCount++;// counting the tick
        this.scale = 1;// scale of the player... this will automatically update during debuging while
                       // rendering the player
        timer = true;
    }

    public void render(Screen screen) {// render the player
        int xTile = 0;// player's sprite sheet location
        int yTIle = 28;
        int xTile_Health = 2;// health's xtile
        int yTIle_Health = 27;// health's ytile
        int walkingSpeed = 4;// walking animation speed
        int flipTop = (numSteps >> walkingSpeed) & 1;// changes the animations
        int flipBottom = (numSteps >> walkingSpeed) & 1;
        // renderCount++;

        if (movingDir == 1) {// if moving up
            xTile += 2;// moving 2 tiles to the right from our sprite sheet
        } else if (movingDir == 2) {/// (2 and 3) moving left or right
            xTile += 4/** it moves 4 tiles (two 16x16) */
                    + ((numSteps >> walkingSpeed) & 1) * 2;// if the number is one then it will give us #2 else if the #
                                                           // is 0 it give us the number "0"
            flipTop = (movingDir - 1) % 2;// give a number between 0 and 1 depense of the players mobing Dir
        } else if (movingDir == 3) {/// (2 and 3) moving left or right
            xTile += 4/** it moves 4 tiles (two 16x16) */
                    + ((numSteps >> walkingSpeed) & 1) * 2;// if the number is one then it will give us #2 else if the #
                                                           // is 0 it give us the number "0"
            flipTop = (movingDir - 1) % 2;// give a number between 0 and 1 depense of the players mobing Dir
            flipBottom = (movingDir - 1) % 2;// give a number between 0 and 1 depense of the players mobing Dir
        }

        int modifier = 8/** in the game move 8px */
                * scale;// size of the player(8 is because one tile is (8x8)
        int xOffset = (int) (x - modifier / 2);// div by2 because we want him in the center
        int yOffset = (int) (y - modifier / 2 - 4);// minus 4 is to get the player meet in center since we divided our
                                                   // player into 4 so we are minusing this by 4

        xloac = xOffset;// this is to send my(player) location to robots
        yloac = yOffset;// this is to send my(player) location to the robots

        ////////////////////////////////////////////////////////////////////////////////////

        for (int i = 0; i < robopocalypseApp.robotsCount; i++) {
            // System.out.println(i);
            renderCount++;
            // System.out.println(renderCount + " ()");

            // if (Player.yloac+1 == Robots.distancey.get(renderCount) && Player.xloac+0 ==
            // Robots.distancex.get(renderCount)){//to affect evey single robot

            if ((Player.yloac + 5 >= Robots.distancey.get(renderCount)
                    && Player.yloac - 8 <= Robots.distancey.get(renderCount))// y-axis
                    && (Player.xloac + 12 >= Robots.distancex.get(renderCount)
                            && Player.xloac - 11 <= Robots.distancex.get(renderCount)))// x-axis
            {
                // (Player.xloac+30 >= Robots.distancex.get(renderCount) && Player.xloac+30 <=
                // Robots.distancex.get(renderCount))){//to affect evey single robot
                if (damage_time) {// if robots attacking you, reduce half health
                    health -= 4;// reduce
                    damage_time = false;
                }

                if (!health_fix) {
                    tick_hold = tickCount;// gets the tick count at the time
                    health_fix = true;
                }

                if (touch == 1) {// if touch = 1(1 = true) then...
                    touch = 0;// touch = 0 (0 = false)
                }

                if ((touch == 1) ? (tickCount % 1 == (0) && timer && !damage_time)
                        : (tickCount % (60 * health_doge) == (tick_hold % (60 * health_doge)) && timer
                                && !damage_time)) {
                    damage_time = true;
                    timer = false;
                    touch = 1;// true
                }
                if ((touch != 0)) {
                    touch = 0;// touch = 0 (0 = false)
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////

        if (isSwimming) {// what to do if swimming...
            int waterColor = 0;// color of the water
            yOffset += 4;
            // animation of the splashy wave when on the water when the player is in the
            // water
            if (tickCount % 60 < 15) {// 0 - 14
                waterColor = color.get(-1, -1, 225, -1);
            } else if (15 <= tickCount % 60 && tickCount % 60 < 30) {// 15 - 29
                yOffset -= 1;
                waterColor = color.get(-1, 225, 115, -1);
            } else if (30 <= tickCount % 60 && tickCount % 60 < 45) {// 30 - 44
                waterColor = color.get(-1, 225, 115, -1);
            } else {
                yOffset -= 1;
                waterColor = color.get(-1, 225, 115, -1);// 45 or above (45 - 60)
            }
            // rendering the animation into our game...
            screen.render((xOffset), yOffset + 3, (0 + 27 * 32), waterColor, 0x00, 1);// left-side
            screen.render((xOffset + 8), yOffset + 3, (0 + 27 * 32), waterColor, 0x01, 1);// right-side
        }

        // rendering the health bar for player...
        /*
         * main hacking keys if(input.space.isPressed()){ if (space_keypress) health-=4;
         * space_keypress = false; } if (!input.space.isPressed()) space_keypress =
         * true;
         * 
         * //////////////////////////////////////////////
         * 
         * if(input.up2.isPressed()){ //TODO: remove this if (w_keypress) health+=4; //
         * w_keypress = false; } if (!input.up2.isPressed()) w_keypress = true;
         * 
         * if(input.down2.isPressed()){ if (w_keypress) health-=4; // w_keypress =
         * false; } //if (!input.down2.isPressed()) w_keypress = true; main hacking
         */

        /*
         * if(input.up2.isPressed()){ if (keypress) { health++; keypress = false; } }
         * 
         * if (!input.up2.isPressed()){ keypress = true; }
         */

        // health = health_list.get(i);//getting the health by its number

        if (health == 0) {// if health is 0 than
            loadingEndScreen();// loading end screen
        }
        // (xOffset + (i * 1 - (health * 5)/8)) + (modifier)

        for (int i = 1; i <= health; i += 8) {// display the healths
            screen.render((xOffset + (i - ((health * 5) / 8)) / ((health / 80) + 1) + modifier), (yOffset) - 8,
                    ((i >= health - 4) ? ((health % 2 == 0) ? xTile_Health + 1 : xTile_Health + 1) : xTile_Health)
                            + (yTIle_Health * 32),
                    color.get(-1, 500, 000, 500), 1, scale);
        } // healths ends

        // now render the player out from sproteSheet to our game(by 1 tile 8x8 totle 4
        // so 8(8x8)x4(tiles) = 32 px)
        screen.render((xOffset) + (modifier * flipTop), (yOffset), ((xTile) + (yTIle) * 32), colors, flipTop, scale);// up-left
                                                                                                                     // tile(from
                                                                                                                     // our
                                                                                                                     // sprite-Sheet/player)

        screen.render((xOffset + modifier - (modifier * flipTop)), (yOffset), ((xTile + 1) + (yTIle) * 32), colors,
                flipTop, scale);// up-right tile(from our sprite-Sheet/player)

        if (!isSwimming) {// if the player is not swimming
            screen.render((xOffset) + (modifier * flipBottom), (yOffset + modifier), ((xTile) + (yTIle + 1) * 32),
                    colors, flipBottom, scale);// down-left tile(from our sprite-Sheet/player)

            screen.render((xOffset + modifier) - (modifier * flipBottom), (yOffset + modifier),
                    ((xTile + 1) + (yTIle + 1) * 32), colors, flipBottom, scale);// down-right tile(from our
                                                                                 // sprite-Sheet/player)
        }

        if (username != null) {// rendering out username (font)
            font.render(username, screen,
                    (xOffset - ((username.length() - 1) / 2 * 8))/** putting the username to the center */
                    , yOffset - 16, color.get(-1, -1, -1, 555), 1);// rendering out userName
        }
    }

    public void loadingEndScreen() {
        robopocalypseApp.running = false;// stop the play
        robopocalypseApp.frame.dispose();// dispose the the game
        robopocalypseApp.window = new JWindow();// open a new Jwindow
        String img = "res/img/others/ending.gif";
        robopocalypseApp.window.getContentPane().add(new JLabel("", new ImageIcon(img), SwingConstants.CENTER));// telling
                                                                                                                // java
                                                                                                                // to
                                                                                                                // show
                                                                                                                // the
                                                                                                                // image
        robopocalypseApp.window.setBounds(0, 0, 450, 450);// set the size of the image
        robopocalypseApp.window.setLocationRelativeTo(null);// set the location to center
        robopocalypseApp.window.setVisible(true);// show it
        try {
            Thread.sleep(12 * 1000);// wait for x sec
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        System.exit(0); // stop the application(exit)
    }
}
