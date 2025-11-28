package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    public static void main(String[] args) {

        try {
            // Set public IP from NGROK (host) IF USING IT FOR WAN
            System.setProperty("java.rmi.server.hostname", "<YOUR IPV4 PUBLIC IP>");

            LocateRegistry.createRegistry(1099);                 //YOU CAN ALWAYS GO WITH YOUR OPTIONAL PORT
            System.out.println("RMI Registry started on port 1099");
            
            BankImpl obj = new BankImpl();   // already exported automatically
            Naming.rebind("rmi://<YOUR IPV4 PUBLIC IP>/Bank", obj);

                BankInterface stub = (BankInterface) UnicastRemoteObject.exportObject(obj, 5000);// YOU CAN ALWAYS GO WITH YOUR OPTIONAL PORT

            // Bind using NGROK's registry port
            Naming.rebind("rmi://<YOUR IPV4 PUBLIC IP>/Bank", stub);

            System.out.println("==============================================");
            System.out.println("        BANK SERVER RUNNING ON PUBLIC WAN      ");
            System.out.println("==============================================");
            System.out.println("Registry Port     : 1099");                              //(tunneled as 14725)
            // System.out.println("Object Port       : 5000 (tunneled separately)"); USE THIS ONLY WHEN USING NGROK OR HOSTING WORLD WIDE
            System.out.println("Public RMI URL    : rmi://<YOUR IPV4 PUBLIC IP>/Bank");
            System.out.println("==============================================");

        } catch (Exception e) {
            System.out.println("Server Startup Error:");
            e.printStackTrace();
        }
    }
}

