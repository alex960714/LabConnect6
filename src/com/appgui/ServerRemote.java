package com.appgui;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRemote extends Remote{
    int getColor() throws RemoteException;
    //int[][] gameFieldStatus() throws RemoteException;
    int getWinner() throws RemoteException;
    void setMove(int player, int[] _changes) throws RemoteException;
    int getMove() throws RemoteException;
    int[] getChanges() throws RemoteException;
}
