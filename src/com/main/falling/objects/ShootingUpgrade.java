package com.main.falling.objects;

import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;
import com.main.logic.ID;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by ulis on 2017-09-27.
 */
public class ShootingUpgrade extends GameObject{
    private Handler handler;

    Random r = new Random();

    private int size = 24;

    public ShootingUpgrade(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 0;
        velY = 5;
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, size,size*3);
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

        //handler.addObject(new Trail( x, y, ID.Trail ,Color.red, size,size, 0.07f, handler));
    }

    Image image;
    @Override
    public void render(Graphics g) {

        g.setColor(Color.red);
        g.fillOval((int)x,(int)y,size,size);
        ImageIcon i = new ImageIcon("./src/com/main/Images/shootingUpgrade.png");
        image = i.getImage();
        g.drawImage(image, (int)x, (int)y, size, size*3, Color.GRAY, null);
    }
}
