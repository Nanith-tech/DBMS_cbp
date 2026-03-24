package com.ipms.dao;
import com.ipms.model.Company;
import com.ipms.util.DBConnection;
import java.sql.*;

public class CompanyDAO {
    public ResultSet getAllCompanies() {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM companies";
            PreparedStatement ps = con.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCompany(int id, Company c) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE companies SET name=?, role=?, package=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getRole());
            ps.setInt(3, c.getPkg());
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCompany(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "DELETE FROM companies WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addCompany(Company c) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO companies (name,role,package) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getName());
            ps.setString(2, c.getRole());
            ps.setInt(3, c.getPkg());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}