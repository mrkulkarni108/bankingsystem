package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankInterface extends Remote {
    int openAccount(String name, String dob, String aadhaar, String pan) throws RemoteException;

    double checkBalance(int accNo, int pin) throws RemoteException;

    boolean deposit(int accNo, double amount) throws RemoteException;

    boolean withdraw(int accNo, int pin, double amount) throws RemoteException;

    boolean changePin(int accNo, int oldPin, int newPin) throws RemoteException;
}