package UserManagement;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class UserFileHandler {

    public static void loadUsersFromFile(String filePath) {
        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNextLine()) {
                String[] fields = sc.nextLine().split(",");
                if (fields.length != 8)
                    continue;

                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                int age = Integer.parseInt(fields[2]);
                String gender = fields[3];
                String address = fields[4];
                String email = fields[5];
                String password = fields[6];
                String role = fields[7].toLowerCase();

                switch (role) {
                    case "doctor" -> new Doctor(id, name, age, gender, address, email, password);
                    case "patient" -> new Patient(id, name, age, gender, address, email, password);
                    case "administrator" -> new Administrator(id, name, age, gender, address, email, password);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public static void appendUserToFile(User user, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            String record = user.getUserID() + "," +
                    user.getName() + "," +
                    user.getAge() + "," +
                    user.getGender() + "," +
                    user.getAddress() + "," +
                    user.getEmail() + "," +
                    user.getPassword() + "," +
                    getRole(user);
            pw.println(record);
        } catch (Exception e) {
            System.out.println("Error writing user: " + e.getMessage());
        }
    }

    private static String getRole(User user) {
        if (user instanceof Doctor) return "Doctor";
        if (user instanceof Administrator) return "Administrator";
        return "Patient";
    }
}
