package com.ipms.ui;

import com.ipms.dao.StudentDAO;
import com.ipms.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class StudentUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField name, email, branch;
    int selectedId = -1;

    public StudentUI() {
        setTitle("Students");
        setSize(600,400);
        setLayout(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20,20,80,25);
        add(l1);

        name = new JTextField();
        name.setBounds(100,20,150,25);
        add(name);

        JLabel l2 = new JLabel("Email:");
        l2.setBounds(20,60,80,25);
        add(l2);

        email = new JTextField();
        email.setBounds(100,60,150,25);
        add(email);

        JLabel l3 = new JLabel("Branch:");
        l3.setBounds(20,100,80,25);
        add(l3);

        branch = new JTextField();
        branch.setBounds(100,100,150,25);
        add(branch);

        JButton add = new JButton("Add");
        add.setBounds(300,20,100,30);
        add(add);

        JButton update = new JButton("Update");
        update.setBounds(300,60,100,30);
        add(update);

        JButton delete = new JButton("Delete");
        delete.setBounds(300,100,100,30);
        add(delete);

        // TABLE
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Branch");

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20,160,540,180);
        add(pane);

        loadTable();

        // SELECT ROW
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
                name.setText(model.getValueAt(row, 1).toString());
                email.setText(model.getValueAt(row, 2).toString());
                branch.setText(model.getValueAt(row, 3).toString());
            }
        });

        // ADD
        add.addActionListener(e -> {
            StudentDAO dao = new StudentDAO();
            dao.addStudent(new Student(
                    name.getText(),
                    email.getText(),
                    branch.getText()
            ));
            loadTable();
        });

        // UPDATE
        update.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            StudentDAO dao = new StudentDAO();
            dao.updateStudent(selectedId, new Student(
                    name.getText(),
                    email.getText(),
                    branch.getText()
            ));

            loadTable();
        });

        // DELETE
        delete.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            StudentDAO dao = new StudentDAO();
            dao.deleteStudent(selectedId);

            loadTable();
        });

        setVisible(true);
    }

    private void loadTable() {
        try {
            StudentDAO dao = new StudentDAO();
            ResultSet rs = dao.getAllStudents();

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
}