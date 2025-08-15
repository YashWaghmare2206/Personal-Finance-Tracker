package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Services.TransactionServices;
import model.Transcation;

public class SearchTransactionByDateRangeForm extends JFrame {

    public SearchTransactionByDateRangeForm(int userId) {
        setTitle("Search Transactions by Date Range");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title section
        JLabel titleLabel = new JLabel("Search Transactions by Date Range", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Input section
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel startLabel = new JLabel("Start Date (yyyy-mm-dd):");
        JTextField startField = new JTextField();

        JLabel endLabel = new JLabel("End Date (yyyy-mm-dd):");
        JTextField endField = new JTextField();

        JButton searchBtn = new JButton("Search");
        JButton backBtn = new JButton("Back");

        inputPanel.add(startLabel);
        inputPanel.add(startField);
        inputPanel.add(endLabel);
        inputPanel.add(endField);
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(searchBtn);
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(backBtn);

        add(inputPanel, BorderLayout.NORTH);

        // Table section
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Action listeners
        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String startDate = startField.getText().trim();
                String endDate = endField.getText().trim();

                if (startDate.isEmpty() || endDate.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchTransactionByDateRangeForm.this,
                            "Both date fields must be filled.",
                            "Missing Input",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    TransactionServices service = new TransactionServices();
                    List<Transcation> list = service.searchTransactionbyDateRange(userId, startDate, endDate);

                    DefaultTableModel model = new DefaultTableModel(
                            new String[]{"Amount", "Category", "Type", "Date"}, 0);

                    for (Transcation t : list) {
                        model.addRow(new Object[]{
                                t.getAmount(),
                                t.getcategory(),
                                t.getType(),
                                t.getDate()
                        });
                    }

                    table.setModel(model);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SearchTransactionByDateRangeForm.this,
                            "Error fetching transactions: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Dashboard(userId);
                dispose();
            }
        });

        setVisible(true);
    }
}
