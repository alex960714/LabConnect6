package com.appgui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
    private static int gameField[][];
    private static int color;
    private static JFrame frame;
    private static Integer clickX, clickY;
    private static int[] changes = {-1,-1,-1,-1};
    private static Registry registry;
    private static ServerRemote stub;

    private Client() {}

    private static void setConnection(String[] args){
        String host = (args.length < 1) ? null : args[0];
        try {
            registry = LocateRegistry.getRegistry(host);
            stub = (ServerRemote) registry.lookup("Hello");
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

    private static class Chip extends JPanel{
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
            /*try {
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
            }*/
            super.paintComponent(g);

            /*String message = "You are in a loop!" + this.fieldX.toString() + " " + this.fieldY.toString();
            JOptionPane.showMessageDialog(null, message, "FATAL ERROR", JOptionPane.PLAIN_MESSAGE);*/
            g.drawImage(this.image, this.fieldX, this.fieldY, this);

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
        Integer cellX = -1, cellY = -1;
        int chipX = -1, chipY = -1;
        clickX=-1;
        clickY=-1;
        if (color == 1){
            stepsRemain=0;
        }
        else{
            stepsRemain=1;
        }
        try {
            while (true) {
                while (stepsRemain > 0) {
                    if(clickX>=0 && clickX<=frame.getWidth() && clickY>=0 && clickY<=frame.getHeight()) {
                        cellX = Math.round(19 * ((float) clickX) / frame.getWidth());
                        cellY = Math.round(19 * ((float) clickY) / frame.getHeight());
                        if (gameField[cellX][cellY] == 0) {
                            gameField[cellX][cellY] = color;
                            chipX=cellX*(int)((frame.getWidth()/19)+0.5);
                            chipY=cellY*(int)((frame.getHeight()/19)+0.5);
                            frame.add(new Chip(chipX, chipY, color));
                            frame.setVisible(true);
                            changes[stepsRemain*2-2]=cellX;
                            changes[stepsRemain*2-1]=cellY;
                            stepsRemain--;
                        }
                        clickX = clickY = -1;
                        if (stepsRemain == 0) {
                            stub.setMove(color, changes);
                            for(int i=0;i<4;i++){
                                changes[i]=-1;
                            }
                        }
                    }
                    Thread.sleep(15);
                }

                if (stub.getMove() == -color) {
                    stepsRemain = 2;
                    //gameField = stub.gameFieldStatus();
                    changes=stub.getChanges();

                    gameField[changes[0]][changes[1]]=-color;
                    chipX=changes[0]*(int)((frame.getWidth()/19)+0.5);
                    chipY=changes[1]*(int)((frame.getHeight()/19)+0.5);
                    frame.add(new Chip(chipX, chipY, color));
                    frame.setVisible(true);

                    if(changes[2]!=-1){
                        gameField[changes[2]][changes[3]]=-color;
                        chipX=changes[2]*(int)((frame.getWidth()/19)+0.5);
                        chipY=changes[3]*(int)((frame.getHeight()/19)+0.5);
                        frame.add(new Chip(chipX, chipY, color));
                        frame.setVisible(true);
                    }
                    for(int i=0;i<4;i++){
                        changes[i]=-1;
                    }

                    int winner = stub.getWinner();
                    if (winner == color) {
                        String message = "Congratulations! You are winner!";
                        JOptionPane.showMessageDialog(null, message, "YOU WIN!!!", JOptionPane.PLAIN_MESSAGE);
                        break;
                    } else if (winner == -color) {
                        String message = "Opponent is winner";
                        JOptionPane.showMessageDialog(null, message, "YOU LOST!!!", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }
                Thread.sleep(10);
            }
        } catch (RemoteException e) {
            String message = "Unable to connect the server. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null, message, "FATAL ERROR", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
