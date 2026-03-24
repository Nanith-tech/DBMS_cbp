package com.ipms.ui;

import com.ipms.dao.UserDAO;

import javax.swing.*;

public class LoginUI extends JFrame {

    JTextField user;
    JPasswordField pass;

    public LoginUI() {
        setTitle("Login");
        setSize(300,200);
        setLayout(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(20,30,80,25);
        add(l1);

        user = new JTextField();
        user.setBounds(100,30,150,25);
        add(user);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(20,70,80,25);
        add(l2);

        pass = new JPasswordField();
        pass.setBounds(100,70,150,25);
        add(pass);

        JButton btn = new JButton("Login");
        btn.setBounds(100,110,100,30);
        add(btn);

        btn.addActionListener(e -> {
            UserDAO dao = new UserDAO();

            if(dao.login(user.getText(), new String(pass.getPassword()))) {
                JOptionPane.showMessageDialog(this,"Login Success");
                new DashboardUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Invalid Credentials");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}