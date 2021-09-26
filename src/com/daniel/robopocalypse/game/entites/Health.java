package com.daniel.robopocalypse.game.entites;

import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.level.Level;

/**
 * Heath
 */
public class Health extends Entity {

    private int health;
    private double xPos, yPos;
    private int scale; 

    public Health(Level level, int health, double x, double y) {
        super(level);
        this.health = health;
        
        xPos = x;
        yPos = y;
    }

    public void tick() {
        

        scale = 4;
    }

    public void render(Screen screen) {
        int xOffset;
        int modifier;
        int flipTop;
        int yOffset;
        int xTile = 2;
        int yTIle = 27;
        int colors = color.get(-1, 500, 000, 500);
        for (int i = 0; i < health; i++) {
            screen.render((int)xPos, (int)yPos, xTile + (yTIle) * 32, colors,
                    1, scale);// up-right tile(from our sprite-Sheet/player)  
        }
    }


    
}