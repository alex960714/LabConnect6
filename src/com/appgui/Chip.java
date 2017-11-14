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

        if(this.color==1) {
            this.image = new ImageIcon("resources\\white.gif").getImage();
        }
        else {
            this.image = new ImageIcon("resources\\black.gif").getImage();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(this.image, this.fieldX, this.fieldY, this);
    }
}