package HealthDataHandling;

import java.util.HashMap;

public class VitalsDatabase {

    public static HashMap<Integer, VitalSign> vitals = new HashMap<>();

    public static String getHeartRate(int patientId) {
        if (vitals.containsKey(patientId))
            return "Heart rate: " + vitals.get(patientId).getHeartRate();
        else
            return "No such patient exists.";
    }

    public static String getOxygenLevel(int patientId) {
        if (vitals.containsKey(patientId))
            return "Oxygen level: " + vitals.get(patientId).getOxygenLevel();
        else
            return "No such patient exists.";
    }

    public static String getBloodPressure(int patientId) {
        if (vitals.containsKey(patientId))
            return "Blood pressure: " + vitals.get(patientId).getBloodPressure();
        else
            return "No such patient exists.";
    }

    public static String getTemperature(int patientId) {
        if (vitals.containsKey(patientId))
            return "Temperature: " + vitals.get(patientId).getTemperature();
        else
            return "No such patient exists.";
    }

    public static String getAllVitals(int patientId) {
        if (vitals.containsKey(patientId))
            return vitals.get(patientId).toString();
        else
            return "No such patient exists.";
    }
}
