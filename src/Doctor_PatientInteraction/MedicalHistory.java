package Doctor_PatientInteraction;

import UserManagement.Patient;
import java.util.HashMap;

public class MedicalHistory {

    public static HashMap<Integer, Patient> pastConsultants = new HashMap<>();

    public static void seeMedicalHistory(int patientId) {
        Patient patient = Patient.getPatient(patientId);
        if (patient == null) {
            System.out.println("Invalid patient ID.");
            return;
        }

        System.out.println("=== Medical History for Patient ID: " + patientId + " ===");
        boolean found = false;

        for (int docId : Prescription.prescriptions.keySet()) {
            Feedback feedback = Prescription.prescriptions.get(docId);
            if (feedback != null && feedback.getPatientid() == patientId) {
                System.out.println("From Doctor ID: " + docId);
                System.out.println(feedback);
                System.out.println("----------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No medical history found for this patient.");
        }
    }
}
