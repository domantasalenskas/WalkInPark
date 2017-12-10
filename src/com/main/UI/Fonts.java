package com.main.UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {
    public Fonts(){
        registerFont();
    }

    public void registerFont(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./resources/fonts/OptimusPrinceps.ttf")));
        } catch (IOException | FontFormatException e) {

        }
    }
}
