package com.appgui;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerRemote {

    private List<Integer> gameField;

    public Server() {
        gameField=new ArrayList<>();
    }

    @Override
    public String sayHello() throws RemoteException {
        return null;
    }

    @Override
    public List<Integer> gameFieldStatus() throws RemoteException {
        return null;
    }

    public static void main(String args[]){
        try {
            Server obj = new Server();
            ServerRemote stub = (ServerRemote) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
