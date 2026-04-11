package com.ipms.ui;

import com.ipms.dao.PlacedStudentDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class PlacedStudentUI extends JFrame {
    JTable table;
    DefaultTableModel model;

    public PlacedStudentUI() {
        setTitle("Placed Students");
        setSize(900, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Student");
        model.addColumn("Email");
        model.addColumn("Branch");
        model.addColumn("Company");
        model.addColumn("Role");
        model.addColumn("Package");
        model.addColumn("Placed On");

        add(new JScrollPane(table), BorderLayout.CENTER);
        loadTable();
        setVisible(true);
    }

    private void loadTable() {
        try {
            ResultSet rs = new PlacedStudentDAO().getAllPlacedStudents();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("branch"),
                        rs.getString("company_name"),
                        rs.getString("role"),
                        rs.getInt("package"),
                        rs.getDate("placed_on")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
