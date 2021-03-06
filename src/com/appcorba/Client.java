package com.appcorba;

import Connect6.ServerCorb;
import Connect6.ServerCorbHelper;
import com.appgui.Chip;
import com.appgui.Field;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Client {
    private static int gameField[][];
    private static int color;
    private static JFrame frame;
    private static Integer clickX, clickY;
    private static int[] changes = {-1,-1,-1,-1};
    private static ServerCorb clientCorb;

    private Client() {}

    private static void setConnection(String[] args){
        String host = (args.length < 1) ? null : args[0];
        try {
            ORB orb = ORB.init(args, null);
            Object objRef =  orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Hello";
            clientCorb = ServerCorbHelper.narrow(ncRef.resolve_str(name));
            System.out.println("Obtained a handle on server object: " + clientCorb);

            int response = clientCorb.getColor();
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
            String message = "Unable to connect the server. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null,message,"FATAL ERROR",JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }

    public static void main(String args[]){
        frame = new JFrame("LabConnect6");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(618, 639);
        frame.add(new Field());
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
        int winner = 0;
        if (color == 1){
            stepsRemain=0;
        }
        else{
            stepsRemain=1;
        }
        try {
            while (true) {
                clickX=-1;
                clickY=-1;
                while (stepsRemain > 0) {
                    cellX = (clickX - 4)/31;
                    cellY = (clickY - 4)/31 - 1;
                    if (cellX>=0 && cellX<19 && cellY>=0 && cellY<19 && gameField[cellX][cellY] == 0) {
                        gameField[cellX][cellY] = color;
                        chipX = 4 + cellX * 31 + 10;
                        chipY = 4 + cellY * 31 + 10;
                        frame.add(new Chip(chipX, chipY, color));
                        frame.setVisible(true);
                        chipX = chipY = -1;
                        changes[stepsRemain * 2 - 2] = cellX;
                        changes[stepsRemain * 2 - 1] = cellY;
                        stepsRemain--;
                    }
                    clickX = clickY = -1;
                    if (stepsRemain == 0) {
                        clientCorb.setMove(color, changes);
                        for(int i=0;i<4;i++) {
                            changes[i] = -1;
                        }
                        winner = clientCorb.getWinner();
                        if (winner == color) {
                            String message = "Congratulations! You are winner!";
                            JOptionPane.showMessageDialog(null, message, "YOU WIN!!!", JOptionPane.PLAIN_MESSAGE);
                            break;
                        }
                    }
                    Thread.sleep(15);
                }

                if (clientCorb.getMove() == -color) {

                    changes= clientCorb.getChanges();

                    gameField[changes[0]][changes[1]]=-color;
                    chipX=4 + changes[0]*31 + 10;
                    chipY=4 + changes[1]*31 + 10;
                    frame.add(new Chip(chipX, chipY, -color));
                    frame.setVisible(true);

                    if(changes[2]!=-1){
                        gameField[changes[2]][changes[3]]=-color;
                        chipX=4 + changes[2]*31 + 10;
                        chipY=4 + changes[3]*31 + 10;
                        frame.add(new Chip(chipX, chipY, -color));
                        frame.setVisible(true);
                    }
                    for(int i=0;i<4;i++){
                        changes[i]=-1;
                    }
                    chipX=chipY=-1;
                    winner = clientCorb.getWinner();
                    if (winner == -color) {
                        String message = "Opponent is winner";
                        JOptionPane.showMessageDialog(null, message, "YOU LOST!!!", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                    stepsRemain = 2;

                }
                Thread.sleep(10);
            }
        } catch (Exception e) {
            String message = "Unable to connect the server. Sorry, the application will be closed";
            JOptionPane.showMessageDialog(null, message, "FATAL ERROR", JOptionPane.PLAIN_MESSAGE);
            System.exit(-1);
        }
    }
}
