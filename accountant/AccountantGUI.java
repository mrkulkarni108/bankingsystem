package accountant;

import javax.swing.*;
import java.awt.event.*;
import server.BankInterface;
import java.rmi.Naming;

public class AccountantGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Accountant - Open Account");
        frame.setSize(420, 350);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel nameLbl = new JLabel("Name as per Addhar:");
        nameLbl.setBounds(20, 40, 120, 25);
        frame.add(nameLbl);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 40, 200, 25);
        frame.add(nameField);

        JLabel dobLbl = new JLabel("DOB (YYYY-MM-DD):");
        dobLbl.setBounds(20, 80, 150, 25);
        frame.add(dobLbl);

        JTextField dobField = new JTextField();
        dobField.setBounds(150, 80, 200, 25);
        frame.add(dobField);

        JLabel aadLbl = new JLabel("Aadhaar:");
        aadLbl.setBounds(20, 120, 150, 25);
        frame.add(aadLbl);

        JTextField aadField = new JTextField();
        aadField.setBounds(150, 120, 200, 25);
        frame.add(aadField);

        JLabel panLbl = new JLabel("PAN:");
        panLbl.setBounds(20, 160, 150, 25);
        frame.add(panLbl);

        JTextField panField = new JTextField();
        panField.setBounds(150, 160, 200, 25);
        frame.add(panField);

        JButton btnOpen = new JButton("Open Account");
        btnOpen.setBounds(140, 220, 150, 35);
        frame.add(btnOpen);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText().trim();
                String dob = dobField.getText().trim();
                String aad = aadField.getText().trim();
                String pan = panField.getText().trim();

                if (name.isEmpty() || dob.isEmpty() || aad.isEmpty() || pan.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.");
                    return;
                }

                try {
                    BankInterface bank = (BankInterface) Naming.lookup("rmi://0.tcp.in.ngrok.io:14725/Bank");

                    JOptionPane.showMessageDialog(frame, "Connected to Bank Server.");

                    int acc = bank.openAccount(name, dob, aad, pan);

                    if (acc > 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Account Created Successfully.\n" +
                                        "Account Number: " + acc + "\n" +
                                        "Default PIN: 1234");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Account Creation Failed.");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Server Error:\n" + ex.getMessage());
                }
            }
        });
    }
}