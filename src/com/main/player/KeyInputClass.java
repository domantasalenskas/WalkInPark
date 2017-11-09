package com.main.player;

import com.main.launcher.IControlEventHandler;
import com.main.logic.GameObject;
import com.main.logic.Handler;
import com.main.logic.ID;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by ulis on 2017-07-19.
 */
public class KeyInputClass extends KeyAdapter {

    private IControlEventHandler controlEventHandler;
    private Handler handler;
    private int[] keyDown = new int[4];
    public boolean shoot = false;
    private int bulletX;
    private int bulletY;
    public static boolean movingLeft = false;
    public static boolean movingRight = false;

    public KeyInputClass(Handler handler, IControlEventHandler controlEventHandler){
        this.handler = handler;
        this.controlEventHandler = controlEventHandler;
        keyDown[2] = 0; //d
        keyDown[3] = 0; //a

        bulletX = 0;
        bulletY = 0;
    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(Player.HEALTH != 0){

             for(int i = 0; i < handler.object.size();i++){
                GameObject tempObject = handler.object.get(i);
                 // PLAYER 1 MOVEMENT SPEEDS.
                if(tempObject.getId() == ID.Player) {

                      if (key == KeyEvent.VK_A) {
                          tempObject.setVelX(-Player.playerSpeed);
                          keyDown[2] = 1;

                      }

                    if (key == KeyEvent.VK_D) {
                        tempObject.setVelX(Player.playerSpeed);
                        keyDown[3] = 1;

                    }
                    if (key == KeyEvent.VK_SPACE) {

                         this.controlEventHandler.shoot();
                    }


                }
            }
            if(key == KeyEvent.VK_ESCAPE) System.exit(1);
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < handler.object.size();i++) {
            GameObject tempObject = handler.object.get(i);

            // PLAYER 1 MOVEMENT SPEEDS.
            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_A)
                {

                    keyDown[2] = 0;
                }

                if (key == KeyEvent.VK_D)
                {
                    keyDown[3] = 0;
                }

                if(keyDown[2] == 0 && keyDown[3] == 0){
                    tempObject.setVelX(0);
                }



            }
        }
    }

}
