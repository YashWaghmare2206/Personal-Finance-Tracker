package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Services.TransactionServices;

public class UpdateTransactionForm extends JFrame {

    public UpdateTransactionForm(int userId) {
        setTitle("Update Transaction");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel titleLabel = new JLabel("Update Transaction Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel idLabel = new JLabel("Transaction ID:");
        JTextField idField = new JTextField();

        JLabel amountLabel = new JLabel("New Amount:");
        JTextField amountField = new JTextField();

        JLabel categoryLabel = new JLabel("New Category:");
        JTextField categoryField = new JTextField();

        JLabel typeLabel = new JLabel("Type (Income/Expense):");
        String[] cat = {"Income" , "Expense"};
        JComboBox typeField = new JComboBox(cat);


        JLabel dateLabel = new JLabel("New Date (yyyy-mm-dd):");
        JTextField dateField = new JTextField();

        JButton updateBtn = new JButton("Update");
        JButton backBtn = new JButton("Back");

        formPanel.add(idLabel);        formPanel.add(idField);
        formPanel.add(amountLabel);    formPanel.add(amountField);
        formPanel.add(categoryLabel);  formPanel.add(categoryField);
        formPanel.add(typeLabel);      formPanel.add(typeField);
        formPanel.add(dateLabel);      formPanel.add(dateField);
        formPanel.add(updateBtn);      formPanel.add(backBtn);

        add(formPanel, BorderLayout.CENTER);

        // Action Listeners
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int transactionId = Integer.parseInt(idField.getText().trim());
                    double amount = Double.parseDouble(amountField.getText().trim());
                    String type = String.valueOf(typeField.getSelectedItem());
                    String category = categoryField.getText().trim();
                    String date = dateField.getText().trim();
                    double prev_balance;

                    if (category.isEmpty() || type.isEmpty() || date.isEmpty()) {
                        JOptionPane.showMessageDialog(UpdateTransactionForm.this,
                                "All fields must be filled.",
                                "Validation Error",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    TransactionServices service = new TransactionServices();
                    prev_balance =service.getBalance(service.getUserId(transactionId));
                    service.updateTransaction(transactionId , amount , category  , type , date ,prev_balance);
                    JOptionPane.showMessageDialog(UpdateTransactionForm.this,
                            "Transaction updated successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UpdateTransactionForm.this,
                            "Please enter valid numeric values for ID and Amount.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UpdateTransactionForm.this,
                            "Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Dashboard(userId);
            }
        });

        setVisible(true);
    }
}
