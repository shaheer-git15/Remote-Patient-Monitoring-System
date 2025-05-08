package Doctor_PatientInteraction;

import java.util.HashMap;
import java.util.Scanner;

public class Prescription {
    static Scanner sc = new Scanner(System.in);

    // Key: patientId → Value: Feedback
    public static HashMap<Integer, Feedback> prescriptions = new HashMap<>();

    public static String seeprescriptions(int patientId) {
        System.out.print("Enter the doctor ID to view prescription from: ");
        int doctorId = sc.nextInt();

        if (!prescriptions.containsKey(patientId)) {
            return "No prescriptions found for this patient.";
        }

        Feedback feedback = prescriptions.get(patientId);

        if (feedback.getPatientid() == patientId &&
                feedback.toString().contains("Doctor ID: " + doctorId)) {
            return feedback.toString();
        } else {
            return "No prescriptions from this doctor for this patient.";
        }
    }
}
