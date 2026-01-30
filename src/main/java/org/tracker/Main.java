package org.tracker;

import org.tracker.dao.ApplicationDAO;
import org.tracker.entity.Application;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        try {

            ApplicationDAO dao = new ApplicationDAO();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== Application Tracker ===");
                System.out.println("1. Add application");
                System.out.println("2. View applications");
                System.out.println("3. Update status");
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
                    System.out.print("Company: ");
                    String company = sc.nextLine();

                    System.out.print("Position: ");
                    String position = sc.nextLine();

                    System.out.print("Status (Applied/Interview/Offer/Rejected): ");
                    String status = sc.nextLine();

                    Application app = new Application(company, position, status);
                    dao.addApplication(app);

                    System.out.println("Application saved!");
                }
                if (choice == 2) {
                    List<Application> apps = dao.getAllApplications();

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

                if (choice == 3) {
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












