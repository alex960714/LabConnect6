import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerRemote extends Remote{
    String sayHello() throws RemoteException;
    List<Integer> gameFieldStatus() throws RemoteException;
}
