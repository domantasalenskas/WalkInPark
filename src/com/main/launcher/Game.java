package com.main.launcher;

import com.main.DbConnect;
import com.main.UI.*;
import com.main.UI.Menu;
import com.main.UI.Window;
import com.main.logic.GameObject;
import com.main.logic.ID;
import com.main.logic.Handler;
import com.main.logic.Spawner;
import com.main.player.Bullet;
import com.main.player.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;


public class Game extends Canvas implements Runnable, IControlEventHandler {

    private static final long serialVersionUID = 4088146271165387233L;
    public static final int WIDTH = 1024, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;

    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawner spawner;
    private Menu menu;
    private DbConnect db;
    private Window window;
    private ScoreSubmission scoreSubmission;
    public static STATE gameState = STATE.Menu;
    private Sounds sounds;

    @Override
    public void shoot() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Player) {
                Player player = (Player) tempObject;
                if (player.bullets != 0) {
                    handler.addObject(new Bullet(player.x + 30, player.y, ID.Bullet, handler));
                    sounds.playBulletSound();
                    player.bullets--;
                }
            }
        }
    }


    public enum STATE {
        Menu,
        Help,
        Game,
        Dead,
        SubmitScore,
        Scoreboard
    }


    public Game() {

        handler = new Handler();


        db = new DbConnect();

        scoreSubmission = new ScoreSubmission(db);

        menu = new Menu(this, handler, db);

        sounds = new Sounds();

        this.addMouseListener(menu);

        spawner = new Spawner(handler, hud);

        hud = new HUD(handler);

        window = new Window(WIDTH, HEIGHT, "Game", this);

        r = new Random();

        Fonts fonts = new Fonts();

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
        while (running == true) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {

            }

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
        sounds.tick();
        if (gameState == STATE.Game) {
            spawner.tick();

            hud.tick();
            scoreSubmission.submitted = false;
        } else if (gameState == STATE.Menu) {
            menu.tick();

        } else if (gameState == STATE.Dead) {
            menu.tick();
        } else if (gameState == STATE.SubmitScore) {
            scoreSubmission.tick();
        }

    }

    Image image;
    String backgroundImage = "/Images/menu.png";

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        ImageIcon i = new ImageIcon(this.getClass().getResource(backgroundImage));
        image = i.getImage();
        Graphics g = bs.getDrawGraphics();
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(image, 0, 0, null);

        handler.render(g);

        if (gameState == STATE.Game) {
            hud.render(g);
            backgroundImage = "/Images/background.png";
        } else if (gameState == STATE.Menu || gameState == STATE.Help) {
            menu.render(g);
            backgroundImage = "/Images/menu.png";
        } else if (gameState == STATE.Dead) {
            menu.render(g);
            hud.render(g);
            backgroundImage = "/Images/death.png";
        } else if (gameState == STATE.SubmitScore) {
            backgroundImage = "/Images/scoreBackground.jpg";
        } else if (gameState == STATE.Scoreboard) {
            backgroundImage = "/Images/scoreboard.png";
            menu.render(g);
        }
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
