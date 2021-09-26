package com.daniel.robopocalypse.game.level.tiles;


/**
 * BasicSolidTile
 */
public class BasicSolidTile extends basicTile {

    public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor) {
        super(id, x, y, tileColor, levelColor);
        this.solid = true;//this tile is solid so the mobs can not pass through it
    }

}