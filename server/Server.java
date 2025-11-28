package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) {

        try {
            // Set public IP from NGROK (host)
            System.setProperty("java.rmi.server.hostname", "0.tcp.in.ngrok.io");

            // Create REGISTRY on fixed port (MUST MATCH NGROK PORT 1)
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry started on port 1099");

            // Export BankImpl on fixed port 5000 (MUST MATCH NGROK PORT 2)
            BankImpl obj = new BankImpl();   // already exported automatically
            Naming.rebind("rmi://0.tcp.in.ngrok.io:14725/Bank", obj);

            BankInterface stub = (BankInterface) UnicastRemoteObject.exportObject(obj, 5000);

            // Bind using NGROK's registry port
            Naming.rebind("rmi://0.tcp.in.ngrok.io:14725/Bank", stub);

            System.out.println("==============================================");
            System.out.println("        BANK SERVER RUNNING ON PUBLIC WAN      ");
            System.out.println("==============================================");
            System.out.println("Registry Port     : 1099 (tunneled as 14725)");
            System.out.println("Object Port       : 5000 (tunneled separately)");
            System.out.println("Public RMI URL    : rmi://0.tcp.in.ngrok.io:14725/Bank");
            System.out.println("==============================================");

        } catch (Exception e) {
            System.out.println("Server Startup Error:");
            e.printStackTrace();
        }
    }
}
