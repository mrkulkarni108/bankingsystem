package atm;

import javax.swing.*;
import java.awt.event.*;
import server.BankInterface;
import java.rmi.Naming;

public class ATMGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("ATM Machine");
        frame.setSize(420, 400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel accLbl = new JLabel("Account No:");
        accLbl.setBounds(40, 40, 120, 25);
        frame.add(accLbl);

        JTextField accField = new JTextField();
        accField.setBounds(150, 40, 200, 25);
        frame.add(accField);

        JLabel pinLbl = new JLabel("PIN:");
        pinLbl.setBounds(40, 80, 120, 25);
        frame.add(pinLbl);

        JTextField pinField = new JTextField();
        pinField.setBounds(150, 80, 200, 25);
        frame.add(pinField);

        JLabel amtLbl = new JLabel("Amount:");
        amtLbl.setBounds(40, 120, 120, 25);
        frame.add(amtLbl);

        JTextField amtField = new JTextField();
        amtField.setBounds(150, 120, 200, 25);
        frame.add(amtField);

        JButton btnBalance = new JButton("Check Balance");
        JButton btnDeposit = new JButton("Deposit");
        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnChangePin = new JButton("Change PIN");

        btnBalance.setBounds(40, 170, 150, 35);
        btnDeposit.setBounds(210, 170, 150, 35);
        btnWithdraw.setBounds(40, 220, 150, 35);
        btnChangePin.setBounds(210, 220, 150, 35);

        frame.add(btnBalance);
        frame.add(btnDeposit);
        frame.add(btnWithdraw);
        frame.add(btnChangePin);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnBalance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int acc = Integer.parseInt(accField.getText());
                    int pin = Integer.parseInt(pinField.getText());

                    BankInterface bank = (BankInterface) Naming.lookup("rmi://0.tcp.in.ngrok.io:11574/bank");

                    double bal = bank.checkBalance(acc, pin);

                    if (bal >= 0)
                        JOptionPane.showMessageDialog(frame, "Balance: " + bal);
                    else
                        JOptionPane.showMessageDialog(frame, "Invalid account or PIN.");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error:\n" + ex.getMessage());
                }
            }
        });

        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int acc = Integer.parseInt(accField.getText());
                    double amt = Double.parseDouble(amtField.getText());

                    BankInterface bank = (BankInterface) Naming.lookup("rmi://0.tcp.in.ngrok.io:11574/bank");

                    boolean ok = bank.deposit(acc, amt);

                    JOptionPane.showMessageDialog(frame,
                            ok ? "Deposit Successful" : "Deposit Failed");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error:\n" + ex.getMessage());
                }
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int acc = Integer.parseInt(accField.getText());
                    int pin = Integer.parseInt(pinField.getText());
                    double amt = Double.parseDouble(amtField.getText());

                    BankInterface bank = (BankInterface) Naming.lookup("rmi://0.tcp.in.ngrok.io:11574/bank");

                    boolean ok = bank.withdraw(acc, pin, amt);

                    JOptionPane.showMessageDialog(frame,
                            ok ? "Withdrawal Successful" : "Withdrawal Failed");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error:\n" + ex.getMessage());
                }
            }
        });

        btnChangePin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int acc = Integer.parseInt(accField.getText());
                    int oldPin = Integer.parseInt(pinField.getText());
                    int newPin = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter new PIN:"));

                    BankInterface bank = (BankInterface) Naming.lookup("rmi://0.tcp.in.ngrok.io:11574/bank");

                    boolean ok = bank.changePin(acc, oldPin, newPin);

                    JOptionPane.showMessageDialog(frame,
                            ok ? "PIN Updated" : "PIN Update Failed");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error:\n" + ex.getMessage());
                }
            }
        });
    }
}
