package com.ipms.dao;

import com.ipms.model.Intern;
import com.ipms.util.DBConnection;

import java.sql.*;

public class InternDAO {
    public ResultSet getAllInterns() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM interns ORDER BY id";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getInternByEmail(String email) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM interns WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addIntern(Intern intern) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO interns (name,email,branch,company_name,internship_role,stipend,status) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, intern.getName());
            ps.setString(2, intern.getEmail());
            ps.setString(3, intern.getBranch());
            ps.setString(4, intern.getCompanyName());
            ps.setString(5, intern.getInternshipRole());
            ps.setInt(6, intern.getStipend());
            ps.setString(7, intern.getStatus());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIntern(int id, Intern intern) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE interns SET name=?, email=?, branch=?, company_name=?, internship_role=?, stipend=?, status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, intern.getName());
            ps.setString(2, intern.getEmail());
            ps.setString(3, intern.getBranch());
            ps.setString(4, intern.getCompanyName());
            ps.setString(5, intern.getInternshipRole());
            ps.setInt(6, intern.getStipend());
            ps.setString(7, intern.getStatus());
            ps.setInt(8, id);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteIntern(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM interns WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
