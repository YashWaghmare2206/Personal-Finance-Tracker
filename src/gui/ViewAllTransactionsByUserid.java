package gui;

import Services.TransactionServices;
import model.Transcation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewAllTransactionsByUserid {

    private JFrame frame;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private int userId;

    public ViewAllTransactionsByUserid(int userId) {
        this.userId = userId;
        TransactionServices service = new TransactionServices();

        frame = new JFrame("All Transactions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Header
        JLabel titleLabel = new JLabel("All Transactions", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Amount", "Category", "Type", "Date"}, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(28);
        transactionTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        transactionTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        transactionTable.setFillsViewportHeight(true);

        // Add rows
        List<Transcation> transactions = service.getUserTransaction(userId);
        for (Transcation t : transactions) {
            tableModel.addRow(new Object[]{
                    t.getAmount(),
                    t.getcategory(),
                    t.getType(),
                    t.getDate()
            });
        }

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel with back button
        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backBtn.setPreferredSize(new Dimension(180, 35));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bottomPanel.add(backBtn);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action Listener for Back
        backBtn.addActionListener(e -> {
            new Dashboard(userId);
            frame.dispose();
        });

        frame.setVisible(true);
    }
}
