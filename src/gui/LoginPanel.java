package gui;

import Services.AuthenticateUser;

import javax.swing.*;
import java.awt.*;

public class LoginPanel {

    private JFrame frame;
    public int userId;

    public LoginPanel() {
        frame = new JFrame("🔐 Personal Finance Tracker - Login");
        frame.setSize(420, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel("Login to Your Account", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        frame.add(heading, BorderLayout.NORTH);

        // Form panel
        JPanel panel = new JPanel(new GridLayout(3, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        panel.setBackground(new Color(245, 248, 255));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton loginButton = createStyledButton("🔓 Login", new Color(25, 135, 84));
        JButton registerButton = createStyledButton("📝 Register", new Color(13, 110, 253));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.setBackground(new Color(245, 248, 255));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(new Color(245, 248, 255));

        frame.setVisible(true);

        // Login Logic
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                return;
            }

            AuthenticateUser auth = new AuthenticateUser();
            if (auth.validateuser(username, password)) {
                userId = auth.getUserId(username);
                JOptionPane.showMessageDialog(frame, "✅ Login Successful");
                new Dashboard(userId);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Invalid Credentials!");
            }
        });

        // Register Logic
        registerButton.addActionListener(e -> {
            new RegisterPanel();
            frame.dispose();
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
