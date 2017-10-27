package com.main.launcher;

import com.main.UI.HUD;
import com.main.UI.Menu;
import com.main.UI.Window;
import com.main.logic.GameObject;
import com.main.logic.ID;
import com.main.logic.Handler;
import com.main.logic.Spawner;
import com.main.player.Bullet;
import com.main.player.IMovingHandler;
import com.main.player.KeyInputClass;
import com.main.player.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Game extends Canvas implements Runnable, IControlEventHandler {

    private static final long serialVersionUID = 4088146271165387233L;
    public static final int WIDTH = 1000, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Menu menu;

    @Override
    public void shoot() {
        for (int i = 0; i < handler.object.size(); i++) {

            GameObject player = handler.object.get(i);

            if (player.getId() == ID.Player && Player.bullets > 0) {
                handler.addObject(new Bullet(player.x+30, player.y, ID.Bullet, handler));
                System.out.println(Player.bullets);
                Player.bullets--;
            }

        }
    }



    public enum STATE {
        Menu,
        Help,
        Game
    }

    public static STATE gameState = STATE.Menu;

    public Game() {

        handler = new Handler();

        menu = new Menu(this, handler);

        //this.addKeyListener(new KeyInputClass(handler, this));
        this.addMouseListener(menu);

        hud = new HUD();
        spawner = new Spawner(handler, hud);

        new Window(WIDTH, HEIGHT, "Game", this);

        r = new Random();


    }


    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        if (gameState == STATE.Game) {
            hud.tick();
            spawner.tick();
        } else if (gameState == STATE.Menu)
            menu.tick();

    }

    Image image;

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        if (gameState == STATE.Game)
            hud.render(g);
        else if (gameState == STATE.Menu || gameState == STATE.Help)
            menu.render(g);
        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max) {
        if (var >= max)
            return var = max;
        else if (var <= min)
            return var = min;
        return var;
    }

    public static void main(String[] args) {
        new Game();

    }
}
