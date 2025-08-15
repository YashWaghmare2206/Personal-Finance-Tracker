package gui;

import Services.TransactionServices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard {

    private JFrame frame;
    private int userId;

    public Dashboard(int userId) {
        this.userId = userId;
        frame = new JFrame("Dashboard - Personal Finance Tracker");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 255));

        JLabel heading = new JLabel(" Personal Finance Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(33, 37, 41));
        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        buttonPanel.setBackground(new Color(245, 248, 255));

        // Buttons
        JButton addTransactionBtn = createStyledButton(" Add Transaction", new Color(0, 123, 255));
        JButton viewTransactionsBtn = createStyledButton(" View All Transactions", new Color(40, 167, 69));
        JButton searchTransactionBtn = createStyledButton(" Search by Transaction By Month", new Color(255, 193, 7));
        JButton searchTransactionByIdBtn = createStyledButton(" Search by Transaction Id", new Color(160, 32, 240));
        JButton dateRangeSearchBtn = createStyledButton(" Search by Date Range", new Color(23, 162, 184));
        JButton updateTransactionBtn = createStyledButton("️ Update Transaction", new Color(108, 117, 125));
        JButton deleteTransactionBtn = createStyledButton("🗑 Delete Transaction", new Color(220, 53, 69));
        JButton balanceBtn = createStyledButton(" Check Balance", new Color(102, 16, 242));
        JButton logoutBtn = createStyledButton(" Logout", new Color(52, 58, 64));
        JLabel l = new JLabel("");
        // Add buttons to panel
        buttonPanel.add(addTransactionBtn);
        buttonPanel.add(viewTransactionsBtn);
        buttonPanel.add(searchTransactionBtn);
        buttonPanel.add(searchTransactionByIdBtn);
        buttonPanel.add(dateRangeSearchBtn);
        buttonPanel.add(updateTransactionBtn);
        buttonPanel.add(deleteTransactionBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(l);
        buttonPanel.add(logoutBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);

        // Actions
        // Button actions
        addTransactionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddTransactionForm(userId);
                frame.dispose();
            }
        });

        viewTransactionsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewAllTransactionsByUserid(userId);
                frame.dispose();
            }
        });

        searchTransactionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MonthyTransactionByid(userId);
                frame.dispose();
            }
        });

        dateRangeSearchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchTransactionByDateRangeForm(userId);
                frame.dispose();
            }
        });

        updateTransactionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UpdateTransactionForm(userId);
                frame.dispose();
            }
        });

        deleteTransactionBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DeleteTransactionForm(userId);
                frame.dispose();
            }
        });        logoutBtn.addActionListener(e -> openNewWindow(new LoginPanel()));

        balanceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransactionServices service = new TransactionServices();
                double balance = service.getBalance(userId);
                JOptionPane.showMessageDialog(frame, "Your current balance is: ₹" + balance);
            }
        });


    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openNewWindow(Object nextScreen) {
        frame.dispose(); // Close current frame
        // Just constructing the screen is enough, they already have internal `setVisible(true)` calls
    }
}
