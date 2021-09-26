package com.daniel.robopocalypse.game.entites;

import com.daniel.robopocalypse.game.gfx.*;
import com.daniel.robopocalypse.game.level.*;

/**
 * Entity
 */
public abstract class Entity {

    public double x, y;// x and y directions
    protected Level level;//level

    public Entity(Level level){//setting up the level
        init(level);
    }

    public final void init(Level level){//"final" method for level
        this.level = level;//syncing the level
    }

    public abstract void tick();//this class need a method in it

    public abstract void render(Screen screen);//screen rendering method
    
    
}