package com.daniel.robopocalypse.game.gfx;
import com.daniel.robopocalypse.game.entites.*;

/**
 * font
 */
public class font {

    private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " +
    "0123456789.,:;'\"!?$%()-=+/      ";

    public static void render(String msg, Screen screen, int x, int y, int color , int scale){
        msg = msg.toUpperCase();//making all the texts to uppercased if it is in lowercase

        for (int i = 0; i < msg.length(); i++) {
            int charIndex = chars.indexOf(msg.charAt(i));//it finds the pixel the string (1px = 1space)
            if (charIndex >= 0) screen.render(x + (i * 8/**because of 8x8 is one tile size in our sprite sheet*/),y ,charIndex + (30/**line 30 in sprite sheet */ * 32/**32 tiles in each row */), color, 0x00, scale); //render aslong it is a valid value
        }
    }
}