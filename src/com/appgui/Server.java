package com.appgui;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerRemote {

    private int[][] gameField;
    private int lastStroke;
    private int[] changes = {-1,-1,-1,-1};
    private boolean whiteIsConnected;
    private boolean blackIsConnected;

    public Server() {
        gameField=new int[19][19];
        lastStroke = 1;
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
    public int getWinner() {
        int streak , streakPlayer;
        for(int i=0;i<19;i++){ //check rows
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
        for(int i=0;i<19;i++){ //check columns
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
        for(int i=13;i>=0;i--){ //check main diagonals
            streak=0;
            streakPlayer=-1;
            for(int j=0, k=i; k<19; j++,k++){
                if (gameField[k][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[k][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int j=1;j<14;j++){
            streak=0;
            streakPlayer=-1;
            for(int i=0, k=j; k<19; i++, k++){
                if (gameField[i][k] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][k] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int i=5;i<19;i++){ //check secondary diagonals
            streak=0;
            streakPlayer=-1;
            for(int j=0, k=i; k>=0; j++, k--){
                if (gameField[k][j] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[k][j] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        for(int j=1;j<14;j++){
            streak=0;
            streakPlayer=-1;
            for(int i=18, k=j; k<19; i--, k++){
                if (gameField[i][k] == streakPlayer){
                    streak++;
                    if (streak == 6)
                        return streakPlayer;
                }
                else if (gameField[i][k] != 0){
                    streak=1;
                    streakPlayer = -streakPlayer;
                }
                else streak=0;
            }
        }
        return 0;
    }

    @Override
    public void setMove(int player, int[] _changes) {

        for(int i=0;i<4;i++){
            changes[i]=_changes[i];
        }
        gameField[changes[0]][changes[1]]=player;
        if(changes[2]!=-1){
            gameField[changes[2]][changes[3]]=player;
        }
        lastStroke=player;
    }

    @Override
    public int getMove() {
        return lastStroke;
    }

    @Override
    public int[] getChanges() {
        return changes;
    }

    public static void main(String args[]){
        try {
            Server obj = new Server();
            ServerRemote stub = (ServerRemote) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
