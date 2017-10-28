package com.main.UI;

import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;
import com.main.logic.ID;
import com.main.player.Player;

import java.awt.*;

/**
 * Created by ulis on 2017-07-19.
 */
public class HUD {

    private int greenValue = 255;
    public static int score = 0;
    private int level = 0;
    private Handler handler;

    public HUD(Handler handler){
        this.handler = handler;
    }
    public void tick(){
        Player.HEALTH = Game.clamp(Player.HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue,0,255);
        greenValue = Player.HEALTH*2;
    }
    public void render(Graphics g) {
        g.setColor(Color.gray);
        int healthBarWitdh = 300;
        g.fillRect(Game.WIDTH / 2 - healthBarWitdh/2, 15, healthBarWitdh, 32);
        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(Game.WIDTH / 2 - healthBarWitdh/2, 15, Player.HEALTH *3, 32);
        g.setColor(Color.WHITE);
        g.drawRect(Game.WIDTH / 2 - healthBarWitdh/2, 15, healthBarWitdh, 32);

        g.setFont(new Font("arial", 1, 15));

        g.drawString("Score " + score, 14, 32);
        if(getPlayer() !=  null) {
            g.drawString("Bullets " + getPlayer().bullets, Game.WIDTH - 100, 32);
            if (getPlayer().speedBoostTime > 0) {
                g.setFont(new Font("arial", 1, 20));
                if (getPlayer().speedBoostTime % 2 == 0 && getPlayer().speedBoostTime > 0)
                    g.setColor(Color.BLACK);
                else g.setColor(Color.WHITE);
                g.drawString("Speed boost " + getPlayer().speedBoostTime, Game.WIDTH / 2 - 75, 70);
            }
        }
    }

    public Player getPlayer(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ID.Player){
                return (Player)tempObject;
            }
        }
        return null;
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
