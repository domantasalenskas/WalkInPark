package com.main.falling.objects;

import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;
import com.main.logic.ID;
import com.main.player.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by ulis on 2017-09-27.
 */
public class SpeedBoost extends GameObject {
    private Handler handler;

    int size = 32;

    private final long createdMillis = System.currentTimeMillis();

    public SpeedBoost(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = 0;
        velY = 5;
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, size, size * 2);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y >= Game.HEIGHT - 32) {
            handler.removeObject(this);
        }
        if (x <= 0 || x >= Game.WIDTH - 32) {
            velX = velX * -1;
        }

    }

    Image image;

    @Override
    public void render(Graphics g) {

        ImageIcon i = new ImageIcon(this.getClass().getResource("/Images/speedBoost.png"));
        image = i.getImage();
        g.drawImage(image, (int) x, (int) y, size, size * 2, null);
    }
}
