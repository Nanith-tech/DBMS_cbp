package com.ipms.ui;

import com.ipms.dao.InternDAO;
import com.ipms.model.Intern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class InternUI extends JFrame {
    JTable table;
    DefaultTableModel model;
    JTextField name, email, branch, companyName, internshipRole, stipend, status;
    int selectedId = -1;

    public InternUI() {
        setTitle("Intern Management");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Intern Details"));

        name = new JTextField();
        email = new JTextField();
        branch = new JTextField();
        companyName = new JTextField();
        internshipRole = new JTextField();
        stipend = new JTextField();
        status = new JTextField("ONGOING");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(name);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(email);
        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branch);
        formPanel.add(new JLabel("Company:"));
        formPanel.add(companyName);
        formPanel.add(new JLabel("Internship Role:"));
        formPanel.add(internshipRole);
        formPanel.add(new JLabel("Stipend:"));
        formPanel.add(stipend);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(status);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Branch");
        model.addColumn("Company");
        model.addColumn("Role");
        model.addColumn("Stipend");
        model.addColumn("Status");

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadTable();

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
                name.setText(model.getValueAt(row, 1).toString());
                email.setText(model.getValueAt(row, 2).toString());
                branch.setText(model.getValueAt(row, 3).toString());
                companyName.setText(model.getValueAt(row, 4).toString());
                internshipRole.setText(model.getValueAt(row, 5).toString());
                stipend.setText(model.getValueAt(row, 6).toString());
                status.setText(model.getValueAt(row, 7).toString());
            }
        });

        addBtn.addActionListener(e -> {
            new InternDAO().addIntern(readInternFromForm());
            clearFields();
            loadTable();
        });

        updateBtn.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            new InternDAO().updateIntern(selectedId, readInternFromForm());
            clearFields();
            selectedId = -1;
            loadTable();
        });

        deleteBtn.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Delete this intern?");
            if (confirm == JOptionPane.YES_OPTION) {
                new InternDAO().deleteIntern(selectedId);
                clearFields();
                selectedId = -1;
                loadTable();
            }
        });

        setVisible(true);
    }

    private Intern readInternFromForm() {
        return new Intern(
                name.getText(),
                email.getText(),
                branch.getText(),
                companyName.getText(),
                internshipRole.getText(),
                Integer.parseInt(stipend.getText()),
                status.getText()
        );
    }

    private void loadTable() {
        try {
            ResultSet rs = new InternDAO().getAllInterns();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("branch"),
                        rs.getString("company_name"),
                        rs.getString("internship_role"),
                        rs.getInt("stipend"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        name.setText("");
        email.setText("");
        branch.setText("");
        companyName.setText("");
        internshipRole.setText("");
        stipend.setText("");
        status.setText("ONGOING");
    }
}
