package EmergencyAlertSystem;

import NotificationAndReminders.EmailNotification;
import NotificationAndReminders.SMSNotification;
import NotificationAndReminders.Notifiable;
import UserManagement.Doctor;
import HealthDataHandling.VitalSign;

public class EmergencyMonitor {

    public static void checkVitals(int patientId, VitalSign vitals) {
        boolean isCritical = false;
        StringBuilder msg = new StringBuilder("[EMERGENCY] Patient ID: " + patientId + "\n");

        if (vitals.getHeartRate() > 130 || vitals.getHeartRate() < 40) {
            msg.append("Abnormal Heart Rate: ").append(vitals.getHeartRate()).append("\n");
            isCritical = true;
        }

        if (vitals.getOxygenLevel() < 85) {
            msg.append("Low Oxygen Level: ").append(vitals.getOxygenLevel()).append("\n");
            isCritical = true;
        }

        if (vitals.getTemperature() > 103 || vitals.getTemperature() < 95) {
            msg.append("Abnormal Temperature: ").append(vitals.getTemperature()).append("\n");
            isCritical = true;
        }

        // Simple string check for critical blood pressure
        String bp = vitals.getBloodPressure();
        if (!bp.equals("120/80")) {
            msg.append("Abnormal Blood Pressure: ").append(bp).append("\n");
            isCritical = true;
        }

        if (isCritical) {
            // Send alert to first available doctor (or assigned doctor logic later)
            for (Doctor doctor : Doctor.doctors.values()) {
                doctor.notifications.add(msg.toString());
                Notifiable email = new EmailNotification();
                Notifiable sms = new SMSNotification();
                email.send("doctor@hospital.com", msg.toString());
                sms.send("+1234567890", msg.toString());
                System.out.println("Auto emergency alert sent to Doctor ID: " + doctor.getUserID());
                break; // Send to only one doctor for now
            }
        }
    }
}
