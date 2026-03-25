package com.ipms.ui;

import javax.swing.*;

public class DashboardUI extends JFrame {

    public DashboardUI() {
        setTitle("Dashboard");
        setSize(400,300);
        setLayout(null);

        JButton studentBtn = new JButton("Manage Students");
        studentBtn.setBounds(100,60,200,30);
        add(studentBtn);

        JButton companyBtn = new JButton("Manage Companies");
        companyBtn.setBounds(100,130,200,30);
        add(companyBtn);
        JButton placementBtn = new JButton("Manage Placements");
        placementBtn.setBounds(100,180,200,30);
        add(placementBtn);

        studentBtn.addActionListener(e -> new StudentUI());
        companyBtn.addActionListener(e -> new CompanyUI());
        placementBtn.addActionListener(e -> new PlacementUI());

        setVisible(true);
    }
}