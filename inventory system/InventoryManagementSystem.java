import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryManagementSystem extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;

    public InventoryManagementSystem() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define table columns
        String[] columns = { "Item ID", "Name", "Quantity", "Price" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");

        // Button panel
        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> openItemDialog(null));

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                openItemDialog(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to update.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to delete.");
            }
        });
    }

    // Dialog to add or update items
    private void openItemDialog(Integer rowIndex) {
        JDialog dialog = new JDialog(this, rowIndex == null ? "Add Item" : "Update Item", true);
        dialog.setSize(300, 250);
        dialog.setLayout(new GridLayout(5, 2));
        dialog.setLocationRelativeTo(this);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField qtyField = new JTextField();
        JTextField priceField = new JTextField();

        // If updating, fill fields with current data
        if (rowIndex != null) {
            idField.setText(tableModel.getValueAt(rowIndex, 0).toString());
            nameField.setText(tableModel.getValueAt(rowIndex, 1).toString());
            qtyField.setText(tableModel.getValueAt(rowIndex, 2).toString());
            priceField.setText(tableModel.getValueAt(rowIndex, 3).toString());
        }

        dialog.add(new JLabel("Item ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Quantity:"));
        dialog.add(qtyField);
        dialog.add(new JLabel("Price:"));
        dialog.add(priceField);

        JButton saveButton = new JButton("Save");
        dialog.add(new JLabel()); // Empty space
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String qty = qtyField.getText();
            String price = priceField.getText();

            if (id.isEmpty() || name.isEmpty() || qty.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields are required.");
                return;
            }

            try {
                int quantity = Integer.parseInt(qty);
                double priceValue = Double.parseDouble(price);

                if (rowIndex == null) {
                    tableModel.addRow(new Object[] { id, name, quantity, priceValue });
                } else {
                    tableModel.setValueAt(id, rowIndex, 0);
                    tableModel.setValueAt(name, rowIndex, 1);
                    tableModel.setValueAt(quantity, rowIndex, 2);
                    tableModel.setValueAt(priceValue, rowIndex, 3);
                }
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Quantity must be an integer and price must be a number.");
            }
        });

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementSystem().setVisible(true));
    }
}
