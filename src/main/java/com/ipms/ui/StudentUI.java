package com.ipms.ui;

import com.ipms.dao.StudentDAO;
import com.ipms.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class StudentUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField name, email, branch;
    int selectedId = -1;

    public StudentUI() {
        setTitle("Student Management");
        setSize(700,500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        // 🔷 FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(3,2,10,10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        name = new JTextField();
        email = new JTextField();
        branch = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(name);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(email);
        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branch);

        // 🔷 BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // 🔷 TABLE PANEL
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Branch");

        table.setRowHeight(25);
        JScrollPane tablePane = new JScrollPane(table);

        // 🔷 LAYOUT STRUCTURE
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(tablePane, BorderLayout.CENTER);

        loadTable();

        // 🔥 ROW SELECTION (same logic as before)
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
                name.setText(model.getValueAt(row, 1).toString());
                email.setText(model.getValueAt(row, 2).toString());
                branch.setText(model.getValueAt(row, 3).toString());
            }
        });

        // 🔥 ADD
        addBtn.addActionListener(e -> {
            new StudentDAO().addStudent(new Student(
                    name.getText(),
                    email.getText(),
                    branch.getText()
            ));

            clearFields();
            loadTable();
        });

        // 🔥 UPDATE
        updateBtn.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            new StudentDAO().updateStudent(selectedId, new Student(
                    name.getText(),
                    email.getText(),
                    branch.getText()
            ));

            clearFields();
            selectedId = -1;
            loadTable();
        });

        // 🔥 DELETE
        deleteBtn.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Delete this student?");
            if (confirm == JOptionPane.YES_OPTION) {
                new StudentDAO().deleteStudent(selectedId);
                clearFields();
                selectedId = -1;
                loadTable();
            }
        });

        setVisible(true);
    }

    private void loadTable() {
        try {
            ResultSet rs = new StudentDAO().getAllStudents();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("branch")
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
    }
}