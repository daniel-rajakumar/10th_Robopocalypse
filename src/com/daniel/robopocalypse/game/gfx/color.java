package com.daniel.robopocalypse.game.gfx;

/**
 * color
 */
public class color {

    //colors in the sprite sheet(4 colors)
    public static int get(int color1/**black */,int color2/**dark */,int color3/**light */,int color4/**white */){
        return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1);//it will print alrage number (24-25 digits value of binary numbers(0 & 1)) 
    }

    private static int get(int color) {//get the value from the user
        if (color < 0) return 255;//if we give negative number, it will become invesiable/transprent
        //color.get(red,green,blue,none) 
        //%10 because we need value from 0-10 so div by 100,10,1 make sense
        int red =   color/100 % 10;//div red by 100 because java need to find the path of the color in the color.get(red,x,x,x) 
        int green = color/10  % 10;//color.get(x,green,x,x) for "/10"
        int blue =  color/1   % 10;//color.get(x,x,blue,x) fpr "/1"
        return (red * 36) + (green * 6 /**R, G, B, rR, gG, bB in the main file class*/ ) + blue;
    }
}