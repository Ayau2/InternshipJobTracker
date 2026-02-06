package org.tracker.dao;

import org.tracker.database.DatabaseConnection;
import org.tracker.entity.Application;
import org.tracker.HashUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ApplicationDAO {


    public void addApplication(int userId, Application app) throws Exception {
        String sql = "INSERT INTO applications(user_id, company, position, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, app.getCompany());
            ps.setString(3, app.getPosition());
            ps.setString(4, app.getStatus());

            ps.executeUpdate();
        }
    }

    public List<Application> getAllApplications(int userId) throws Exception {
        List<Application> list = new ArrayList<>();

        String sql = "SELECT id, company, position, status FROM applications WHERE user_id = ?"; // Filter by user_id

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);  // Set the user_id parameter

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String company = rs.getString("company");
                    String position = rs.getString("position");
                    String status = rs.getString("status");

                    Application app = new Application(id, company, position, status);
                    list.add(app);
                }
            }
        }

        return list;
    }


    public void updateStatus(int id, String newStatus) throws Exception {
        String sql = "UPDATE applications SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No application found with id " + id);
            } else {
                System.out.println("Status updated!");
            }
        }

    }


    public void registerUser(String username, String password) throws Exception {
        String sql = "INSERT INTO users (username, password_hash, created_at) VALUES (?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();
            System.out.println("User registered successfully!");
        }
    }


    public boolean loginUser(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    public int getUserIdFromDatabase(String username) throws Exception {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return 0;
    }
}
