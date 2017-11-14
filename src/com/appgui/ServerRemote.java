package com.appgui;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote{
    int getColor() throws RemoteException;
    int getWinner() throws RemoteException;
    void setMove(int player, int[] _changes) throws RemoteException;
    int getMove() throws RemoteException;
    int[] getChanges() throws RemoteException;
}
