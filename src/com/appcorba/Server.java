package com.appcorba;

import Connect6.ServerCorb;
import Connect6.ServerCorbHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args) {
        try{
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ServerCorbImpl serverCorb = new ServerCorbImpl();
            serverCorb.setOrb(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serverCorb);
            ServerCorb href = ServerCorbHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Hello";
            NameComponent path[] = ncRef.to_name( name );
            ncRef.rebind(path, href);
            System.out.println("HelloServer ready and waiting ...");
            orb.run();
        } catch (Exception e){
            System.err.println("ERROR: " + e);   e.printStackTrace(System.out);
        }
        System.out.println("HelloServer Exiting ...");
    }
}
