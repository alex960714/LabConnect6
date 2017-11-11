package com.appgui;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerRemote {

    private int[][] gameField;
    private int lastStroke;
    private int winner;
    private boolean whiteIsConnected;
    private boolean blackIsConnected;

    public Server() {
        gameField=new int[19][19];
        lastStroke = 1;
        winner = 0;
    }

    @Override
    public int getColor() {
        if(!whiteIsConnected) {
            whiteIsConnected=true;
            return 1;
        }
        else if (!blackIsConnected) {
            blackIsConnected=true;
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int[][] gameFieldStatus() {
        return gameField;
    }

    @Override
    public int getWinner() {
        int streak , streakPlayer;
        for(int i=0;i<19;i++){
            streak=0;
            streakPlayer=-1;
            for(int j=0;j<19;j++){
                if (gameField[i][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int i=0;i<19;i++){
            streak=0;
            streakPlayer=-1;
            for(int j=0;j<19;j++){
                if (gameField[j][i] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[j][i] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int i=13;i>=0;i--){
            streak=0;
            streakPlayer=-1;
            for(int j=0;j<19-i;j++){
                if (gameField[i][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int j=1;j<14;j++){
            streak=0;
            streakPlayer=-1;
            for(int i=0;i<19-j;i++){
                if (gameField[i][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int i=5;i<19;i++){
            streak=0;
            streakPlayer=-1;
            for(int j=0;j<=i;j++){
                if (gameField[i][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int j=1;j<14;j++){
            streak=0;
            streakPlayer=-1;
            for(int i=18;i>=j;i--){
                if (gameField[i][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        return 0;
    }

    @Override
    public void setMove(int player, int[][] _field) {
        gameField=_field;
        lastStroke=player;
    }

    @Override
    public int getMove() {
        return lastStroke;
    }

    public static void main(String args[]){
        try {
            Server obj = new Server();
            ServerRemote stub = (ServerRemote) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
