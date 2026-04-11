package com.ipms.ui;

import com.ipms.model.User;

import javax.swing.*;

public class DashboardUI extends JFrame {

    private final User currentUser;

    public DashboardUI(User currentUser) {
        this.currentUser = currentUser;

        setTitle("Dashboard - " + currentUser.getRole());
        setSize(440,360);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel welcome = new JLabel("Welcome, " + currentUser.getUsername());
        welcome.setBounds(100,20,260,25);
        add(welcome);

        JButton studentBtn = new JButton(currentUser.isAdmin() ? "Manage Students" : "My Information");
        studentBtn.setBounds(100,60,220,30);
        add(studentBtn);

        studentBtn.addActionListener(e -> {
            if (currentUser.isAdmin()) {
                new StudentUI();
            } else {
                new StudentProfileUI(currentUser);
            }
        });

        if (currentUser.isAdmin()) {
            JButton internBtn = new JButton("Manage Interns");
            internBtn.setBounds(100,105,220,30);
            add(internBtn);

            JButton placedBtn = new JButton("Placed Students");
            placedBtn.setBounds(100,150,220,30);
            add(placedBtn);

            JButton companyBtn = new JButton("Manage Companies");
            companyBtn.setBounds(100,195,220,30);
            add(companyBtn);

            JButton placementBtn = new JButton("Assign Placements");
            placementBtn.setBounds(100,240,220,30);
            add(placementBtn);

            internBtn.addActionListener(e -> new InternUI());
            placedBtn.addActionListener(e -> new PlacedStudentUI());
            companyBtn.addActionListener(e -> new CompanyUI());
            placementBtn.addActionListener(e -> new PlacementUI());
        } else {
            JButton companyBtn = new JButton("View Companies");
            companyBtn.setBounds(100,105,220,30);
            add(companyBtn);
            companyBtn.addActionListener(e -> new CompanyUI(false));
        }

        setVisible(true);
    }
}
