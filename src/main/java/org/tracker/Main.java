package org.tracker;

import org.tracker.database.DatabaseConnection;
import org.tracker.dao.ApplicationDAO;
import org.tracker.entity.Application;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationDAO dao = new ApplicationDAO();
            Scanner sc = new Scanner(System.in);

            int userId = 0; // Initialize userId as 0. It will be set after login.

            while (true) {
                System.out.println("\n=== Application Tracker ===");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Add application");
                System.out.println("4. View applications");
                System.out.println("5. Update status");
                System.out.println("0. Exit");
                System.out.print("Choose option: ");

                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Please enter a number.");
                    continue;
                }
                int choice = Integer.parseInt(input);

                if (choice == 0) {
                    System.out.println("Bye!");
                    break;
                }

                if (choice == 1) {
                    // Register functionality
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    // Call the registerUser method from ApplicationDAO
                    dao.registerUser(username, password);  // Register user
                    System.out.println("User registered successfully!");
                }

                if (choice == 2) {
                    // Login functionality
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter password: ");
                    String password = sc.nextLine();

                    // Call the loginUser method from ApplicationDAO
                    if (dao.loginUser(username, password)) {
                        System.out.println("Login successful!");
                        userId = dao.getUserIdFromDatabase(username);  // Get userId after successful login
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                }

                if (choice == 3) {
                    // Add application
                    if (userId == 0) {
                        System.out.println("Please login first.");
                        continue;
                    }

                    System.out.print("Company: ");
                    String company = sc.nextLine();

                    System.out.print("Position: ");
                    String position = sc.nextLine();

                    System.out.print("Status (Applied/Interview/Offer/Rejected): ");
                    String status = sc.nextLine();

                    // Create a new Application object
                    Application app = new Application(company, position, status);

                    // Pass user_id and application to the addApplication method
                    dao.addApplication(userId, app);

                    System.out.println("Application saved!");
                }

                if (choice == 4) {
                    // View applications
                    if (userId == 0) {
                        System.out.println("Please login first.");
                        continue;
                    }

                    List<Application> apps = dao.getAllApplications(userId);

                    System.out.println("=== Applications in DB ===");
                    for (Application a : apps) {
                        System.out.println(
                                a.getId() + " | " +
                                        a.getCompany() + " | " +
                                        a.getPosition() + " | " +
                                        a.getStatus()
                        );
                    }
                }

                if (choice == 5) {
                    // Update application status
                    if (userId == 0) {
                        System.out.println("Please login first.");
                        continue;
                    }

                    System.out.print("Enter application ID: ");
                    String idInput = sc.nextLine().trim();
                    if (idInput.isEmpty()) {
                        System.out.println("ID cannot be empty.");
                        continue;
                    }
                    int id = Integer.parseInt(idInput);

                    System.out.print("Enter new status (Applied/Interview/Offer/Rejected): ");
                    String newStatus = sc.nextLine();

                    dao.updateStatus(id, newStatus);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
