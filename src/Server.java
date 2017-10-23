import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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

    }
}
