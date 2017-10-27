package com.main.player;

import com.main.UI.HUD;
import com.main.falling.objects.ScoreObject;
import com.main.falling.objects.SpeedBoost;
import com.main.logic.ID;
import com.main.launcher.Game;
import com.main.logic.GameObject;
import com.main.logic.Handler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by ulis on 2017-07-14.
 */
public class Player extends GameObject implements IMovingHandler{

    enum PlayerState {
        MovingLeft,
        MovingRight,
        StandingStill,
        Dead
    }

    private PlayerState state;
    public static int HEALTH = 100;
    private Handler handler;
    public static int playerSpeed = 10;
    public static int bullets;
    int imgNumRight = 0;
    int imgNumLeft = 0;
    int imgNumDead = 0;
    int currentTick = 0;
    boolean movingRight = false;
    boolean movingLeft = false;

    int sizeX = 83;
    int sizeY = 122;
    public static String playerImage;
    Image image;


    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.state = PlayerState.StandingStill;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, sizeX, sizeY);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp((int) x, 0, Game.WIDTH - 80);
        y = Game.clamp((int) y, 0, Game.HEIGHT - 150);

        collision();

    }
    // should be somewhere else, because implements shield collision as well

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
                    HEALTH = HEALTH - 25; //damage
                    handler.removeObject(tempObject);
                }
            }
            if (tempObject.getId() == ID.HealthPack) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HEALTH = HEALTH + 50; //heal rate
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
                    Shield.shieldInstances = 2;
                }
            }
            if (tempObject.getId() == ID.SpeedBoost) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    SpeedBoost.boostSpeed(this);
                    handler.removeObject(tempObject);
                }
            }
            if(tempObject.getId() == ID.ShootingUpgrade) {
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(tempObject);
                    bullets = 5;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        ImageIcon i = new ImageIcon(playerImage);
        image = i.getImage();
        currentTick++;
        if (HEALTH == 0) {

            if(currentTick % 20 == 0){
                playerImage = "./src/com/main/Images/Player/Dead/Dead__00" + imgNumDead + ".png";
                if(imgNumDead < 9)
                    imgNumDead++;
            }
            g.drawImage(image, (int) x, (int) y, sizeX + 70, sizeY, Color.GRAY, null);

        } else {

            if (velX < 0 && currentTick > 10) {
                currentTick = 0;
                playerImage = "./src/com/main/Images/Player/Left/Run__00" + imgNumLeft + ".png";
                if(imgNumLeft < 9)
                    imgNumLeft++;
                else imgNumLeft = 0;
            }
            if (velX > 0 && currentTick > 10) {
                currentTick = 0;
                playerImage = "./src/com/main/Images/Player/Right/Run__00" + imgNumRight + ".png";
                if(imgNumRight < 9)
                    imgNumRight++;
                else imgNumRight = 0;
            }
            if(velX == 0){
                playerImage = "./src/com/main/Images/Player/playerForward.png";
            }
            g.drawImage(image, (int) x, (int) y, sizeX, sizeY, Color.GRAY, null);
        }

    }

    @Override
    public void movingLeft() {
        if (this.state == PlayerState.StandingStill){
            this.state = PlayerState.MovingLeft;
            this.movingLeft = true;
        }

    }

    @Override
    public void movingRight() {
        if (this.state == PlayerState.StandingStill){
            this.state = PlayerState.MovingRight;
            this.movingRight = true;
        }
    }

    @Override
    public void standingStill() {
        this.state = PlayerState.StandingStill;
    }

    @Override
    public void stoppedMovingLeft() {
        this.state = PlayerState.StandingStill;
        this.movingLeft = false;
    }

    @Override
    public void stoppedMovingRight() {
        this.state = PlayerState.StandingStill;
        this.movingRight = false;
    }
}

