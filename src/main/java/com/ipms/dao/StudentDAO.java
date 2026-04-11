package com.ipms.dao;

import com.ipms.model.Student;
import com.ipms.util.DBConnection;
import java.sql.*;

public class StudentDAO {
    private static final String DEFAULT_STUDENT_PASSWORD = "student123";

    public ResultSet getAllStudents() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getStudentById(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getStudentByEmail(String email) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM students WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void updateStudent(int id, Student s) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE students SET name=?, email=?, branch=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getBranch());
            ps.setInt(4, id);

            ps.executeUpdate();

            String userSql = "UPDATE users SET username=? WHERE student_id=? AND role='STUDENT'";
            PreparedStatement userPs = con.prepareStatement(userSql);
            userPs.setString(1, s.getEmail());
            userPs.setInt(2, id);
            userPs.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String userSql = "DELETE FROM users WHERE student_id=? AND role='STUDENT'";
            PreparedStatement userPs = con.prepareStatement(userSql);
            userPs.setInt(1, id);
            userPs.executeUpdate();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudent(Student s) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String sql = "INSERT INTO students (name,email,branch) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getBranch());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int studentId = generatedKeys.getInt(1);
                createStudentUser(con, studentId, s.getEmail());
            }

            con.commit();

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException autoCommitError) {
                autoCommitError.printStackTrace();
            }
        }
    }

    private void createStudentUser(Connection con, int studentId, String email) throws SQLException {
        String sql = "INSERT INTO users (username, password, role, student_id) VALUES (?, ?, 'STUDENT', ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, DEFAULT_STUDENT_PASSWORD);
        ps.setInt(3, studentId);
        ps.executeUpdate();
    }
}
