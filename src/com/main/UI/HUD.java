package com.main.UI;

import com.main.launcher.Game;
import com.main.player.Player;

import java.awt.*;

/**
 * Created by ulis on 2017-07-19.
 */
public class HUD {

    private int greenValue = 255;
    public static int score = 0;
    private int level = 0;

    public void tick(){
        Player.HEALTH = Game.clamp(Player.HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue,0,255);
        greenValue = Player.HEALTH*2;
    }
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(15, 15, Player.HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);

        g.drawString("Score " + score, 14, 64);
        g.drawString("Level " + level, 15, 82);
        if(Player.HEALTH < 1){
           // g.drawString("GG" + level, Game.WIDTH/2, Game.HEIGHT/2);
            //Player.playerImage = "./src/com/main/Images/Player/playerRight.gif";
        }

    }

    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }
}
