package gui;

import Services.TransactionServices;
import model.Expense;
import model.Income;
import model.Transcation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTransactionForm {

    private JFrame frame;
    private JTextField amountField, descriptionField, dateField;
    private JComboBox<String> typeCombo;
    private int userId;

    public AddTransactionForm(int userId) {
        this.userId = userId;
        frame = new JFrame("Add Transaction");
        frame.setSize(420, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setBackground(new Color(245, 248, 255));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        panel.add(makeLabeledField("💰 Amount:", amountField = new JTextField(), labelFont, fieldFont));
        panel.add(makeLabeledField("📝 Description:", descriptionField = new JTextField(), labelFont, fieldFont));
        panel.add(makeLabeledField("📊 Type:", typeCombo = new JComboBox<>(new String[]{"Income", "Expense"}), labelFont, fieldFont));
        panel.add(makeLabeledField("📅 Date (yyyy-mm-dd):", dateField = new JTextField(), labelFont, fieldFont));

        JButton addButton = new JButton("➕ Add Transaction");
        JButton backButton = new JButton("⬅ Back");

        styleButton(addButton, new Color(0, 123, 255));
        styleButton(backButton, new Color(108, 117, 125));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 248, 255));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        panel.add(Box.createVerticalStrut(20));
        panel.add(buttonPanel);

        frame.add(panel);
        frame.setVisible(true);

        // Action Listeners
        addButton.addActionListener(e -> handleAddTransaction());
        backButton.addActionListener(e -> {
            new Dashboard(userId);
            frame.dispose();
        });
    }

    private JPanel makeLabeledField(String labelText, JComponent field, Font labelFont, Font fieldFont) {
        JPanel container = new JPanel(new BorderLayout(5, 5));
        container.setBackground(new Color(245, 248, 255));

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(33, 37, 41));

        if (field instanceof JTextField) ((JTextField) field).setFont(fieldFont);
        if (field instanceof JComboBox) ((JComboBox<?>) field).setFont(fieldFont);

        container.add(label, BorderLayout.NORTH);
        container.add(field, BorderLayout.CENTER);
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        return container;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void handleAddTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String description = descriptionField.getText();
            String type = (String) typeCombo.getSelectedItem();
            String date = dateField.getText();

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Description cannot be empty!");
                return;
            }

            Transcation transaction;
            if (type.equals("Income")) {
                transaction = new Income(userId, amount, description, date);
            } else {
                transaction = new Expense(userId, amount, description, date);
            }

            TransactionServices service = new TransactionServices();
            int transactionId = service.addTransaction(transaction);

            if (transactionId != -1) {
                //double currentBalance = service.getBalance(userId);
                //double newBalance = type.equals("Income") ? currentBalance + amount : currentBalance - amount;


                JOptionPane.showMessageDialog(frame, "Transaction added!\nID: " + transactionId);
                new Dashboard(userId);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add transaction.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, " Please enter a valid number for amount.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }
}
