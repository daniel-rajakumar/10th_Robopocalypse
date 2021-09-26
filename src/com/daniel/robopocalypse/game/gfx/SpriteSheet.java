package com.daniel.robopocalypse.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * spritSheet
 */
public class SpriteSheet {
    
    public String path;//the image's path
    public int width;//public int means you have no control ove it... it will take it value automatically.
    public int height;

    public int[] pixels;

    public SpriteSheet(String path){
        BufferedImage image = null;//setting image to none then...
        
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));//read the path's image 
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(image == null){//read the path's image until it can't read
            return;//void method can not return a value
        }
        

        this.path=path;//"this.path"(class's path) = "path"(method's path)
        this.width=image.getWidth();//get the width of the image
        this.height=image.getHeight();//get the height of the image

        pixels = image.getRGB(0, 0, width, height, null, 0, width);//image.getRGB(startX, startY, w, h, rgbArray, offset, scansize)
    
        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = (pixels[i] & 0xff)/64;//Oxff aka (00 00 00 ff)...dividing by 64 so we can divide 255 by 4 parts...(thsi sprite sheet is going to have four colors..)
        }

        for(int i = 0; i < 8; i++) {
           // System.out.println("[" + (i+1) + "] " + pixels[i]);//getting the data of the color(its value 0-3)
        }
    }
}