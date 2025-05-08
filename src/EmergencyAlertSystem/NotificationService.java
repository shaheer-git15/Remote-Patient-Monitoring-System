package EmergencyAlertSystem;

import NotificationAndReminders.EmailNotification;
import NotificationAndReminders.SMSNotification;
import NotificationAndReminders.Notifiable;
import UserManagement.Doctor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NotificationService {

    public static void sendToDoctor(int patientId, String compiledVitals) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter the Doctor ID to send alert: ");
            int doctorId = sc.nextInt();

            Doctor doctor = Doctor.getDoctor(doctorId);
            if (doctor == null) {
                throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
            }

            String message = "[ALERT] Notification from Patient ID " + patientId + ":\n" + compiledVitals;

            Notifiable email = new EmailNotification();
            Notifiable sms = new SMSNotification();

            email.send("doctor@hospital.com", message);
            sms.send("+1234567890", message);

            doctor.notifications.add(message);
            System.out.println("Alert successfully sent to Doctor ID: " + doctorId);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric Doctor ID.");
            sc.nextLine(); // clear buffer
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error while sending alert: " + e.getMessage());
        }
    }
}
