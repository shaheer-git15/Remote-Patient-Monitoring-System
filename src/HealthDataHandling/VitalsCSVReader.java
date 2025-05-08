package HealthDataHandling;

import EmergencyAlertSystem.EmergencyMonitor;
import UserManagement.Patient;

import java.io.File;
import java.util.Scanner;

public class VitalsCSVReader {

    public static void importVitalsFromCSV(String filePath) {
        try (Scanner sc = new Scanner(new File(filePath))) {
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                if (data.length != 5) continue;

                int patientId = Integer.parseInt(data[0]);
                double heartRate = Double.parseDouble(data[1]);
                double oxygenLevel = Double.parseDouble(data[2]);
                String bloodPressure = data[3];
                double temperature = Double.parseDouble(data[4]);

                // Validate patient
                Patient patient = Patient.getPatient(patientId);
                if (patient == null) {
                    System.out.println("Patient ID " + patientId + " not found. Skipping.");
                    continue;
                }

                // Create and update vitals
                VitalSign vitals = new VitalSign(patientId);
                vitals.setHeartRate(heartRate);
                vitals.setOxygenLevel(oxygenLevel);
                vitals.setBloodPressure(bloodPressure);
                vitals.setTemperature(temperature);

                VitalsDatabase.vitals.put(patientId, vitals);
                VitalsHistoryTracker.logVitals(patientId, vitals);
                EmergencyMonitor.checkVitals(patientId, vitals);

                System.out.println("Vitals updated for Patient ID: " + patientId);
            }

        } catch (Exception e) {
            System.out.println("Error reading vitals CSV: " + e.getMessage());
        }
    }
}
