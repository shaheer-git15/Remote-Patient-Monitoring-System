package UserManagement;

import AppointmentScheduling.Appointment;
import AppointmentScheduling.AppointmentManager;
import ChatAndVedioConsultation.ChatClient;
import ChatAndVedioConsultation.ChatServer;
import ChatAndVedioConsultation.VideoCall;
import Doctor_PatientInteraction.Feedback;
import Doctor_PatientInteraction.MedicalHistory;
import Doctor_PatientInteraction.Prescription;
import HealthDataHandling.VitalsDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Doctor extends User {
    static Scanner sc = new Scanner(System.in);

    public static HashMap<Integer, Doctor> doctors = new HashMap<>();
    public HashMap<Integer, Appointment> appointments = new HashMap<>();
    public HashSet<Integer> approveAppointments = new HashSet<>();
    public HashMap<Integer, String> messages = new HashMap<>();
    public HashSet<String> notifications = new HashSet<>();

    public Doctor(int userID, String name, int age, String gender, String address, String email, String password) {
        super(userID, name, age, gender, address, email, password);
        doctors.put(userID, this);
    }

    public static Doctor getDoctor(int userID) {
        return doctors.get(userID);
    }

    public void loginDoctor() {
        while (true) {
            System.out.println("\n--- Doctor Dashboard ---");
            System.out.println("1. View vitals of a patient");
            System.out.println("2. See appointment requests");
            System.out.println("3. See appointment history");
            System.out.println("4. Prescribe and give feedback");
            System.out.println("5. View messages");
            System.out.println("6. Reply to messages");
            System.out.println("7. Start chat");
            System.out.println("8. Start video call");
            System.out.println("9. View notifications");
            System.out.println("10. View full medical history");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 0 -> { return; }
                case 1 -> seeVitals();
                case 2 -> seeRequests();
                case 3 -> System.out.println(appointments.values());
                case 4 -> prescribeFeedback();
                case 5 -> viewMessages();
                case 6 -> replyToMessage();
                case 7 -> ChatClient.startChat(this.getUserID());
                case 8 -> VideoCall.startCall(this.getUserID());
                case 9 -> viewNotifications();
                case 10 -> seeFullHistory();
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewNotifications() {
        if (notifications.isEmpty()) System.out.println("No new notifications.");
        else {
            System.out.println("--- Notifications ---");
            for (String note : notifications) System.out.println(note);
            notifications.clear();
        }
    }

    private void viewMessages() {
        if (messages.isEmpty()) System.out.println("No messages.");
        else messages.forEach((id, msg) -> System.out.println("From " + id + ": " + msg));
    }

    private void replyToMessage() {
        System.out.print("Enter patient ID to reply: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter message: ");
        String reply = sc.nextLine();

        if (Patient.getPatient(id) != null) {
            Patient.getPatient(id).messages.put(this.getUserID(), reply);
            ChatServer.logMessage(this.getUserID(), id, reply);
        } else {
            System.out.println("Patient not found.");
        }
    }

    private void seeVitals() {
        System.out.print("Enter patient ID to view vitals: ");
        int pid = sc.nextInt();
        System.out.println(VitalsDatabase.getAllVitals(pid));
    }

    private void seeRequests() {
        System.out.print("Enter appointment ID to review: ");
        int aid = sc.nextInt();
        System.out.println(AppointmentManager.appointments.get(aid));
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        int c = sc.nextInt();
        if (c == 1) AppointmentManager.approveAppointment(aid, this.getUserID());
        else if (c == 2) AppointmentManager.rejectAppointment(aid, this.getUserID());
        else System.out.println("Invalid input.");
    }

    private void prescribeFeedback() {
        System.out.print("Enter patient ID: ");
        int pid = sc.nextInt();
        Feedback feedback = new Feedback(pid, this.getUserID());
        feedback.prescribeMedicines();
        feedback.prescribeSchedule();
        Prescription.prescriptions.put(pid, feedback);
    }

    private void seeFullHistory() {
        System.out.print("Enter patient ID: ");
        int pid = sc.nextInt();
        MedicalHistory.seeMedicalHistory(pid);
    }

    @Override
    public String toString() {
        return "Doctor ID: " + this.getUserID() + ", Name: " + this.getName();
    }
}
