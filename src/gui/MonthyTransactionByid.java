package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import Services.TransactionServices;
import model.Transcation;

public class MonthyTransactionByid extends JFrame {

    public MonthyTransactionByid(int userId) {
        setTitle("📅 Monthly Transactions");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel("View Transactions by Month", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();
        JLabel monthLabel = new JLabel("Month (1-12):");
        JTextField monthField = new JTextField();
        JButton searchBtn = createStyledButton("🔍 Search", new Color(25, 135, 84));
        JButton backBtn = createStyledButton("⬅ Back", new Color(108, 117, 125));

        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
        inputPanel.add(monthLabel);
        inputPanel.add(monthField);
        inputPanel.add(new JLabel());
        inputPanel.add(searchBtn);
        inputPanel.add(new JLabel());
        inputPanel.add(backBtn);

        // Table
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction List"));

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action: Search
        searchBtn.addActionListener(e -> {
            String year = yearField.getText().trim();
            String month = monthField.getText().trim();

            if (year.isEmpty() || month.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both year and month.");
                return;
            }

            try {
                int monthNum = Integer.parseInt(month);
                if (monthNum < 1 || monthNum > 12) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid month (1-12).");
                    return;
                }

                TransactionServices service = new TransactionServices();
                List<Transcation> list = service.getMonthlyTransaction(userId, year, month);

                DefaultTableModel model = new DefaultTableModel(
                        new String[]{"Amount (₹)", "Category", "Type", "Date"}, 0
                );

                for (Transcation t : list) {
                    model.addRow(new Object[]{
                            t.getAmount(),
                            t.getcategory(),
                            t.getType(),
                            t.getDate()
                    });
                }

                table.setModel(model);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Month must be a number between 1 and 12.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Action: Back
        backBtn.addActionListener(e -> {
            new Dashboard(userId);
            dispose();
        });

        setVisible(true);
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
