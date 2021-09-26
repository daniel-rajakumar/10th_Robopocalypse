package com.daniel.robopocalypse.game.level;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.daniel.robopocalypse.game.entites.Entity;
import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.level.tiles.Tile;

import java.awt.image.BufferedImage;

/**
 * Level
 */
public class Level {

    private static final Tile Tile = null;
    private byte[] tiles;//id of every single tile
    public int width;//width of the level(map)
    public int height;//height of the level(m)
    public List<Entity> entities = new ArrayList<Entity>();//list of mobs on our screen
    private String imagePath;//the path of the name(name)
    private BufferedImage image;//the image(from our spriteSheet)

  //  public Level(int width, int height){
    public Level(String imagePath){//getting the path of the image by its name
        if (imagePath != null) {//if we send an image
            this.imagePath = imagePath;//syncing
            this.loadLevelFromFile();
        } else {//if we do not send an image
            this.width = 64;//syncing the width value
            this.height = 64;//syncing the hegiht value
            tiles = new byte[width * height];//the map size
            this.generateLevel();//generate the level
        }
    }

    private void loadLevelFromFile(){
        try {
            this.image = ImageIO.read(Level.class.getResource(this.imagePath));
            this.width = image.getWidth();//syncing
            this.height = image.getHeight();//syncing
            tiles = new byte[width * height];//getting the id of the tile
            this.loadTiles();
        } catch (IOException e) {//if the data in the try vody have error/issue then this will hold the error till its get the data
            e.printStackTrace();
        }
    }

    private void loadTiles(){
        int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);//translate all the data from our image to an int value and store it in tile array
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileCheck : for (Tile t : Tile.tiles) {//it going to loop the whole arraylist
                    if (t != null && t.getLevelColor() == tileColors[x + y * width]) {//if the level color and the tile color are equal then...
                        this.tiles[x + y * width] = t.getId();//synicng
                        break tileCheck;
                    }
                }
            }
        }
    }

    private void saveLevelToFile(){
        try {
            ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));//it will take the iamge and save it in the imagePath as "png"
        } catch (IOException e) {
            e.printStackTrace();//if anything goes wrong in thr try then... hold on
        }
    }

    public void alterTile(int x, int y, Tile newTile){
        this.tiles[x + y * width] = newTile.getId();//syncing
        image.setRGB(x, y, newTile.getLevelColor());//pushinh the saved image
    }

    public void generateLevel() {
        for (int y = 0; y < height/**height of the level- map*/; y++) {
            for (int x = 0; x < width/**width of the level - map */; x++) {
                if (x * y % 10 < 5) {
                    tiles[x + y * width] = Tile.GRASS.getId();//to get the id of the tile(grass)
                } else {
                    tiles[x + y * width] = Tile.STONE.getId();//to get the id of the tile(stone)
                }
            }
        }
    }

    public void tick(){
        for (Entity e : entities) {
          e.tick();//ticking the player so he can move accoding to the tick speed
        }
        for (Tile t : Tile.tiles) {//updating everyting at the same time rather than 1 by 1
            if(t == null){
                break;
            }
            t.tick();
        }
    }

    public void renderTiles(Screen screen, int xOffset, int yOffset) {//render the tile so it will lock and the map wont cant move the map infinity times.
        if(xOffset < 0) xOffset = 0;//preventing from getting negative value
        if(xOffset > ((width << 3)/**totale size of the baord */ - screen.width)) xOffset = ((width << 3) - screen.width);//
        if(yOffset < 0) yOffset = 0;//no negative value
        if(yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);

        screen.setOffset(xOffset, yOffset);//the camerca of the game.
      
        for (int y = (yOffset >> 3); y < (yOffset + screen.height >> 3) + 1; y++) {
            for (int x = (xOffset >> 3); x < (xOffset + screen.width >> 3) + 1; x++) {
                getTile(x,y).render(screen, this, x << 3, y << 3);
            }
        }
    }

    public void renderEntities(Screen screen){//ot os like z-index in html... stays top
        for (Entity e : entities) {
            e.render(screen);
        }
    }

    public Tile getTile(int x, int y) {//if the camera is outside the level board then...
        if (0 > x || x >= width || 0 > y || y >= height) return Tile.VOID /* VOID */;
        return Tile.tiles[tiles[x + y * width]];
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
}