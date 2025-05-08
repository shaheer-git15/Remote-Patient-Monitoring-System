package UserManagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Load users from file
        UserFileHandler.loadUsersFromFile("E:\\nust\\Second Semester\\OOP\\Assignments\\" +
                "End Semester Project\\Remote Patient Monitoring System\\src\\UserManagement\\users.txt");

        while (true) {
            System.out.println("\n--- Welcome to RPMS ---");
            System.out.println("1. Login");
            System.out.println("2. Signup as New Patient");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                case 1 -> {
                    System.out.print("Enter your User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();
                    System.out.print("Enter your role (Doctor / Patient / Administrator): ");
                    String role = sc.nextLine();
                    User.login(id, password, role);
                }

                case 2 -> {
                    System.out.print("Enter new Patient ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Enter address: ");
                    String address = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();
                    System.out.print("Create password: ");
                    String password = sc.nextLine();

                    // Create new patient object
                    Patient newPatient = new Patient(id, name, age, gender, address, email, password);

                    // Append to file
                    UserFileHandler.appendUserToFile(newPatient, "E:\\nust\\Second Semester\\OOP\\Assignments\\" +
                            "End Semester Project\\Remote Patient Monitoring System\\src\\UserManagement\\users.txt");

                    System.out.println("Patient registered successfully! You can now log in.");
                }

                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }
}
