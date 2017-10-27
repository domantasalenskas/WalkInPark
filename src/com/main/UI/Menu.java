package com.main.UI;

import com.main.logic.ID;
import com.main.launcher.Game;
import com.main.logic.Handler;
import com.main.player.KeyInputClass;
import com.main.player.Player;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by ulis on 2017-08-28.
 */
public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private Random r;

    public Menu(Game game, Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Game.gameState == Game.STATE.Menu || game.gameState == Game.STATE.Help) {
            //play button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 300, 300, 80)) {
                game.gameState = Game.STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT - 94, ID.Player, handler));
                game.addKeyListener(new KeyInputClass(handler, game));
            }
            //help button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 500, 300, 80)) {
                game.gameState = Game.STATE.Help;
            }
            //help back button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80))
                game.gameState = Game.STATE.Menu;
            //quit button
            if (mouseOver(mx, my, Game.WIDTH / 2 - 170, 700, 300, 80)) {
                System.exit(1);
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
            g.drawString("Menu", Game.WIDTH / 2 - 100, 100);

            g.setFont(fnt2);
            g.drawString("Start", Game.WIDTH / 2 - 80, 355);
            g.drawRect(Game.WIDTH / 2 - 170, 300, 300, 80);
            g.drawString("Help", Game.WIDTH / 2 - 80, 555);
            g.drawRect(Game.WIDTH / 2 - 170, 500, 300, 80);
            g.drawString("Quit", Game.WIDTH / 2 - 80, 755);
            g.drawRect(Game.WIDTH / 2 - 170, 700, 300, 80);
        } else if (game.gameState == Game.STATE.Help) {
            g.setFont(fnt2);
            g.setColor(Color.WHITE);
            g.drawString("To play this game use AD to move and ", 100, 100);
            g.drawString("SPACE to shoot (shooting upgrade).", 100, 170);

            g.drawRect(Game.WIDTH / 2 - 170, -(150 - Game.HEIGHT), 300, 80);
            g.drawString("Back", Game.WIDTH / 2 - 80, -(95 - Game.HEIGHT));

        }
    }

}
