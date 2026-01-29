package org.tracker;

import org.tracker.dao.ApplicationDAO;
import org.tracker.entity.Application;

public class Main {

    public static void main(String[] args) {

        try {

            ApplicationDAO dao = new ApplicationDAO();

            Application app =
                    new Application("Google", "Intern", "Applied");

            dao.addApplication(app);

            System.out.println("Application saved!");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}