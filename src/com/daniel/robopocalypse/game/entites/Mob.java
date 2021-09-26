package com.daniel.robopocalypse.game.entites;

import com.daniel.robopocalypse.game.level.Level;
import com.daniel.robopocalypse.game.level.tiles.Tile;

/**
 * Mob
 */
public abstract class Mob extends Entity {//this will be same for every single type of mob(player, emenie etc...)

    protected String name;//name of the Mob
    protected int speed;//the speed (movenemt)
    protected int numSteps = 0;//tracking the steps of mob...
    protected boolean isMoving;//moving or not?
    protected int movingDir = 1;//moving direction (0 = facing the camera... 1 = not facing the camera;) 
    protected int scale = 1;//size
    protected int health;//health

    public Mob(Level level, String name, int x, int y, int health, int speed) {//a method for MOB
        super(level);
        this.name = name;//syncing
        this.x = x;//syncing
        this.y = y;//syncing
        this.speed = speed;//sycing
        this.health = health;//syncing
    }

    public void move(int xa/**left(-)/right(+) */, int ya/**up(+)/down(-) */) {//move function/ method... and if 0 means standing still
        if (xa != 0 && ya != 0) {//0 means standing still, if the mob is not standing then do this...
            move(xa, 0);//move (left/right) and DO-NOT move (up/down)
            move(0, ya);//move (up/down) and DO-NOT move (left/right)
            numSteps--;//why are we (-=1)? because it count as 2 moves since we have 2 vriables above... one for x- axies move, and second is for y-axies moves... so (-=1) make the count equal (1 step = 1 move) direction does not matter.
            return;
        }

        numSteps++;//add one step...

        if (!hasCollided(xa, ya)) {// the mob can not pass/walk throught this area...(like inside a stone...building...enemy...lakes....any objects...etc...)
            //0-3 = east,west,north and south
            if (ya < 0) {//south(-) down
                movingDir = 0;
            }
            if (ya > 0) {//north(+) up
                movingDir = 1;
            }
            if (xa < 0) {//west(-) left
                movingDir = 2;
            }
            if (xa > 0) {//east(+) right
                movingDir = 3;
            }

            x += xa * speed/100.0;//direction(east/west) * speed
            y += ya * speed/100.0;//direction(north/south) * speed
        }
    }

    public abstract boolean hasCollided(int xa, int ya);//method for what if the mob collided

    protected boolean isSolidTile(int xa/**how much you moving(distance) */,int ya, int x, int y){
        if (level == null) {
            return false;
        }
        Tile lastTile = level.getTile(((int)this.x + x) >> 3, ((int)this.y + y) >> 3);//telling java the last tile 
        Tile newTile = level.getTile(((int)this.x + x + xa/**the new tile */) >> 3, ((int)this.y + y + ya >> 3));//telling java the new like aka the current tile
        if (!lastTile.equals(newTile) && newTile.isSolid()) {//if it is a solid object and last tile and the new tile are not equal then...
            return true;
        }
        return false;//if nothing happends then return false!
    }
        
    public String getName() {//get the name of the mob... like your name, hero, enemy, villan, opponent etc...
        return name;
    }
}