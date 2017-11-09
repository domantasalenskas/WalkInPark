package com.main.UI;

import com.main.DbConnect;
import com.main.launcher.Game;
import com.main.logic.Handler;
import com.main.player.KeyInputClass;
import com.main.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by ulis on 2017-10-29.
 */
public class ScoreSubmission{
    private DbConnect db;
    private String name;
    public boolean submitted = false;

    public ScoreSubmission(DbConnect db) {
        this.db = db;


    }
    public void submit(){
        if(!submitted) {
            System.out.println(HUD.score);
            name = JOptionPane.showInputDialog(null, "Enter your name");
            if (name != null)
                db.insertData(name, HUD.score);
            db.getData();
            submitted = true;
            Game.gameState = Game.STATE.Menu;
        }
    }
    public void tick(){
        submit();
    }


}
