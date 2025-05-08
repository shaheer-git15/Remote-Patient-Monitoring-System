package NotificationAndReminders;

import UserManagement.Patient;

public class ReminderService {
    public static void sendReminder(int patientId, String message) {
        try {
            Patient patient = Patient.getPatient(patientId);
            if (patient == null) {
                throw new IllegalArgumentException("Patient with ID " + patientId + " does not exist.");
            }

            Notifiable email = new EmailNotification();
            Notifiable sms = new SMSNotification();

            email.send(patient.getEmail(), message);
            sms.send("+1234567890", message); // Placeholder phone

            patient.notifications.add("[Reminder] " + message);
            System.out.println("Reminder sent to Patient ID: " + patientId);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while sending reminder: " + e.getMessage());
        }
    }
}
