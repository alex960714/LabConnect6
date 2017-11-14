package com.appgui;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private Image image;

    public Field() {
        image = new ImageIcon("resources\\field.png").getImage();
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
