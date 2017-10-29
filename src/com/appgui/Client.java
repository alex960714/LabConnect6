package com.appgui;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private List<Integer> gameField = new ArrayList<>();

    private Client() {}

    public static void main(String args[]){
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            ServerRemote stub = (ServerRemote) registry.lookup("Hello");
            System.out.println(stub);

            String response = stub.sayHello();
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
