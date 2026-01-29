package org.tracker.dao;

import org.tracker.database.DatabaseConnection;
import org.tracker.entity.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ApplicationDAO {

    public void addApplication(Application app) throws Exception {

        String sql = "INSERT INTO applications(company, position, status) VALUES (?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, app.getCompany());
        ps.setString(2, app.getPosition());
        ps.setString(3, app.getStatus());

        ps.executeUpdate();
        conn.close();
    }
}

