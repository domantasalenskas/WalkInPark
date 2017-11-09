package com.main.UI;

import com.main.DbConnect;
import com.main.logic.ID;
import com.main.launcher.Game;
import com.main.logic.Handler;
import com.main.player.KeyInputClass;
import com.main.player.Player;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by ulis on 2017-08-28.
 */
public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private DbConnect db;

    public Menu(Game game, Handler handler, DbConnect db) {
        this.game = game;
        this.handler = handler;
        this.db = db;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Game.gameState == Game.STATE.Menu || game.gameState == Game.STATE.Help || game.gameState ==Game.STATE.Scoreboard) {
            //play button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 200, 300, 80)) {
                game.gameState = Game.STATE.Game;
                HUD.score = 0;
                game.addKeyListener(new KeyInputClass(handler, game));
            }
            //help button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 300, 300, 80)) {
                game.gameState = Game.STATE.Help;
            }
            //help back button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80)){
                game.gameState = Game.STATE.Menu;
            }

            //quit button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 500, 300, 80)) {
                System.exit(1);
            }
            //scoreboard button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 400, 300, 80)) {
                game.gameState = Game.STATE.Scoreboard;
            }
            //scoreboard back button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80)){
                game.gameState = Game.STATE.Menu;
            }

        }
        // DEAD GAME STATE
        if(Game.gameState == Game.STATE.Dead) {
            if (mouseOver(mx, my, 45, 605, 180, 85)) {
                System.exit(1);
            }

             if (mouseOver(mx, my, 745, 607, 210, 65)) {
                game.gameState = Game.STATE.Menu;
                handler.object.remove(0);
            }
             if(mouseOver(mx,my, Game.WIDTH / 2-170,490,320,50)){
                game.gameState = Game.STATE.SubmitScore;
                handler.object.remove(0);
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

    }

    public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            } else return false;
        } else return false;

    }

    public void tick() {

    }

    public void render(Graphics g) {
        Font fnt2 = new Font("arial", 1, 50);
        if (game.gameState == Game.STATE.Menu) {
            Font fnt = new Font("arial", 1, 70);
            g.setColor(Color.WHITE);

            g.setFont(fnt);
            g.drawString("Menu", Game.WIDTH / 2 - 110, 100);

            g.setFont(fnt2);
            g.drawString("Start", Game.WIDTH / 2 - 80, 255);
            g.drawRect(Game.WIDTH / 2 - 170, 200, 300, 80);
            g.drawString("Help", Game.WIDTH / 2 - 80, 355);
            g.drawRect(Game.WIDTH / 2 - 170, 300, 300, 80);
            g.drawString("Scoreboard", Game.WIDTH / 2 - 160, 455);
            g.drawRect(Game.WIDTH / 2 - 170, 400, 300, 80);
            g.drawString("Quit", Game.WIDTH / 2 - 80, 555);
            g.drawRect(Game.WIDTH / 2 - 170, 500, 300, 80);
        } else if (game.gameState == Game.STATE.Help) {
            g.setFont(fnt2);
            g.setColor(Color.WHITE);
            g.drawString("To play this game use AD to move ", 100, 100);
            g.drawString("and SPACE to shoot.", 100, 170);

            g.drawRect(Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80);
            g.drawString("Back", Game.WIDTH / 2 - 80, -(95 - Game.HEIGHT));

        } else if (game.gameState == Game.STATE.Dead){
            g.drawRect(Game.WIDTH / 2-170,490,320,50);

        } else if (game.gameState == Game.STATE.Scoreboard){
            LinkedList<String> list = db.getData();
            int slideImageCounter = 2;
            for(int i = 0; i < 9; i++){
                Font fnt = new Font("arial", 1, 30);
                g.setFont(fnt);
                g.setColor(Color.WHITE);
                g.drawString(list.get(i*2), Game.WIDTH/6, 60*slideImageCounter+1);
                g.drawString(list.get(i*2+1), Game.WIDTH*4/6, 60*slideImageCounter+1);
                slideImageCounter++;
            }
            g.setFont(new Font("arial", 1, 70));
            g.drawRect(Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80);
            g.drawString("Back", Game.WIDTH / 2 - 110, Game.HEIGHT - 85);
        }
    }

}
