package UserManagement;

import AppointmentScheduling.Appointment;
import AppointmentScheduling.AppointmentManager;
import ChatAndVedioConsultation.ChatClient;
import ChatAndVedioConsultation.VideoCall;
import Doctor_PatientInteraction.MedicalHistory;
import Doctor_PatientInteraction.Prescription;
import EmergencyAlertSystem.PanicButton;
import EmergencyAlertSystem.EmergencyMonitor;
import HealthDataHandling.VitalSign;
import HealthDataHandling.VitalsDatabase;
import HealthDataHandling.VitalsCSVReader;
import HealthDataHandling.VitalsHistoryTracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Patient extends User {
    private VitalSign vitals;
    static Scanner sc = new Scanner(System.in);

    public static HashMap<Integer, Patient> patients = new HashMap<>();
    public HashMap<Integer, Appointment> appointments = new HashMap<>();
    public HashMap<Integer, String> messages = new HashMap<>();
    public HashSet<String> notifications = new HashSet<>();

    public Patient(int userID, String name, int age, String gender, String address, String email, String password) {
        super(userID, name, age, gender, address, email, password);
        patients.put(userID, this);
        this.vitals = new VitalSign(this.getUserID());
    }

    public static Patient getPatient(int userID) {
        return patients.get(userID);
    }

    public void loginPatient() {
        while (true) {
            System.out.println("\n--- Patient Dashboard ---");
            System.out.println("1. Book appointment");
            System.out.println("2. View appointments");
            System.out.println("3. Upload vitals manually");
            System.out.println("4. Upload vitals from CSV");
            System.out.println("5. View medical history");
            System.out.println("6. Press Panic Button");
            System.out.println("7. View notifications");
            System.out.println("8. View messages");
            System.out.println("9. Reply to message");
            System.out.println("10. Start chat");
            System.out.println("11. Start video call");
            System.out.println("0. Logout");
            System.out.print("Choose option: ");
            int answer = sc.nextInt();

            switch (answer) {
                case 0 -> { return; }
                case 1 -> bookAppointment();
                case 2 -> seeAppointments();
                case 3 -> manageVitals();
                case 4 -> {
                    System.out.print("Enter CSV file path: ");
                    sc.nextLine();
                    String path = sc.nextLine();
                    VitalsCSVReader.importVitalsFromCSV(path);
                }
                case 5 -> medicalHistory();
                case 6 -> new PanicButton(this.getUserID());
                case 7 -> viewNotifications();
                case 8 -> viewMessages();
                case 9 -> replyToMessage();
                case 10 -> ChatClient.startChat(this.getUserID());
                case 11 -> VideoCall.startCall(this.getUserID());
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void viewNotifications() {
        if (notifications.isEmpty()) System.out.println("No notifications.");
        else {
            System.out.println("--- Notifications ---");
            for (String note : notifications) System.out.println(note);
            notifications.clear();
        }
    }

    public void viewMessages() {
        if (messages.isEmpty()) System.out.println("No messages.");
        else messages.forEach((id, msg) -> System.out.println("From " + id + ": " + msg));
    }

    public void replyToMessage() {
        System.out.print("Enter ID to reply to: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter reply: ");
        String reply = sc.nextLine();
        if (Doctor.getDoctor(id) != null) {
            Doctor.getDoctor(id).messages.put(this.getUserID(), reply);
            ChatAndVedioConsultation.ChatServer.logMessage(this.getUserID(), id, reply);
        } else {
            System.out.println("Invalid recipient.");
        }
    }

    private void bookAppointment() {
        System.out.print("Enter your patient ID: ");
        int patientid = sc.nextInt();
        System.out.print("Enter the doctor's ID: ");
        int doctorid = sc.nextInt();
        AppointmentManager.bookAppointment(patientid, doctorid);
    }

    private void seeAppointments() {
        System.out.println(AppointmentManager.appointments.values());
        while (true) {
            System.out.println("1. Cancel an appointment");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            switch (choice) {
                case 0 -> { return; }
                case 1 -> cancelAppointment();
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void cancelAppointment() {
        System.out.print("Enter appointment ID to cancel: ");
        int appointmentid = sc.nextInt();
        AppointmentManager.cancelAppointment(appointmentid);
    }

    private void manageVitals() {
        while (true) {
            System.out.println("1. Change all vitals");
            System.out.println("2. Heart rate");
            System.out.println("3. Oxygen level");
            System.out.println("4. Blood pressure");
            System.out.println("5. Temperature");
            System.out.println("6. View vitals");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            switch (choice) {
                case 0 -> { return; }
                case 1 -> changeAllVitals();
                case 2 -> changeHeartRate();
                case 3 -> changeOxygenLevel();
                case 4 -> changeBloodpressure();
                case 5 -> changeTemperature();
                case 6 -> seeAllVitals();
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void changeHeartRate() {
        System.out.print("Enter heart rate: ");
        double heartRate = sc.nextDouble();
        vitals.setHeartRate(heartRate);
        updateVitals();
    }

    private void changeOxygenLevel() {
        System.out.print("Enter oxygen level: ");
        double oxygen = sc.nextDouble();
        vitals.setOxygenLevel(oxygen);
        updateVitals();
    }

    private void changeBloodpressure() {
        System.out.print("Enter blood pressure (e.g. 120/80): ");
        String bp = sc.next();
        vitals.setBloodPressure(bp);
        updateVitals();
    }

    private void changeTemperature() {
        System.out.print("Enter temperature: ");
        double temp = sc.nextDouble();
        vitals.setTemperature(temp);
        updateVitals();
    }

    private void changeAllVitals() {
        changeHeartRate();
        changeOxygenLevel();
        changeBloodpressure();
        changeTemperature();
    }

    private void updateVitals() {
        VitalsDatabase.vitals.put(this.getUserID(), vitals);
        VitalsHistoryTracker.logVitals(this.getUserID(), vitals);
        EmergencyMonitor.checkVitals(this.getUserID(), vitals);
    }

    private void seeAllVitals() {
        System.out.println(VitalsDatabase.getAllVitals(this.getUserID()));
    }

    private void medicalHistory() {
        while (true) {
            System.out.println("1. View prescription by doctor");
            System.out.println("2. Full medical history");
            System.out.println("0. Back");
            int choice = sc.nextInt();
            switch (choice) {
                case 0 -> { return; }
                case 1 -> seePrescription();
                case 2 -> MedicalHistory.seeMedicalHistory(this.getUserID());
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void seePrescription() {
        System.out.println(Prescription.seeprescriptions(this.getUserID()));
    }

    @Override
    public String toString() {
        return "Patient ID: " + this.getUserID() + ", Name: " + this.getName() + ", Age: " + this.getAge();
    }
}
