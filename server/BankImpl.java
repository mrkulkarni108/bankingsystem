package server;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.sql.*;

public class BankImpl extends UnicastRemoteObject implements BankInterface {

    private Connection con;

    public BankImpl() throws RemoteException {
        super();
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:bank.db");

            String sql = """
                    CREATE TABLE IF NOT EXISTS accounts(
                        acc_no INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        dob TEXT NOT NULL,
                        aadhaar INTEGER UNIQUE NOT NULL,
                        pan TEXT UNIQUE NOT NULL,
                        balance DOUBLE DEFAULT 0,
                        pin INTEGER NOT NULL
                    );
                    """;

            try (Statement st = con.createStatement()) {
                st.executeUpdate(sql);
            }

            System.out.println("Database initialized.");

        } catch (Exception e) {
            System.out.println("Database initialization error:");
            e.printStackTrace();
        }
    }

    @Override
    public int openAccount(String name, String dob, String aadhaar, String pan) throws RemoteException {
        String sql = """
                INSERT INTO accounts(name, dob, aadhaar, pan, balance, pin)
                VALUES (?, ?, ?, ?, 0, 1234);
                """;

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, aadhaar);
            ps.setString(4, pan);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            System.out.println("Error creating account:");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public double checkBalance(int accNo, int pin) throws RemoteException {
        try (PreparedStatement ps =
                     con.prepareStatement("SELECT balance, pin FROM accounts WHERE acc_no=?")) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt("pin") == pin)
                return rs.getDouble("balance");

        } catch (Exception e) {
            System.out.println("Check balance error:");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean deposit(int accNo, double amount) throws RemoteException {
        try (PreparedStatement ps =
                     con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE acc_no=?")) {

            ps.setDouble(1, amount);
            ps.setInt(2, accNo);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Deposit error:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean withdraw(int accNo, int pin, double amount) throws RemoteException {
        String check = "SELECT balance, pin FROM accounts WHERE acc_no=?";
        String update = "UPDATE accounts SET balance = balance - ? WHERE acc_no=?";

        try (PreparedStatement ps = con.prepareStatement(check)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt("pin") == pin) {

                if (rs.getDouble("balance") >= amount) {
                    try (PreparedStatement updatePs = con.prepareStatement(update)) {
                        updatePs.setDouble(1, amount);
                        updatePs.setInt(2, accNo);
                        return updatePs.executeUpdate() > 0;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Withdraw error:");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean changePin(int accNo, int oldPin, int newPin) throws RemoteException {
        String check = "SELECT pin FROM accounts WHERE acc_no=?";
        String update = "UPDATE accounts SET pin=? WHERE acc_no=?";

        try (PreparedStatement ps = con.prepareStatement(check)) {
            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt("pin") == oldPin) {

                try (PreparedStatement updatePs = con.prepareStatement(update)) {
                    updatePs.setInt(1, newPin);
                    updatePs.setInt(2, accNo);
                    return updatePs.executeUpdate() > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Change PIN error:");
            e.printStackTrace();
        }
        return false;
    }
}
