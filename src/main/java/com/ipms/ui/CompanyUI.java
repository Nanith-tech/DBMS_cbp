package com.ipms.ui;

import com.ipms.dao.CompanyDAO;
import com.ipms.model.Company;

import javax.swing.*;

public class CompanyUI extends JFrame {

    public CompanyUI() {
        setTitle("Companies");
        setSize(300,300);
        setLayout(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20,30,80,25);
        add(l1);

        JTextField name = new JTextField();
        name.setBounds(100,30,150,25);
        add(name);

        JLabel l2 = new JLabel("Role:");
        l2.setBounds(20,70,80,25);
        add(l2);

        JTextField role = new JTextField();
        role.setBounds(100,70,150,25);
        add(role);

        JLabel l3 = new JLabel("Package:");
        l3.setBounds(20,110,80,25);
        add(l3);

        JTextField pkg = new JTextField();
        pkg.setBounds(100,110,150,25);
        add(pkg);

        JButton add = new JButton("Add");
        add.setBounds(100,170,100,30);
        add(add);

        add.addActionListener(e -> {
            CompanyDAO dao = new CompanyDAO();
            dao.addCompany(new Company(
                    name.getText(),
                    role.getText(),
                    Integer.parseInt(pkg.getText())
            ));
            JOptionPane.showMessageDialog(this,"Company Added");
        });

        setVisible(true);
    }
}