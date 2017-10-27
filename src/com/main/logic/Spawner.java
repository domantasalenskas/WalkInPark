package com.main.logic;

import com.main.UI.HUD;
import com.main.falling.objects.*;
import com.main.launcher.Game;
import com.main.player.Player;

import java.util.Random;

/**
 * Created by ulis on 2017-07-22.
 */
public class Spawner {

    private HUD hud;
    private Handler handler;
    private Random r = new Random();
    public int scoreObjectRate = 50; // lower = more obj
    public int enemyRate = 60;
    public int healthPackRate = 1000;
    public int shieldRate = 1000;
    public int speedBoostRate = 1000;
    public int shootingUpgradeRate = 1000;


    public Spawner(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }


    public void tick() {
        if ((Game.gameState == Game.STATE.Game) && (Player.HEALTH > 0)) {
            if (r.nextInt(scoreObjectRate) == 1) {
                handler.addObject(new ScoreObject(r.nextInt(Game.WIDTH), 0, ID.ScoreObject, handler));
            }
            if (r.nextInt(enemyRate) == 1) {
                handler.addObject(new Enemy(r.nextInt(Game.WIDTH), 0, ID.Enemy, handler));
            }
            if (r.nextInt(healthPackRate) == 1) {
                handler.addObject(new HealthPack(r.nextInt(Game.WIDTH), 0, ID.HealthPack, handler));
            }
            if (r.nextInt(shieldRate) == 1) {
                handler.addObject(new ShieldUpgrade(r.nextInt(Game.WIDTH), 0, ID.ShieldUpgrade, handler));
            }
            if(r.nextInt(speedBoostRate) == 1){
                handler.addObject(new SpeedBoost(r.nextInt(Game.WIDTH), 0, ID.SpeedBoost, handler));
            }
            if(r.nextInt(shootingUpgradeRate) == 1){
                handler.addObject(new ShootingUpgrade(r.nextInt(Game.WIDTH), 0, ID.ShootingUpgrade, handler));
            }
        } else if (Player.HEALTH == 0) {
            handler.clearEnemys();
        }


    }
}

