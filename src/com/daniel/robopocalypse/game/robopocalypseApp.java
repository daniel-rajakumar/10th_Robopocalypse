package com.daniel.robopocalypse.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;//creats a new windows.
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import com.daniel.robopocalypse.game.entites.*;
import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.SpriteSheet;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.gfx.font;
import com.daniel.robopocalypse.game.level.Level;
import com.daniel.robopocalypse.game.net.GameClient;
import com.daniel.robopocalypse.game.net.GameServer;

public class robopocalypseApp extends Canvas implements Runnable {// runnable is a Thread & canvas is used to draw
                                                                  // inside the window

    private static final long serialVersionUID = 1L;
    private static int storyORno = 1;// type any other number value to skip the
                                     // story!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public static final int WIDTH = 160; // width of the window
    public static final int HEIGHT = WIDTH / 12 * 9;// height of the window
    public static final int SCALE = 3;// length of the windows (cm or m or inch or pix etc...)
    public static final String NAME = "Game";// app name in the title bar

    public static JFrame frame;// setting up the window's frame
    public static JWindow window;

    public static boolean running = false;// declaring a variable called running and setting it to false

    public int tickCount = 0;// declaring a variable called tickCount

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);// creating an image
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();// setting up the pixels
    // private SpriteSheet SpriteSheet = new
    // SpriteSheet("/img/Sprite_Sheet.png");//delcaring a new sting called
    // "spriteSheete" with image's path
    private int[] colors = new int[6 * 6 * 6];// each "6" for R, G, B

    private Screen screen;// the screen of the window
    public inputHandler input;// setting up an input variable
    public Level level;// creating the level variable
    public Player player;// the player
    public Robots Enemy;// the robot
    public Health health;// health(heart)

    private GameClient socketClient;
    private GameServer socketServer;

    public static int distancex, distancey;// x and y loac
    public static int robotsCount;// robot count
    public static boolean starting_robo = true;// starting robot(which will be 1st robo which enters the arena)
    public static boolean w_keypress = true;

    public robopocalypseApp() {
        loadingScreen();// loading screen

        if (storyORno == 1) {
            // story();// story
        }

        // start loading the game...
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));// minimum size you can resize(minimize) your
        // window
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));// maximum size you can resize(maximize) your
        // window
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));// preferred size.

        frame = new JFrame(NAME); // from now "frame" is "JFrame(NAME)"

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// just deleted every data as soon you closed the window
        // frame.setLayout(new BorderLayout());// setting up what kind of layout you
        // want

        frame.add(this, BorderLayout.CENTER);// the canvas will be center inside the window.
        frame.pack();// it keeps all the contants inside your windows(part of JFrame)

        frame.setResizable(true);// resize button(on window)
        frame.setLocationRelativeTo(null);// starting location = false
        frame.setVisible(true);// poping the window up
    }

    public void loadingScreen() {
        int width = 1000 / 2;
        int height = 600 / 2;
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("res/img/others/loadingScreen.gif").getImage()
                .getScaledInstance(width, height, Image.SCALE_DEFAULT));

        // loads a loading menu before the game starts!
        window = new JWindow();// creats a Window
        window.getContentPane().add(new JLabel("", imageIcon, SwingConstants.CENTER));
        window.setBounds(250, 100, width, height);// the window size
        window.setLocationRelativeTo(null);// set the frame in center
        window.setVisible(true);// telling java to display the image
        try {
            Thread.sleep(50 * 100);// wait for x amount of time
        } catch (Exception e) {
            e.fillInStackTrace();// shows the error in the terminal
        }
        window.dispose();// window hide/close
        // loading menu ends
    }

    public static void story(String name, int width, int height, int sec) {
        // loads a loading menu before the game starts!
        window = new JWindow();// creats a Window
        ImageIcon imageIcon = new ImageIcon(
                new ImageIcon(name).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        window.getContentPane().add(new JLabel("", imageIcon, SwingConstants.CENTER));
        window.setBounds(250, 100, width, height);// the window size
        window.setLocationRelativeTo(null);// set the frame in center
        window.setVisible(true);// telling java to display the image

        try {
            Thread.sleep(sec * 1000);// wait for x amount of time
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        // loading menu ends
    }

    public void init() {
        int index = 0;
        // RGB
        for (int red = 0; red < 6; red++) {// red
            for (int green = 0; green < 6; green++) {// green
                for (int blue = 0; blue < 6; blue++) {// blue
                    // gives the shades from 0 - 5... 4 is for black,...white and 1 is for
                    // transprent
                    int redRed = (red * 255 / 5);// 256 is gong to be transpent color...
                    int greenGreen = (green * 255 / 5);
                    int blueBlue = (blue * 255 / 5);

                    colors[index++] = (redRed << 16) | (greenGreen << 8) | (blueBlue);
                }
            }
        }
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/img/sprite/Sprite_Sheet.png"));// syncing the screen
        input = new inputHandler(this);// syncing the inputHandler file here
        level = new Level("/img/levels/Level_2.png");// setting up the level using the path

        SwamPlayer(269, 102);// spam a player

        // robotsCount = 100;

        // health(200, 200);

        // SwamRobot(250, 250);
        socketClient.sendData("ping".getBytes());
    }

    public synchronized void start() {
        running = true;// start running
        new Thread(this).start();// this tells to run the app AND output datas in terminal(both at same time)
        if (JOptionPane.showConfirmDialog(this, "Do you want to run this server") == 0) {
            socketServer = new GameServer(this);
            socketServer.start();
        }
        socketClient = new GameClient(this, "localhost");
        socketClient.start();
    }

    public synchronized void stop() {
        running = false;// stop running
    }

    public void run() {
        long lastTime = System.nanoTime();// long is like int... taking nanotime
        double nanoSecondPerTick = 1000000000D / 60D;// dividinig the tick by 60---(min to sec)

        int ticks = 0;// ticks = time
        int frames = 0;// declaring a int variable called frames

        long lastTimer = System.currentTimeMillis();// take current million
        double delta = 0;// declaring a double variable called delta

        init();// add the data to the run from there (like sprite sheet info, player info, main
               // color) etc...

        while (running) {// while the code(game) is running
            long now = System.nanoTime();// declaring a variable called now, which get the live time in nanoSecond
            delta += (now - lastTime) / nanoSecondPerTick;// add delta by currentNanoTime(now) minus(-)
                                                          // lastNanoTIme(lastTime) then divide by perTick
            lastTime = now;// from now, lastTime is "now"; they both have same value

            boolean shouldRender = true;// declaring a boonlean variable called shouldRender, which mean start/stop
                                        // rendering
            while (delta >= 1) {// while the delta is greater than or equale to 1 then....
                ticks++;// add tick by 1
                tick();
                delta--;// reduce delta by 1
                shouldRender = true;// shouldRender is running
            }

            try {// if...
                Thread.sleep(2);// ...this statement is an error...
            } catch (InterruptedException e) {// ...then...
                e.printStackTrace();/// ...output this statement.
            }

            if (shouldRender) {// if shouldRender is true, then...
                frames++;// add frames by 1
                render();// start rendering
            }

            if ((System.currentTimeMillis() - lastTimer) >= 1000) {// if liveTime minus lastTime is greater than or
                                                                   // equale to 1000(1000milisecond = 1 sec) then...
                lastTimer += 1000;// ... add 1000
                frame.setTitle("[Frames: " + frames + "] , [Ticks: " + ticks + "]");// render the frames and ticks on
                                                                                    // the window's frame title bar
                frames = 0;// then set frames to equal 0
                ticks = 0;// set frames to quale 0
            }
        }
    }
    // private int x = 0;
    // private int y = 0;

    public void tick() {// tick aka updats
        tickCount++;// add tickCount by 1
        // screen.xOffset++;screen.yOffset++;
        // for (int i = 0; i < pixels.length; i++) {// forloop for image animation
        // pixels[i] = i + tickCount;// ANIMATION STYLE...

        /** ordering the computer what to do if the key is pressed */
        /*
         * if(input.up.isPressed()){ y--;//move 1px up } if(input.down.isPressed()){
         * y++;//move 1px down } if(input.left.isPressed()){ x--;//move 1px left }
         * if(input.right.isPressed()){ x++;//move 1px right }
         */

        level.tick();// start ticking the level

        if (tickCount % 60 == 0 && starting_robo) {// staring robo
            robotsCount = 1;// robo count = 1
            SpamRobot((int) (Math.random() * 500), (int) (Math.random() * 500));// spam 1 robo as soon the game starts
            starting_robo = false;// set the starting wave_robo to false
        }

        if (tickCount % 60 * 1 == 0 && !starting_robo) {// from 2nd spamming robo till forever
            robotsCount++;// count 1 robo
            SpamRobot((int) (Math.random() * 500), (int) (Math.random() * 500));// spam 1 robo
        }
    }

    public void render() {// render aka shows the results of the tick(updats) as ouput.
        BufferStrategy bs = getBufferStrategy();// from now bs is aka BufferStrategy
        if (bs == null) {// is bufferStrategy's value is none then...
            createBufferStrategy(3);// helps canvas to run without buffering... the higher your value is, the lower
                                    // it buffer; the higher your value is, the more your memory uses.
            return;// returns the value, in this case it is 3
        }

        int xOffset = (int) (player.x - (screen.width / 2));// the player is center of the screen
        int yOffset = (int) (player.y - (screen.height / 2));// the player is in the right of the screen

        level.renderTiles(screen, xOffset, yOffset);// rendering tiles to the game

        for (int x = 0; x < level.width; x++) {// till the level width
            int colors = color.get(-1, -1, -1, 000);// setting the default color of the tile which is inversiable
            if (x % 10 == 0 && x != 0) {
                colors = color.get(-1, -1, -1, 500); // change color
            }
            font.render((x % 10) + "", screen, 0 + (x * 8), 0, colors, 1);// render the font out
        }

        level.renderEntities(screen);// rendering the player and he stays at top everytimme...

        // screen.render(pixels,0,WIDTH);//render the pixels

        /*
         * for (int y = 0; y < 32; y++) { for (int x = 0; x < 32; x++) { boolean flipX =
         * x % 2 == 1;//alternating, (for one loop say true, next time say false)
         * boolean flipY = y % 2 == 1;//alternating, (for one loop say true, next time
         * say false) screen.render(x << 3, y << 3, 0, color.get(555, 505, 055, 550),
         * flipX, flipY);//screen.render(telling java the xPos, telling java the yPos,
         * TileNumber, the color we want to input ) } }
         */

        // String msg1 = "Coding is creative";
        // font.render(msg1, screen, (screen.xOffset + ((screen.width / 2)/**div screen
        // width by 2 so it would be in center */ - ((msg1.length()*8) / 2)/**the
        // rendering starts from the center(1st move) and moves the font's value(2nd
        // move) again to the right we dont want this move, so this minus the value that
        // render moved 2nd time*/)), (screen.yOffset + (screen.height / 2)),
        // color.get(-1, -1, -1, 000));//rendering the font to our game screen

        // syncing the above loop with the size of our window

        for (int y = 0; y < screen.height; y++) {// render the map (height) only the camera view
            for (int x = 0; x < screen.width; x++) {// render the map (width) only the camera view
                int colorCode = screen.pixels[x + (y * screen.width)];// (1x1) *resizable
                if (colorCode < 255)
                    pixels[x + y * WIDTH] = colors[colorCode];// if the value is less than 255, then update our array
            }
        }

        Graphics g = bs.getDrawGraphics();// from now g aka BufferStrategy.DrawGraphics

        g.setColor(Color.BLACK);// background color:black
        g.fillRect(0, 0, getWidth(), getHeight());// the g.setcolor's size

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);// draw the image

        g.dispose();// it will clean up and destory the window after it closed...
        bs.show();// show the animations
    }

    public void SpamRobot(int x, int y) {
        distancex = x;// robots starting xPos
        distancey = y;// robots starting yPos
        Enemy = new Robots(level, distancex, distancey, input, "");// asking users to type a username
        level.addEntity(Enemy);// adding our player to the game
    }

    public void SwamPlayer(int x, int y) {
        distancex = x;// player starting xPos
        distancey = y;// player starting yPos
        player = new Player(level, distancex, distancey, input, 6,
                JOptionPane.showInputDialog(this, "please enter a username"));// asking users to type a username
        level.addEntity(player);// adding our player to the game
    }

    public void health(int x, int y) {
        health = new Health(level, 2, x, y);// making healths
        level.addEntity(health);// adding our player's health to the game
    }

    public void story() {
        // tell the sotry
        story("res/img/Story/one.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/two.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 15);
        story("res/img/Story/three.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 30);
        story("res/img/Story/four.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/five.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 20);
        story("res/img/Story/six.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 19);
        story("res/img/Story/seven.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 12);
        story("res/img/Story/eight.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 15);
        story("res/img/Story/nine.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/ten.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/eleven.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/twelve.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 30);
        story("res/img/Story/thirteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 20);
        story("res/img/Story/fourteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 15);
        story("res/img/Story/fifteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 11);
        story("res/img/Story/sixteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 22);
        story("res/img/Story/eighteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 15);
        story("res/img/Story/seventeen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/nineteen.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 15);
        story("res/img/Story/twenty.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
        story("res/img/Story/twentyone.JPG", (WIDTH * SCALE), (HEIGHT * SCALE), 10);
    }

    public static void main(String[] args) {
        new robopocalypseApp().start();// create the window and (open/start) it
    }

}