package com.appgui;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private Image image;

    public Field() {
        //try {
            image = new ImageIcon("resources\\field.png").getImage();
        /*} catch (IOException ex) {
            String message = "Unable to show the field. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }*/
    }

    public void paint(Graphics g) {
        //super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
