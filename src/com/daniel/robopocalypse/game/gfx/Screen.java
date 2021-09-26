package com.daniel.robopocalypse.game.gfx;

/**
 * Screen
 */
public class Screen {

    public static final int MAP_WIDTH = 64;//math width
    public static final int MAP_WIDTH_MASK = MAP_WIDTH-1;//map width inside map width
    
    public static final byte BIT_MIRROR_X = 0x01;// 0x01 means 1 in decimal = 00000001 in binary number...
    public static final byte BIT_MIRROR_Y = 0x02;
    //public int[] tiles = new int[MAP_WIDTH*MAP_WIDTH];//array for tiles (w * h)
    //public int[] colors = new int[MAP_WIDTH*MAP_WIDTH*4];
    public int[] pixels;//array for color (1 tile)    

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public SpriteSheet sheet;

    public Screen(int width, int height, SpriteSheet sheet){//method for display(screen)
        this.width = width;
        this.height = height;
        this.sheet = sheet;

        pixels = new int[width * height]; // syncing it to buffered image size
    }    
/** 
        for (int i = 0; i < (MAP_WIDTH*MAP_WIDTH); i++) {//srtting up the colors
            colors[i*4+0] = 0xff00ff;
            colors[i*4+1] = 0x00ffff;
            colors[i*4+2] = 0xffff00;
            colors[i*4+3] = 0xffffff;
        }
    }

    public void render(int[] pixels/**the pixel of the tile ,int offset /**offset of the tile, int row/**the row in spriteSheet ){
        for (int yTile = (yOffset >> 3); yTile <= ((yOffset + height) >> 3); yTile++) { //((yOffset >> 3) = (((yOffset / 2) / 2) / 2)
            //"8 because our sheet is 8x8" 
            int yMin = yTile * 8 - yOffset;//if the y tile hit 8x8(y-axis) then go back to 0(this would cause infinity loop)
            int yMax = yMin + 8;//saying the maximum is 8 pixels
            if(yMin < 0) yMin = 0;//if yMin is smaller than 0 then then stay at 0
            if(yMax > height) yMax = height;//ifthe hight of the screen is smaller than yMax then yMax = the hight's value. This keep the images inside out height of the window

            //SAME RULE APPLIES WITH X-AXIS
            for (int xTile = (xOffset >> 3); xTile <= ((xOffset + width) >> 3); xTile++) {
                int xMin = xTile * 8 - xOffset;
                int xMax = xMin + 8;
                if(xMin < 0) xMin = 0;
                if(xMax > width) xMax = width;

                //out tile size is 8x08
                // "&" this compares binary #
                int tileIndex = (xTile & (MAP_WIDTH_MASK)) + (yTile & (MAP_WIDTH_MASK)) * MAP_WIDTH;//telling javs to take only 8x8 from out sprite sheet


                for (int y = yMin; y < yMax; y++) {//draw from minumum(1 px) unitl it reaches the maximum height(8 px)
                    int sheetPixel = (((y + yOffset) & 7) * sheet.width) + ((xMin + xOffset) & 7);//"7" because we are taking 1 pixels so we need to tell the java the full length so (1 + 7) = 8px which is our total height(8x8)
                    int tilePixel = offset + xMin + (y * row);//multiply y(1-8) by the row, then add xMin and offset value to get out tile's pixel(1x1)

                    for(int x = xMin; x < xMax; x++){//draw unitl it reaches the maximum width(8 pix)
                        int colors = (tileIndex * 4/**we are using 4 color in our sprite sheet ) + sheet.pixels[sheetPixel++];
                        pixels[tilePixel++] = this.colors[colors];//syncing this color with out default color variable
                    }
                }
            }
        }
    }
    */

    /*
    public void render(int xPos, int yPos, int tile, int color) {//making a shortcute where we do not need to add "false, flase" everytime when we want to render fonts
        render(xPos, yPos, tile, color, 0x00);
    }*/
        
    public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {//this method is the main method for rendering colore
        xPos -= xOffset;
        yPos -= yOffset;

        boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;//if mirrorDie and Bit_miroor x is positive then...true
        boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;//if mirrorDie and Bit_miroor y is positive then... trre

        int scaleMap = scale - 1;//the scale of the map
        int xTile = tile % 32;// 1-32 (width of the row)
        int yTile = tile / 32;//get y pixel by /32 since x-axis row is 32 px
        int tileOffset = (xTile << 3/**"<<3" is same as "x8" */) + (yTile << 3) * sheet.width;//because tile size is 8x8 "3<<3"
        //setting data inside the loop... so java can access the data from the loop...
        for (int y = 0; y < 8; y++) {
            int ySheet = y;
            if (mirrorY) ySheet = 7 - y;//if mirrorY is true than count from (8 - 1) rather than (1 - 8) in opposite way

            int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2)/**this will center the pixels */;//this lines helps us to render the chars depends on its size...
        
            // same rule applies for x-axis as above(y-axis)
            for (int x = 0; x < 8; x++) {
                int xSheet = x;
                if (mirrorX) xSheet = 7 - x;//if morrow x is true than xSheet = 7-x beause of your tile size (8x8)
                int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);//sme with this like y-pixel
                int col = (color >> (sheet.pixels[xSheet/**aka x-row */ + (ySheet * sheet.width/**in this case 32 */) + tileOffset/**x < 8x8 */] * 8)) &/**make sure we are in between 0-255 */ 255;
                if (col < 255) {//if the color is less tan 255 then...
                    for (int yScale = 0; yScale < scale; yScale++) {//loop for Yscaling the mob
                        //we are finding the y pos first then find x pos then do the calc... thats why x' loop has extra datas
                        if((yPixel + yScale < 0) || (yPixel + yScale >= height/**screen's height' */)) continue;//if the y pixel + y scale is less than 0 and the window's hight then countinu rendering...
                        for (int xScale = 0; xScale < scale; xScale++) {//loop for xScailing the mob
                            if((xPixel + xScale < 0) || (xPixel + xScale >= width)) continue;//"contine" is like a short while loop
                            /**then */ pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;//if the color is inside the target value... then do this calc or do none
                            
                        }
                    }
                }           
            }   
        }
    }

	public void setOffset(int xOffset, int yOffset) {//method for offset (map)
        this.xOffset = xOffset;//syncing value
        this.yOffset = yOffset;//syncing
        
	}
}