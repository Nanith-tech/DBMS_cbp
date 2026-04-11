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
}
