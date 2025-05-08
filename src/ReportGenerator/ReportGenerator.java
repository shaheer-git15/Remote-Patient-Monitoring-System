package ReportGenerator;

import Doctor_PatientInteraction.Feedback;
import Doctor_PatientInteraction.Prescription;
import HealthDataHandling.VitalsDatabase;
import HealthDataHandling.VitalSign;
import UserManagement.Patient;

public class ReportGenerator {

    public static String generatePatientReport(int patientId) {
        Patient patient = Patient.getPatient(patientId);
        if (patient == null) return "Patient not found.";

        StringBuilder report = new StringBuilder();
        report.append("===== Remote Patient Report =====\n");
        report.append("Patient ID: ").append(patient.getUserID()).append("\n");
        report.append("Name     : ").append(patient.getName()).append("\n");
        report.append("Gender   : ").append(patient.getGender()).append("\n");
        report.append("Age      : ").append(patient.getAge()).append("\n");
        report.append("Email    : ").append(patient.getEmail()).append("\n\n");

        // Add vitals
        report.append(">>> Latest Vitals <<<\n");
        VitalSign vitals = VitalsDatabase.vitals.get(patientId);
        if (vitals == null) {
            report.append("No vitals found.\n\n");
        } else {
            report.append(vitals.toString()).append("\n\n");
        }

        // Add feedback
        Feedback feedback = Prescription.prescriptions.get(patientId);
        if (feedback == null) {
            report.append(">>> Doctor Feedback <<<\nNo feedback or prescriptions found.\n");
        } else {
            report.append(">>> Doctor Feedback & Prescriptions <<<\n");
            report.append(feedback.toString()).append("\n");
        }

        report.append("==================================");
        return report.toString();
    }
}
