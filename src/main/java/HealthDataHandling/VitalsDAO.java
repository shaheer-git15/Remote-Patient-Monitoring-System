package HealthDataHandling;

import DatabaseConnector.DBConnection;
import EmergencyAlertSystem.EmergencyMonitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VitalsDAO {

    // Insert vitals and check for emergency
    public static void insertVitals(VitalSign vitals) {
        try (Connection con = DBConnection.getConnection()) {
            String updateVitals = "UPDATE vitals SET heart_rate = ?, oxygen_level = ?, " +
                    "blood_pressure = ?, temperature = ? WHERE patient_id = ?";
            String insertHistory = "INSERT INTO vitals_history (patient_id, heart_rate, oxygen_level," +
                    " blood_pressure, temperature) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps1 = con.prepareStatement(updateVitals);
            PreparedStatement ps2 = con.prepareStatement(insertHistory);

            for (PreparedStatement ps : new PreparedStatement[]{ps1, ps2}) {
                ps.setInt(1, vitals.getPatientId());
                ps.setDouble(2, vitals.getHeartRate());
                ps.setDouble(3, vitals.getOxygenLevel());
                ps.setString(4, vitals.getBloodPressure());
                ps.setDouble(5, vitals.getTemperature());
                ps.executeUpdate();
            }

            EmergencyMonitor.checkVitals(vitals.getPatientId()); // trigger optimized check

        } catch (Exception e) {
            System.out.println("Error inserting vitals: " + e.getMessage());
        }
    }

    // Get latest vitals as object
    public static VitalSign getVitals(int patientId) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM vitals WHERE patient_id = ? ORDER BY timestamp DESC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                VitalSign v = new VitalSign(patientId);
                v.setHeartRate(rs.getDouble("heart_rate"));
                v.setOxygenLevel(rs.getDouble("oxygen_level"));
                v.setBloodPressure(rs.getString("blood_pressure"));
                v.setTemperature(rs.getDouble("temperature"));
                return v;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving vitals: " + e.getMessage());
        }
        return null;
    }

    // Get vitals as formatted string
    public static String getVitalsAsString(int patientId) {
        VitalSign v = getVitals(patientId);
        return (v == null) ?
                "No vitals found." : v.toString();
    }

    // Import from CSV call this in VitalsCSVReader
    public static void importVitalsFromCSV(String filePath) {
        VitalsCSVReader.importVitalsFromCSV(filePath); // make sure that reader calls insertVitals()
    }
}