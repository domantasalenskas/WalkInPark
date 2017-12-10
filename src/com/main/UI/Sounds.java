package com.main.UI;

import com.main.launcher.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Sounds {
    Clip clipGame;
    Clip clipMenu;
    Clip clipScore;
    Clip clipDeath;

    boolean init = true;
    boolean playSoundGame = true;
    boolean playSoundDeath = true;
    boolean playSoundMenu = true;

    public void tick() {
        if (Game.gameState == Game.STATE.Menu) {
            if (playSoundMenu) {
                playMenuSong();
                playSoundMenu = false;

                playSoundGame = true;
                playSoundDeath = true;
            }
        } else if (Game.gameState == Game.STATE.Game) {
            if (playSoundGame) {
                playGameSong();
                playSoundGame = false;

                playSoundMenu = true;
                playSoundDeath = true;
            }
        } else if (Game.gameState == Game.STATE.Dead) {
            if (playSoundDeath) {
                playDeathSound();
                playSoundDeath = false;

                playSoundGame = true;
                playSoundMenu = true;
            }
        }

    }

    private void playSound(String soundName) {
        try {
            if (init) { //resetinam soundus
                clipGame = AudioSystem.getClip();
                clipMenu = AudioSystem.getClip();
                clipScore = AudioSystem.getClip();
                clipDeath = AudioSystem.getClip();
                init = false;
            }
            // Open an audio input stream.
            InputStream url = ClassLoader.getSystemResourceAsStream("Sounds/" + soundName + ".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a sound clip resource.
            if (soundName == "game") {
                clipMenu.stop();
                clipMenu = AudioSystem.getClip();
                clipGame.open(audioIn);
                clipGame.start();
            } else if (soundName == "death") {
                clipGame.stop();
                clipGame = AudioSystem.getClip();
                clipDeath.open(audioIn);
                clipDeath.start();
            } else if (soundName == "menu") {
                clipDeath.stop();
                clipDeath = AudioSystem.getClip();
                clipMenu.open(audioIn);
                clipMenu.start();
                clipMenu.loop(10);
            }


        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void itemSound(String soundName) {
        try {
            InputStream url = ClassLoader.getSystemResourceAsStream("Sounds/" + soundName + ".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e){

        }

    }

    public void playGameSong() {
        playSound("game");
    }

    public void playScoreSound() {
        itemSound("score2");
    }

    public void playEnemySound() {
        itemSound("knife");
    }

    public void playDeathSound() {
        playSound("death");
    }

    public void playMenuSong() {
        playSound("menu");
    }

    public void playSpeedSound(){
        itemSound("speed");
    }
    public void playBulletSound(){
        itemSound("shoot");
    }
    public void playShatterSound(){
        itemSound("shatter");
    }
    public void playShieldBlockSound(){
        itemSound("block");
    }
    public void playHealthSound(){
        itemSound("health");
    }
    public void playGunSound(){
        itemSound("gun");
    }
    public void playShieldSound(){
        itemSound("shield");
    }
}
