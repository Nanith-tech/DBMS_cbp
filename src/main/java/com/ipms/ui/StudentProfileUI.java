package com.ipms.ui;

import com.ipms.dao.InternDAO;
import com.ipms.dao.PlacedStudentDAO;
import com.ipms.dao.StudentDAO;
import com.ipms.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class StudentProfileUI extends JFrame {
    private final User currentUser;

    public StudentProfileUI(User currentUser) {
        this.currentUser = currentUser;

        setTitle("My Information");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Read Only Student Details"));
        add(panel, BorderLayout.CENTER);

        loadProfile(panel);
        setVisible(true);
    }

    private void loadProfile(JPanel panel) {
        try {
            Integer studentId = currentUser.getStudentId();

            ResultSet activeStudent = null;
            if (studentId != null) {
                activeStudent = new StudentDAO().getStudentById(studentId);
            }
            boolean hasActiveStudent = activeStudent != null && activeStudent.next();
            if (!hasActiveStudent) {
                activeStudent = new StudentDAO().getStudentByEmail(currentUser.getUsername());
                hasActiveStudent = activeStudent != null && activeStudent.next();
            }

            if (hasActiveStudent) {
                String email = activeStudent.getString("email");
                addRow(panel, "Name", activeStudent.getString("name"));
                addRow(panel, "Email", email);
                addRow(panel, "Branch", activeStudent.getString("branch"));
                addRow(panel, "Placement Status", "Not placed");
                addInternshipRows(panel, email);
                return;
            }

            ResultSet placedStudent = null;
            if (studentId != null) {
                placedStudent = new PlacedStudentDAO().getPlacedStudentByOriginalStudentId(studentId);
            }
            boolean hasPlacedStudent = placedStudent != null && placedStudent.next();
            if (!hasPlacedStudent) {
                placedStudent = new PlacedStudentDAO().getPlacedStudentByEmail(currentUser.getUsername());
                hasPlacedStudent = placedStudent != null && placedStudent.next();
            }

            if (hasPlacedStudent) {
                String email = placedStudent.getString("email");
                addRow(panel, "Name", placedStudent.getString("name"));
                addRow(panel, "Email", email);
                addRow(panel, "Branch", placedStudent.getString("branch"));
                addRow(panel, "Placement Status", "Placed");
                addRow(panel, "Company", placedStudent.getString("company_name"));
                addRow(panel, "Role", placedStudent.getString("role"));
                addRow(panel, "Package", String.valueOf(placedStudent.getInt("package")));
                addRow(panel, "Placed On", String.valueOf(placedStudent.getDate("placed_on")));
                addInternshipRows(panel, email);
                return;
            }

            addRow(panel, "Status", "Student record not found");
            addRow(panel, "Fix", "Set users.student_id or use student email as username");
        } catch (Exception e) {
            e.printStackTrace();
            addRow(panel, "Error", "Could not load profile");
        }
    }

    private void addInternshipRows(JPanel panel, String email) {
        try {
            ResultSet intern = new InternDAO().getInternByEmail(email);
            if (intern != null && intern.next()) {
                addRow(panel, "Internship Company", intern.getString("company_name"));
                addRow(panel, "Internship Role", intern.getString("internship_role"));
                addRow(panel, "Stipend", String.valueOf(intern.getInt("stipend")));
                addRow(panel, "Internship Status", intern.getString("status"));
            } else {
                addRow(panel, "Internship Status", "No internship assigned");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addRow(panel, "Internship Status", "Could not load internship");
        }
    }

    private void addRow(JPanel panel, String label, String value) {
        JTextField field = new JTextField(value);
        field.setEditable(false);
        panel.add(new JLabel(label + ":"));
        panel.add(field);
    }
}
