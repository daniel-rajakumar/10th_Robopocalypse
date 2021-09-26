package com.daniel.robopocalypse.game.level.tiles;

import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.gfx.color;
import com.daniel.robopocalypse.game.level.Level;


public abstract class Tile {

    public static final Tile[] tiles = new Tile[256];//256 is the maximum about of tile we could possiable have.
    public static final Tile VOID = new BasicSolidTile(0, 0, 0, color.get(000, -1, -1, -1), 0xFF000000);//giving the location and teh color for the tile
    public static final Tile STONE = new BasicSolidTile(1, 1, 0, color.get(-1, 333, -1, -1), 0xFF555555);//giving the location and the color for the stone
    public static final Tile GRASS = new basicTile(2, 2, 0, color.get(-1, 131, 141, -1), 0xFF00FF00);//giving the location and the color for the grass
    public static final Tile WATER = new AnimatedTile(3, new int[][] {{0,5}/**line 5 and 1st tile... in our sprite sheet */, {1,5}, {2,5}, {1,5}}/**follow this patten {x , y} tile in our sprite sheet */, color.get(-1, 004, 115, -1), 0xFF0000FF, 500);//giving the location and the color for the water
    

    protected byte id;//location of tile
    protected boolean solid;//like a wall
    protected boolean emitter;//like a path
    private int levelColor;//color of the level

    public Tile(int id, boolean isSolid, boolean isEmitter , int levelColor){
        this.id = (byte)id;//changing id to byte balue to make the run time faster ans smoother
        if (tiles[id] != null) throw new RuntimeException("Duplicated tile id on " + id);//this should never happend if somehow happen it will throw error called "Duplic........on "
        this.solid = isSolid;//syncing value
        this.emitter = isEmitter;//syncing value
        this.levelColor = levelColor;//syncing
        tiles[id] = this; //syncing value     
        
    }

    public byte getId(){
        return id;
    }

    public boolean isSolid(){
        return solid;
    }

    public boolean isEmitter(){
        return emitter;
    }

	public int getLevelColor() {
		return levelColor;
    }

    public abstract void tick();
    
	public abstract void render(Screen screen, Level level, int x, int y);

}
