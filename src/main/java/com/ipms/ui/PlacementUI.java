package com.ipms.ui;

import com.ipms.dao.PlacementDAO;
import com.ipms.model.Placement;
import com.ipms.util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;

public class PlacementUI extends JFrame {
    int selectedId = -1;
    HashMap<String, Integer> studentMap = new HashMap<>();
    HashMap<String, Integer> companyMap = new HashMap<>();
    JComboBox<String> studentBox, companyBox;
    JTable table;
    DefaultTableModel model;

    public PlacementUI() {
        setTitle("Placements");
        setSize(500,400);
        setLayout(null);

        JLabel l1 = new JLabel("Student:");
        l1.setBounds(20,20,80,25);
        add(l1);

        studentBox = new JComboBox<>();
        studentBox.setBounds(100,20,200,25);
        add(studentBox);

        JLabel l2 = new JLabel("Company:");
        l2.setBounds(20,60,80,25);
        add(l2);

        companyBox = new JComboBox<>();
        companyBox.setBounds(100,60,200,25);
        add(companyBox);

        JButton assign = new JButton("Assign");
        assign.setBounds(100,100,100,30);
        add(assign);

        JButton delete = new JButton("Delete");
        delete.setBounds(220,100,100,30);
        add(delete);

        delete.addActionListener(e -> {
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Delete this placement?");
            if (confirm == JOptionPane.YES_OPTION) {
                new PlacementDAO().deletePlacement(selectedId);
                selectedId = -1;
                loadTable();
            }
        });

        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Student");
        model.addColumn("Company");
        model.addColumn("Package");
        model.addColumn("Placed On");

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20,150,440,180);
        add(pane);
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
                System.out.println("Selected ID: " + selectedId); // debug
            }
        });
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = (int) model.getValueAt(row, 0);
            }
        });

        loadDropdowns();
        loadTable();

        assign.addActionListener(e -> {
            String studentName = (String) studentBox.getSelectedItem();
            String companyName = (String) companyBox.getSelectedItem();
            if (studentName == null || companyName == null) {
                JOptionPane.showMessageDialog(this, "Select a student and company first");
                return;
            }

            int studentId = studentMap.get(studentName);
            int companyId = companyMap.get(companyName);

            PlacementDAO dao = new PlacementDAO();
            if (dao.isAlreadyPlaced(studentId)) {
                JOptionPane.showMessageDialog(this, "Student already placed!");
                return;
            }

            dao.addPlacement(new Placement(studentId, companyId));

            loadDropdowns();
            loadTable();
        });

        setVisible(true);
    }

    private void loadDropdowns() {
        try {
            studentBox.removeAllItems();
            companyBox.removeAllItems();
            studentMap.clear();
            companyMap.clear();

            Connection con = DBConnection.getConnection();

            ResultSet rs1 = con.createStatement().executeQuery("SELECT id, name FROM students ORDER BY name");
            while (rs1.next()) {
                String name = rs1.getString("name");
                int id = rs1.getInt("id");

                studentBox.addItem(name);
                studentMap.put(name, id);
            }

            ResultSet rs2 = con.createStatement().executeQuery("SELECT id, name FROM companies ORDER BY name");
            while (rs2.next()) {
                String name = rs2.getString("name");
                int id = rs2.getInt("id");

                companyBox.addItem(name);
                companyMap.put(name, id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        try {
            PlacementDAO dao = new PlacementDAO();
            ResultSet rs = dao.getAllPlacements();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("student"),
                        rs.getString("company"),
                        rs.getInt("package"),
                        rs.getDate("placed_on")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
