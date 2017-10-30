package com.appgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private static int gameField[][];
    private static int color;
    private static JFrame frame;
    private static int clickX=0, clickY=0;

    private Client() {}

    private static void setConnection(String[] args){
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ServerRemote stub = (ServerRemote) registry.lookup("Hello");
            //System.out.println(stub);

            int response = stub.getColor();
            if(response==0){
                String message = "Server is currently busy. Sorry, the application will be closed";
                JOptionPane.showMessageDialog(null,message,"Server is busy!",JOptionPane.PLAIN_MESSAGE);
                System.exit(-1);
            }
            color=response;
            if(color == 1) {
                frame.setTitle("Connect6 - WHITE");
            }
            else {
                frame.setTitle("Connect 6 - BLACK");
            }
        } catch (Exception e) {
            //System.err.println("Client exception: " + e.toString());
            String message = "Unable to connect the server. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
            //e.printStackTrace();
        }
    }

    private class Draw extends JPanel{
        private BufferedImage image;
        private int fieldX, fieldY;
        private int color;

        public Draw(int fieldX, int fieldY, int clr){
            this.fieldX=fieldX;
            this.fieldY=fieldY;
            this.color=clr;

            try {
                if(this.color==1) {
                    this.image = ImageIO.read(new File("resources\\white.png"));
                }
                else{
                    this.image = ImageIO.read(new File("resources\\black.png"));
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
            g.drawImage(this.image, 0, 0, this);
        }
    }

    public static void main(String args[]){
        frame = new JFrame("LabConnect6");
        frame.setContentPane(new appgui());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(617, 639);
        frame.setVisible(true);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clickX = e.getX();
                clickY = e.getY();
            }
        });

        setConnection(args);
        gameField=new int[19][19];

        int stepsRemain;
        int cellX, cellY;
        if (color == 1){
            stepsRemain=0;
        }
        else{
            stepsRemain=1;
        }

        while(true){
            while(stepsRemain>0){
                cellX=Math.round(19*((float)clickX)/frame.getWidth());
                cellY=Math.round(19*((float)clickY)/frame.getHeight());
                if (gameField[cellX][cellY] == 0){
                    gameField[cellX][cellY] = color;
                    //TODO: draw the circle
                    stepsRemain--;
                }
            }
            //TODO: get status from server
        }
    }
}
