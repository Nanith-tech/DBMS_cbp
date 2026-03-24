package com.ipms.dao;
import com.ipms.model.Company;
import com.ipms.util.DBConnection;
import java.sql.*;

public class CompanyDAO {

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