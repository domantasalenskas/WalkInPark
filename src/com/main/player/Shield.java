package com.main.player;

import com.main.logic.GameObject;
import com.main.UI.HUD;
import com.main.logic.Handler;
import com.main.logic.ID;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Shield extends GameObject {
    private Handler handler;
    public static int shieldInstances = 2;

    Random r = new Random();

    int size = 32;

    public Shield(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 0;
        velY = 0;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y-50, size * 3, size);
    }

    @Override
    public void tick() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Player) {
                this.x = tempObject.x;
                this.y = tempObject.y;
            }
        }
        collision();
    }

    Image image;

    @Override
    public void render(Graphics g) {

        ImageIcon i = new ImageIcon(this.getClass().getResource("/Images/shield.png"));
        image = i.getImage();
        g.drawImage(image, (int) x - 6, (int) y - 30, size * 3, size, null);
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.ScoreObject) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    HUD.score += 100; //score increment

                }
            }
            if (tempObject.getId() == ID.Enemy) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    shieldInstances--;
                    if(shieldInstances == 0){
                        handler.removeObject(this);
                    }
                }
            }
            if (tempObject.getId() == ID.HealthPack) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    Player.HEALTH += 50;
                    handler.removeObject(tempObject);
                }
            }
            if (tempObject.getId() == ID.ShieldUpgrade) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(tempObject);
                    for (int j = 0; j < handler.object.size(); j++) { // deletes old shield
                        GameObject shield = handler.object.get(j);
                        if (shield.getId() == ID.Shield) {
                            handler.removeObject(shield);
                        }
                    }
                    handler.addObject(new Shield(x, y, ID.Shield, handler));
                    shieldInstances = 2;
                }
            }

        }
    }

}
