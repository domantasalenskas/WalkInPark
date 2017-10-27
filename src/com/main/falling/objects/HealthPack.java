package com.main.falling.objects;

import com.main.logic.ID;
import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;

import java.awt.*;
import java.util.Random;

public class HealthPack extends GameObject {

    private Handler handler;

    Random r = new Random();

    public HealthPack(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 0;
        velY = 5;
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, 40,40);
    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y >= Game.HEIGHT-32){
            handler.removeObject(this);
        }
        if(x <= 0 || x >= Game.WIDTH-32){
            velX = velX * -1;
        }

    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.red);
        g.fillRect((int)x-8,(int)y+16,16,32);
        g.fillRect((int)x-16,(int)y+24,32,16);
    }
}