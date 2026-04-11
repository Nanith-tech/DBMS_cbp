package com.ipms.ui;

import com.ipms.dao.CompanyDAO;
import com.ipms.model.Company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class CompanyUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField name, role, pkg;
    int selectedId = -1;
    private final boolean adminMode;

    public CompanyUI() {
        this(true);
    }

    public CompanyUI(boolean adminMode) {
        this.adminMode = adminMode;
        setTitle("Companies");
        setSize(600,400);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20,20,80,25);
        add(l1);

        name = new JTextField();
        name.setBounds(100,20,150,25);
        add(name);

        JLabel l2 = new JLabel("Role:");
        l2.setBounds(20,60,80,25);
        add(l2);

        role = new JTextField();
        role.setBounds(100,60,150,25);
        add(role);

        JLabel l3 = new JLabel("Package:");
        l3.setBounds(20,100,80,25);
        add(l3);

        pkg = new JTextField();
        pkg.setBounds(100,100,150,25);
        add(pkg);

        JButton add = new JButton("Add");
        add.setBounds(300,20,100,30);
        add(add);

        JButton update = new JButton("Update");
        update.setBounds(300,60,100,30);
        add(update);

        JButton delete = new JButton("Delete");
        delete.setBounds(300,100,100,30);
        add(delete);

        add.setVisible(adminMode);
        update.setVisible(adminMode);
        delete.setVisible(adminMode);
        name.setEditable(adminMode);
        role.setEditable(adminMode);
        pkg.setEditable(adminMode);

        // TABLE
        model = new DefaultTableModel();
        table = new JTable(model);

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Role");
        model.addColumn("Package");

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
                role.setText(model.getValueAt(row, 2).toString());
                pkg.setText(model.getValueAt(row, 3).toString());
            }
        });

        // ADD
        add.addActionListener(e -> {
            if (!adminMode) {
                return;
            }
            CompanyDAO dao = new CompanyDAO();
            dao.addCompany(new Company(
                    name.getText(),
                    role.getText(),
                    Integer.parseInt(pkg.getText())
            ));
            loadTable();
        });

        // UPDATE
        update.addActionListener(e -> {
            if (!adminMode) {
                return;
            }
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            CompanyDAO dao = new CompanyDAO();
            dao.updateCompany(selectedId, new Company(
                    name.getText(),
                    role.getText(),
                    Integer.parseInt(pkg.getText())
            ));

            loadTable();
        });

        // DELETE
        delete.addActionListener(e -> {
            if (!adminMode) {
                return;
            }
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first");
                return;
            }

            CompanyDAO dao = new CompanyDAO();
            dao.deleteCompany(selectedId);

            loadTable();
        });

        setVisible(true);
    }

    private void loadTable() {
        try {
            CompanyDAO dao = new CompanyDAO();
            ResultSet rs = dao.getAllCompanies();

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getInt("package")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
