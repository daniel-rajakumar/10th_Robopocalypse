package com.daniel.robopocalypse.game.level.tiles;

import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.level.Level;

/**
 * basicTile
 */
public class basicTile extends Tile {

    protected int tileId;//tile id number
    protected int tileColor;//the color of the tile

    public basicTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, false, false, levelColor);//default, do not swap axies
        this.tileId = x + y * 32;//giving the id number
        this.tileColor = tileColor;//giving the color
    }

    public void tick(){

    }
    
    public void render(Screen screen, Level level, int x, int y) {//the main render function/method
        screen.render(x, y, tileId, tileColor, 0x00, 1);//rendering the tile
    }
}