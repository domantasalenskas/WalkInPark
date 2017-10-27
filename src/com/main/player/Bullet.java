package com.main.player;

import com.main.UI.HUD;
import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;
import com.main.logic.ID;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bullet extends GameObject{
    private Handler handler;

    Random r = new Random();

    private int size = 8;

    public Bullet(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 0;
        velY = -15;
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, size,size*2);
    }
    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y >= Game.HEIGHT-32){
            handler.removeObject(this);
        }

        collision();
    }

    Image image;
    @Override
    public void render(Graphics g) {

        g.setColor(Color.black);
        g.fillOval((int)x,(int)y,size,size);
        //ImageIcon i = new ImageIcon("./src/com/main/Images/enemy.png");
        //image = i.getImage();
        g.drawImage(image, (int)x, (int)y, size, size*2, Color.GRAY, null);
    }
    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    handler.removeObject(this);
                    HUD.score += 500;
                }
            }
        }
    }
}
