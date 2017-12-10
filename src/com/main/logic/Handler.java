package com.main.logic;

import com.main.player.Player;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by ulis on 2017-07-14.
 */
public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    long timer = System.currentTimeMillis();

    public static int DEFAULT_GAME_TIME = 60;
    int gameTime = DEFAULT_GAME_TIME;

    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);

            tempObject.tick();
        }

        if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            if (gameTime > 0) {
                gameTime--;
                System.out.println(gameTime);
                if (gameTime == 0) {
                    Player.HEALTH = 0;
                }
            }
        }

    }

    public Player getPlayer() {
        for (int i = 0; i < object.size(); i++) {

            GameObject player = object.get(i);

            if (player.getId() == ID.Player) {
                return (Player) player;
            }

        }
        return null;
    }

    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void clearEnemys(){
        for(int i = object.size()-1; i > 0; i--){
            removeObject(object.get(i));
        }
    }
    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }
}

