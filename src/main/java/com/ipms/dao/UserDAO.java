package com.ipms.dao;

import com.ipms.model.User;
import com.ipms.util.DBConnection;

import java.sql.*;

public class UserDAO {

    public User login(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT id, username, password, role, student_id FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        (Integer) rs.getObject("student_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean studentExistsForUser(User user) {
        if (user == null || !user.isStudent()) {
            return false;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (user.getStudentId() != null) {
                String byIdSql = "SELECT id FROM students WHERE id=?";
                PreparedStatement byIdPs = con.prepareStatement(byIdSql);
                byIdPs.setInt(1, user.getStudentId());
                ResultSet byIdRs = byIdPs.executeQuery();
                if (byIdRs.next()) {
                    return true;
                }
            }

            String byEmailSql = "SELECT id FROM students WHERE email=?";
            PreparedStatement byEmailPs = con.prepareStatement(byEmailSql);
            byEmailPs.setString(1, user.getUsername());
            ResultSet byEmailRs = byEmailPs.executeQuery();
            return byEmailRs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
