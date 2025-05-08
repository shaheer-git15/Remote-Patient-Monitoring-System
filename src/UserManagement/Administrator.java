package UserManagement;

import NotificationAndReminders.ReminderService;
import ReportGenerator.ReportGenerator;

import java.util.HashMap;
import java.util.Scanner;

public class Administrator extends User {
    static Scanner sc = new Scanner(System.in);

    public static HashMap<Integer, Administrator> admins = new HashMap<>();

    public Administrator(int userID, String name, int age, String gender, String address, String email, String password) {
        super(userID, name, age, gender, address, email, password);
        admins.put(userID, this);
    }

    public static Administrator getAdministrator(int userID) {
        return admins.get(userID);
    }

    public void loginAdministrator() {
        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View all doctors");
            System.out.println("2. View all patients");
            System.out.println("3. Send reminder");
            System.out.println("4. Generate patient report");
            System.out.println("0. Logout");
            System.out.print("Select option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 0 -> { return; }
                case 1 -> showDoctors();
                case 2 -> showPatients();
                case 3 -> sendReminder();
                case 4 -> generateReport();
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void showDoctors() {
        if (Doctor.doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.println("--- Doctors ---");
            Doctor.doctors.values().forEach(System.out::println);
        }
    }

    private void showPatients() {
        if (Patient.patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            System.out.println("--- Patients ---");
            Patient.patients.values().forEach(System.out::println);
        }
    }

    private void sendReminder() {
        System.out.print("Enter patient ID to send reminder: ");
        int pid = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter message: ");
        String msg = sc.nextLine();
        ReminderService.sendReminder(pid, msg);
    }

    private void generateReport() {
        System.out.print("Enter patient ID for report: ");
        int pid = sc.nextInt();
        String report = ReportGenerator.generatePatientReport(pid);
        System.out.println("--- Report ---\n" + report);
    }

    @Override
    public String toString() {
        return "Admin ID: " + this.getUserID() + ", Name: " + this.getName();
    }
}
