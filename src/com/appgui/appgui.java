package com.appgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class appgui extends JPanel {
    private BufferedImage image;

    public appgui() {
        try {
            image = ImageIO.read(new File("resources\\field.png"));
        } catch (IOException ex) {
            String message = "Unable to show the field. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LabConnect6");
        frame.setContentPane(new appgui());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(617, 639);
        frame.setVisible(true);
    }
}
