package HealthDataHandling;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class VitalsHistoryTracker {

    private static final String HISTORY_FILE = "E:\\nust\\Second Semester\\OOP\\Assignments\\End Semester Project\\" +
            "Remote Patient Monitoring System\\src\\HealthDataHandling\\vitals_history.txt";

    public static void logVitals(int patientId, VitalSign vitals) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            String record = patientId + "," +
                    vitals.getHeartRate() + "," +
                    vitals.getOxygenLevel() + "," +
                    vitals.getBloodPressure() + "," +
                    vitals.getTemperature() + "," +
                    LocalDateTime.now();

            pw.println(record);
        } catch (Exception e) {
            System.out.println("Failed to log vitals: " + e.getMessage());
        }
    }
}
