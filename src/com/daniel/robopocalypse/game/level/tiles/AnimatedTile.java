package com.daniel.robopocalypse.game.level.tiles;

import com.daniel.robopocalypse.game.gfx.Screen;
import com.daniel.robopocalypse.game.level.Level;

/**
 * AnimatedTile
 */
public class AnimatedTile extends basicTile {

    private int[][] animationTileCoords;//2d array to store 2 numbers(in this case it would be x and y)
    private int currentAnimationIndex;
    private long lastIterationTime;//last millisec we updated
    private int animationSwitchDelay;

    public AnimatedTile(int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay)//making a 2d array to store the x and y pos
     {
        super(id, animationCoords[0][0], animationCoords[0][1], tileColor, levelColor);
        this.animationTileCoords = animationCoords;//syncing
        this.currentAnimationIndex = 0;//syncing
        this.lastIterationTime = System.currentTimeMillis();//syncing
        this.animationSwitchDelay = animationSwitchDelay;//syncing
    }

    public void tick(){
        if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {//updating the animation using time
            lastIterationTime = System.currentTimeMillis();//update the lasttime to now
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;//finding the lenght of the index then moding it(in this case it is 3(tiles))
            this.tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));//updating the tile id
        }
    }
    
    /*
    public void render(Screen screen, Level level, int x, int y) {//the main render function/method
        screen.render(x, y, tileId, tileColor, 0x00, 1);//rendering the tile
    }
    */
}