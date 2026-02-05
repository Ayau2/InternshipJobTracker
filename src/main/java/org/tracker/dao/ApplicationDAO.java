package org.tracker.dao;

import org.tracker.database.DatabaseConnection;
import org.tracker.entity.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    // Исправленный метод для добавления заявки с привязкой к пользователю
    public void addApplication(int userId, Application app) throws Exception {
        String sql = "INSERT INTO applications(user_id, company, position, status) VALUES (?, ?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        // Устанавливаем значения для запроса
        ps.setInt(1, userId);  // Передаем user_id
        ps.setString(2, app.getCompany());
        ps.setString(3, app.getPosition());
        ps.setString(4, app.getStatus());

        ps.executeUpdate();  // Выполняем запрос
        conn.close();  // Закрываем соединение
    }

    // Метод для получения всех заявок
    public List<Application> getAllApplications() throws Exception {

        List<Application> list = new ArrayList<>();

        String sql = "SELECT id, company, position, status FROM applications";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String company = rs.getString("company");
            String position = rs.getString("position");
            String status = rs.getString("status");

            Application app = new Application(id, company, position, status);
            list.add(app);
        }

        conn.close();
        return list;
    }

    // Метод для обновления статуса заявки
    public void updateStatus(int id, String newStatus) throws Exception {

        String sql = "UPDATE applications SET status = ? WHERE id = ?";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, newStatus);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();
        conn.close();

        if (rows == 0) {
            System.out.println("No application found with id " + id);
        } else {
            System.out.println("Status updated!");
        }
    }
}
