package gui;

import Services.AuthenticateUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel {

    private JFrame frame;

    public RegisterPanel() {
        frame = new JFrame("Register - Personal Finance Tracker");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Create New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel balanceLabel = new JLabel("Starting Balance:");
        JTextField balanceField = new JTextField();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(balanceLabel);
        panel.add(balanceField);

        frame.add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Register Button Logic
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String balanceStr = balanceField.getText().trim();

                if (username.isEmpty() || password.isEmpty() || balanceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    double balance = Double.parseDouble(balanceStr);
                    if (balance < 0) {
                        JOptionPane.showMessageDialog(frame, "Balance cannot be negative.", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    AuthenticateUser auth = new AuthenticateUser();
                    auth.registerUser(username, password, balance);
                    JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new LoginPanel();
                    frame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid balance amount.", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Back Button Logic
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPanel();
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
}
