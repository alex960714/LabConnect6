package com.appgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chip extends JPanel {
    private BufferedImage image;
    private Integer fieldX, fieldY;
    private int color;

    public Chip(int fieldX, int fieldY, int clr){
        this.fieldX=fieldX;
        this.fieldY=fieldY;
        this.color=clr;

        try {
            if(this.color==1) {
                this.image = ImageIO.read(new File("resources\\white.gif"));
            }
            else{
                this.image = ImageIO.read(new File("resources\\black.gif"));
            }
        } catch (IOException ex) {
            String message = "Unable to draw the stone. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, this.fieldX, this.fieldY, this);

    }
}