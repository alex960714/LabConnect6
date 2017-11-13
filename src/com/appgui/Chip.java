package com.appgui;

import javax.swing.*;
import java.awt.*;

public class Chip extends JPanel {
    private Image image;
    private Integer fieldX, fieldY;
    private int color;

    public Chip(int fieldX, int fieldY, int clr){
        this.fieldX=fieldX;
        this.fieldY=fieldY;
        this.color=clr;

        //try {
            if(this.color==1) {
                this.image = new ImageIcon("resources\\white.gif").getImage();
            }
            else{
                this.image = new ImageIcon("resources\\black.gif").getImage();
            }
        /*} catch (IOException ex) {
            String message = "Unable to draw the stone. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }*/
    }

    public void paint(Graphics g) {
        //super.paintComponent(g);
        g.drawImage(this.image, this.fieldX, this.fieldY, this);

    }
}