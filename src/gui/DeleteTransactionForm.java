package gui;

import javax.swing.*;
import java.awt.*;
import Services.TransactionServices;

public class DeleteTransactionForm extends JFrame {

    public DeleteTransactionForm(int userId) {
        setTitle("🗑️ Delete Transaction");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Panel and layout
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        panel.setBackground(new Color(250, 250, 255));

        JLabel idLabel = new JLabel("Enter Transaction ID:");
        JTextField idField = new JTextField();
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(idLabel);
        panel.add(idField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton deleteBtn = createStyledButton("🗑️ Delete", new Color(220, 53, 69));
        JButton backBtn = createStyledButton("↩️ Back", new Color(108, 117, 125));

        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        buttonPanel.setBackground(new Color(250, 250, 255));

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Delete action
        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                TransactionServices service = new TransactionServices();
                boolean deleted = service.deleteTransaction(id);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "✅ Transaction deleted successfully.");
                    idField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "⚠️ Transaction ID not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❌ Please enter a valid numeric Transaction ID.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });

        // Back action
        backBtn.addActionListener(e -> {
            dispose();
            new Dashboard(userId);
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
