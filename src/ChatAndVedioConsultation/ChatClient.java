package ChatAndVedioConsultation;

import UserManagement.Doctor;
import UserManagement.Patient;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ChatClient {
    static Scanner sc = new Scanner(System.in);

    public static void startChat(int userID) {
        boolean isDoctor = Doctor.doctors.containsKey(userID);

        try {
            System.out.print("Enter message: ");
            sc.nextLine();  // clear buffer
            String message = sc.nextLine();

            if (isDoctor) {
                System.out.print("Enter Patient ID to send message to: ");
                int pid = sc.nextInt();
                Patient patient = Patient.getPatient(pid);
                if (patient == null) {
                    throw new IllegalArgumentException("Patient not found.");
                }
                patient.messages.put(userID, message);
                ChatServer.logMessage(userID, pid, message);
            } else {
                System.out.print("Enter Doctor ID to send message to: ");
                int did = sc.nextInt();
                Doctor doctor = Doctor.getDoctor(did);
                if (doctor == null) {
                    throw new IllegalArgumentException("Doctor not found.");
                }
                doctor.messages.put(userID, message);
                ChatServer.logMessage(userID, did, message);
            }

            System.out.println("Message sent.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric ID.");
            sc.nextLine(); // clear buffer
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
