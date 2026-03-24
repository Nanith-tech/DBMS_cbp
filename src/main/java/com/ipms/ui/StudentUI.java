package com.ipms.ui;

import com.ipms.dao.StudentDAO;
import com.ipms.model.Student;

import javax.swing.*;

public class StudentUI extends JFrame {

    public StudentUI() {
        setTitle("Students");
        setSize(300,300);
        setLayout(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20,30,80,25);
        add(l1);

        JTextField name = new JTextField();
        name.setBounds(100,30,150,25);
        add(name);

        JLabel l2 = new JLabel("Email:");
        l2.setBounds(20,70,80,25);
        add(l2);

        JTextField email = new JTextField();
        email.setBounds(100,70,150,25);
        add(email);

        JLabel l3 = new JLabel("Branch:");
        l3.setBounds(20,110,80,25);
        add(l3);

        JTextField branch = new JTextField();
        branch.setBounds(100,110,150,25);
        add(branch);

        JButton add = new JButton("Add");
        add.setBounds(100,170,100,30);
        add(add);

        add.addActionListener(e -> {
            StudentDAO dao = new StudentDAO();
            dao.addStudent(new Student(
                    name.getText(),
                    email.getText(),
                    branch.getText()
            ));
            JOptionPane.showMessageDialog(this,"Student Added");
        });

        setVisible(true);
    }
}